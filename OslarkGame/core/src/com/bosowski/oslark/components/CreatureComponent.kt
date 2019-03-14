package com.bosowski.oslark.components

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.physics.box2d.BodyDef
import com.bosowski.oslark.Assets
import com.bosowski.oslark.World
import com.bosowski.oslark.gameObjects.GameObject
import com.bosowski.oslark.gameObjects.prefabs.monsters.Monster
import com.bosowski.oslark.utils.Util

class CreatureComponent : AbstractComponent {

  var maxHealth: Float
    set(value) {
      field = value
      currentHealth = maxHealth
    }
  var level: Int
  var damage: Pair<Float, Float>
  var attack: ActionInterface?
  var canAttack = true
  var attackSpeed: Float

  // min/max damage
  constructor(maxHealth: Float, level: Int = 1, damage: Pair<Float, Float> = Pair(1f, 1f), attack: ActionInterface? = null, attackSpeed:Float = 1f) : super() {
    this.maxHealth = maxHealth
    this.level = level
    this.damage = damage
    this.attack = attack
    this.currentHealth = maxHealth
    this.attackSpeed = attackSpeed
  }

  var currentHealth: Float
    set(value) {
      field = when {
        value < 0 -> {
          die()
          0f
        }
        value > maxHealth -> maxHealth
        else -> value
      }
    }

  var timer:Float = 0f

  override fun awake() {
  }

  override fun start() {
  }

  override fun update(deltaTime: Float) {
    //perform an attack each second if the owner is a monster.
    timer += deltaTime
    if(timer >= attackSpeed){
      canAttack = true
      timer = 0f
    }

    if(owner is Monster && canAttack){
      attack?.perform(deltaTime)
    }
  }

  override fun render(batch: SpriteBatch) {
  }

  override fun destroy() {

  }

  fun die(){
    val death = GameObject(position = owner.transform.body.position, layer = 1, name = "death", bodyType = BodyDef.BodyType.KinematicBody)
    val animationComponent = AnimationComponent(Assets.animations["death"  ]!!)
    death.addComponent(animationComponent)
    death.transform.body.setLinearVelocity(0f,2f)
    var timer = 0f
    val actionComponent = ActionComponent(ActionInterface { deltaTime ->
      timer += deltaTime
      if(timer >= 0.5f){
        death.destroy()
      }
    })
    death.addComponent(actionComponent)
    death.instantiate()
    owner.destroy()
  }

  fun getDamage(): Float{
    return Util.randomFloat(World.random, damage.first, damage.second)
  }
}