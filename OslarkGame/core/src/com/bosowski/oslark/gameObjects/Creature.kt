package com.bosowski.oslark.gameObjects

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.bosowski.oslark.World
import com.bosowski.oslark.components.Animator
import com.bosowski.oslark.enums.Direction
import com.bosowski.oslark.enums.State
import com.bosowski.oslark.enums.TerrainType
import com.bosowski.oslark.main.Assets
import com.bosowski.oslark.utils.Constants

import org.json.JSONObject

import java.util.ArrayList

abstract class Creature : GameObject {

  var totalHitPoints: Float = 0.toFloat()
  protected var hitPoints: Float = 0.toFloat()
    set(hitPoints: Float) {
      when {
        hitPoints > totalHitPoints -> field = totalHitPoints
        hitPoints <= 0 -> {
          field = 0f
          state = State.DIE
          position.z = Math.floor(position.z.toDouble()).toFloat() + java.lang.Float.MIN_VALUE
          collides = false
          println("$name died.")
        }
        else -> field = hitPoints
      }
    }

  protected var hasAttacked = false
  var damage = 0f
  var animator: Animator? = null
  var state = State.MOVE
    set(state) {
      if (field != state && field != State.DIE && state != State.ATTACK) {
        stateTime = 0f
        field = state
      }
      if(animator != null && animator?.animations!!.containsKey(state)){
        animation = animator!!.animations[state]
      }
    }

  protected var direction = Direction.DOWN
    set(direction: Direction) {
      if ((direction == Direction.LEFT || direction == Direction.RIGHT) && direction != this.direction) {
        //change direction
        scale = Vector2(Math.abs(scale.x) * direction.value.x, scale.y)
      }
      field = direction

      //animation = animator.getAnimations().get(direction).get(state);
    }
  var speed = 0f
  var level = 1


  constructor(jsonObject: JSONObject) : super(jsonObject.getJSONObject("super")) {
    this.level = jsonObject.getInt("level")
    this.totalHitPoints = jsonObject.getFloat("totalHitpoints")
    this.hitPoints = jsonObject.getFloat("hitpoints")
    this.damage = jsonObject.getFloat("damage")
    this.speed = jsonObject.getFloat("speed")
    if(Assets.instance.stateAnimations.containsKey(name)){
      this.animator = Animator(Assets.instance.stateAnimations[name]!!)
    }
    this.state = State.getState(jsonObject.getString("state"))
  }


  constructor(name: String, animator: Animator, scale: Vector2, collides: Boolean, collisionBox: Rectangle, totalHitPoints: Float, hitPoints: Float, damage: Float, state: State, direction: Direction, speed: Float, level: Int, position: Vector3) : super(name, scale, collides, collisionBox, position) {
    this.totalHitPoints = totalHitPoints
    this.hitPoints = hitPoints
    this.animator = animator
    this.state = state
    this.direction = direction
    this.damage = damage
    this.speed = speed
    this.level = level
  }

  constructor(original: Creature) : super(original) {
    this.totalHitPoints = original.totalHitPoints
    this.hitPoints = original.hitPoints
    this.animator = original.animator
    this.state = original.state
    this.direction = original.direction
    this.hasAttacked = original.hasAttacked
    this.damage = original.damage
    this.speed = original.speed
  }

  abstract override fun update(deltaTime: Float)

  fun attack() {
    animation!!.frameDuration = Constants.FRAME_DURATION
    state = State.ATTACK
    if (state == State.ATTACK && animation!!.getKeyFrameIndex(stateTime) == animation!!.keyFrames.size - 1 && !hasAttacked) {
      val attackArea = Rectangle(position.x + direction.value.x, position.y + direction.value.y, 1f, 1f)
      println("$name is attacking.")
      for (gameObject in World.instance.gameObjects) {
        if (gameObject !== this && gameObject is Creature && gameObject.collides && Math.floor(position.z.toDouble()) == Math.floor(gameObject.position.z.toDouble()) && gameObject.collisionBox.overlaps(attackArea)) {
          gameObject.receiveDamage(damage)
          println("Attacked ." + gameObject.name)
        }
      }
      hasAttacked = true
    } else if (animation!!.getKeyFrameIndex(stateTime) != animation!!.keyFrames.size - 1 && hasAttacked) {
      hasAttacked = false
    }
  }

  fun move(amount: Float, deltaTime: Float, direction: Direction): Boolean {
    var amount = amount
    if (state == State.DIE) {
      return false
    }
    if (checkCollisions(deltaTime).contains(TerrainType.MUCK)) {
      amount /= 2
      stateTime -= deltaTime / 2
    }
    val futurePos = Vector3(position)
    futurePos.mulAdd(Vector3(direction.value.x, direction.value.y, 0f), deltaTime * amount)
    val futureBounds = Rectangle()
    futureBounds.setPosition(futurePos.x, futurePos.y)
    futureBounds.setWidth(collisionBox.getWidth())
    futureBounds.setHeight(collisionBox.getHeight())
    this.direction = direction

    if (!World.instance.willCollide(this, futurePos)) {
      position = futurePos
      state = State.MOVE
      return true
    }
    return false
  }

  fun receiveDamage(damage: Float) {
    println("$name received $damage damage.")
    hitPoints -= damage
  }

  fun checkCollisions(deltaTime: Float): ArrayList<TerrainType> {
    val terrainCollisions = ArrayList<TerrainType>()
    for (gameObject in World.instance.gameObjects) {
      if (gameObject is Terrain && gameObject.collides && collisionBox.overlaps(gameObject.collisionBox)) {
        when (gameObject.terrain) {
          TerrainType.MUCK ->
            //System.out.println("On muck");
            terrainCollisions.add(TerrainType.MUCK)
          TerrainType.NORMAL ->
            //System.out.println("On normal");
            terrainCollisions.add(TerrainType.NORMAL)
          TerrainType.OBSTRUCTION ->
            //System.out.println("On obstruction");
            terrainCollisions.add(TerrainType.OBSTRUCTION)
          TerrainType.FIRE ->
            //System.out.println("On fire");
            terrainCollisions.add(TerrainType.FIRE)
          TerrainType.WATER ->
            //System.out.println("On water");
            terrainCollisions.add(TerrainType.WATER)
        }
      }
    }
    return terrainCollisions
  }

  fun reactOnEnvironment(deltaTime: Float) {
    for (gameObject in World.instance.gameObjects) {
      if (gameObject is Terrain) {
        if (gameObject.terrain == TerrainType.FIRE) {
          val distance = distance(gameObject)
          if (distance <= Constants.FIRE_DAMAGE_DISTANCE) {
            val damage = Constants.FIRE_DPS / (distance + 1)
            println("Damage = $damage")
            hitPoints = (hitPoints - deltaTime * damage)
          }
        }
      }
    }
  }
}