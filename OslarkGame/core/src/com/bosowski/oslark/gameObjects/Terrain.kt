package com.bosowski.oslark.gameObjects

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.bosowski.oslark.enums.TerrainType
import com.bosowski.oslark.main.Assets
import org.json.JSONObject

open class Terrain : GameObject {

  var terrain = TerrainType.NORMAL
    private set

  constructor(terrain: Terrain) : super(terrain) {
    this.terrain = terrain.terrain
  }

  constructor(id: Int, name: String, position: Vector3, collides: Boolean) : super(id, name, position, collides) {
    setUp()
  }

  constructor(name: String, position: Vector3, collides: Boolean) : super(name, position, collides) {
    setUp()
  }

  constructor(jsonObject: JSONObject) : super(jsonObject.getJSONObject("super")) {
    this.terrain = TerrainType.valueOf(jsonObject.getString("terrainType"))
    //        setUp();
  }

  constructor(name: String, texture: TextureRegion, terrain: TerrainType, scale: Vector2, collides: Boolean, collisionBox: Rectangle, position: Vector3) : super(name, texture, scale, collides, collisionBox, position) {
    this.terrain = terrain
  }

  constructor(name: String, animation: Animation<*>, terrain: TerrainType, scale: Vector2, collides: Boolean, collisionBox: Rectangle, position: Vector3) : super(name, animation, scale, collides, collisionBox, position) {
    this.terrain = terrain
  }

  override fun toString(): String {
    return "Terrain{" +
        "id=" + id +
        ", name='" + name + '\''.toString() +
        ", position=" + position +
        ", rotation=" + rotation +
        ", animation=" + animation +
        ", stateTime=" + stateTime +
        ", texture=" + texture +
        ", scale=" + scale +
        ", dimension=" + dimension +
        ", origin=" + origin +
        ", collisionBox=" + collisionBox +
        ", collides=" + collides +
        '}'.toString()
  }

  protected fun setUp() {
    if (Assets.instance.animations.containsKey(name)) {
      this.animation = Assets.instance.animations[name]
    } else if (Assets.instance.textures.containsKey(name)) {
      this.texture = Assets.instance.textures[name]!!
    } else {
      this.texture = Assets.instance.textures["undefined"]!!
      Gdx.app.error(GameObject.TAG, "Unable to load any textures for object '$name' ($id)")
    }
    this.collisionBox = Rectangle(this.position.x - origin.x, this.position.y, 1f, 1f)
  }

  override fun update(deltaTime: Float) {}
}