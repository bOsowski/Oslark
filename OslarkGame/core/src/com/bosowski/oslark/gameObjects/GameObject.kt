package com.bosowski.oslark.gameObjects

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.bosowski.oslark.enums.State
import com.bosowski.oslark.main.Assets

import org.json.JSONObject

abstract class GameObject {

  protected var id = -1                       // id of -1 suggests the object has not been set up properly
  var name = "undefined"
  var position = Vector3.Zero  // The z component represents the depth.
  var rotation = 0f

  var animation: Animation<*>? = null
  var stateTime: Float = 0.toFloat()
  lateinit var texture: TextureRegion
  var scale = Vector2(1f, 1f)
  var dimension = Vector2(1f, 1f)
  var origin = Vector2(dimension.x / 2, dimension.y / 2)
  var collisionBox: Rectangle = Rectangle()
  var collides = false


  protected constructor(jsonObject: JSONObject) {
    try {
      this.id = jsonObject.getInt("id")
    } catch (e: Exception) {

    }

    this.name = jsonObject.getString("name")
    this.position = Vector3(
        jsonObject.getJSONObject("position").getFloat("x"),
        jsonObject.getJSONObject("position").getFloat("y"),
        jsonObject.getJSONObject("position").getFloat("z")
    )
    this.rotation = jsonObject.getFloat("rotation")
    this.stateTime = jsonObject.getFloat("stateTime")
    this.scale = Vector2(
        jsonObject.getJSONObject("scale").getFloat("x"),
        jsonObject.getJSONObject("scale").getFloat("y")
    )
    this.dimension = Vector2(
        jsonObject.getJSONObject("dimension").getFloat("x"),
        jsonObject.getJSONObject("dimension").getFloat("y")
    )
    this.origin = Vector2(
        jsonObject.getJSONObject("origin").getFloat("x"),
        jsonObject.getJSONObject("origin").getFloat("y")
    )
    if (!jsonObject.isNull("collisionBox")) {
      this.collisionBox = Rectangle(
          jsonObject.getJSONObject("collisionBox").getFloat("x"),
          jsonObject.getJSONObject("collisionBox").getFloat("y"),
          jsonObject.getJSONObject("collisionBox").getFloat("width"),
          jsonObject.getJSONObject("collisionBox").getFloat("height")
      )
    }

    this.collides = jsonObject.getBoolean("collides")

    when {
      Assets.instance.animations.containsKey(name) -> this.animation = Assets.instance.animations[name]
      Assets.instance.textures.containsKey(name) -> this.texture = Assets.instance.textures[name]!!
      else -> {
        this.texture = Assets.instance.textures["undefined"]!!
        Gdx.app.error(TAG, "Unable to load any textures for object '$name' ($id)")
      }
    }
  }

  protected constructor(original: GameObject) {
    this.id = original.id
    this.name = original.name
    this.position = Vector3(original.position)
    this.rotation = original.rotation
    this.animation = original.animation
    this.stateTime = original.stateTime
    this.texture = original.texture
    this.scale = original.scale
    this.dimension = original.dimension
    this.origin = original.origin
    this.collisionBox = Rectangle(original.collisionBox)
    this.collides = original.collides
  }

  constructor(name: String, animation: Animation<*>, scale: Vector2, collides: Boolean, collisionBox: Rectangle, position: Vector3) {
    this.name = name
    this.animation = animation
    this.scale = scale
    this.collides = collides
    this.collisionBox = Rectangle(collisionBox)
    this.position = position
    println("Assigning animation. Animation state = " + this.animation!!)
  }

  constructor(name: String, texture: TextureRegion, scale: Vector2, collides: Boolean, collisionBox: Rectangle, position: Vector3) {
    this.name = name
    this.texture = texture
    this.scale = scale
    this.collides = collides
    this.collisionBox = Rectangle(collisionBox)
    this.position = position
  }

  protected constructor(name: String, scale: Vector2, collides: Boolean, collisionBox: Rectangle, position: Vector3) {
    this.name = name
    this.scale = scale
    this.collides = collides
    this.collisionBox = Rectangle(collisionBox)
    this.position = position
  }

  constructor(id: Int, name: String, position: Vector3, collides: Boolean) {
    this.id = id
    this.name = name
    this.position = position
    this.collides = collides
  }

  constructor(name: String, position: Vector3, collides: Boolean) {
    this.name = name
    this.position = position
    this.collides = collides
  }

  fun render(batch: SpriteBatch) {
    if (animation != null) {
      if (this is Creature && this.state == State.DIE) {
        animation!!.setPlayMode(Animation.PlayMode.NORMAL)
      } else {
        animation!!.setPlayMode(Animation.PlayMode.LOOP)
      }
      stateTime += Gdx.graphics.deltaTime
      texture = animation!!.getKeyFrame(stateTime) as TextureRegion
    }
    batch.draw(texture.texture, position.x - origin.x, position.y - origin.y, origin.x, origin.y,
        dimension.x, dimension.y, scale.x, scale.y, rotation,
        texture.regionX, texture.regionY, texture.regionWidth, texture.regionHeight,
        false, false)
  }

  fun distance(other: GameObject): Float {
    return distance(other.position)
  }

  fun distance(otherPosition: Vector3): Float {
    return if (Math.floor(otherPosition.z.toDouble()) == Math.floor(position.z.toDouble())) {
      Vector2.dst2(otherPosition.x, otherPosition.y, position.x, position.y)
    } else {
      java.lang.Float.MAX_VALUE
    }
  }

  fun isWithinDistance(other: GameObject, distance: Float): Boolean {
    return Math.floor(other.position.z.toDouble()) == Math.floor(this.position.z.toDouble()) && Vector2.dst(other.position.x, other.position.y, this.position.x, this.position.y) <= distance
  }

  fun isWithinDistance(other: Vector3, distance: Float): Boolean {
    return Math.floor(other.z.toDouble()) == Math.floor(this.position.z.toDouble()) && Vector2.dst(other.x, other.y, this.position.x, this.position.y) <= distance
  }

  fun overlaps(other: GameObject): Boolean {
    return Math.floor(other.position.z.toDouble()) == Math.floor(this.position.z.toDouble()) && collisionBox.overlaps(other.collisionBox)
  }

  abstract fun update(deltaTime: Float)

  fun collides(): Boolean {
    return collides
  }

  companion object {

    val TAG = GameObject::class.java.name
  }
}