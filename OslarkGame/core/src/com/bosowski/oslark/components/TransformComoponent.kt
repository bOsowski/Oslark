package com.bosowski.oslark.components

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.bosowski.oslark.World
import com.bosowski.oslark.enums.Direction

class TransformComoponent(
    position: Vector2 = Vector2(),
    var layer: Short = 0) : AbstractComponent() {
  var body: Body
  var position: Vector2
    get() = body.position
    set(value){body.position.set(value)}


  init{
    val bdef = BodyDef()
    bdef.type = BodyDef.BodyType.KinematicBody
    bdef.position.set(position)
    body = World.physicsWorld.createBody(bdef)
  }

  override fun render(batch: SpriteBatch) {}

  override fun awake() {}

  override fun start() {}

  override fun update(deltaTime: Float) {}

  override fun destroy() {
    World.physicsWorld.destroyBody(body)
  }
}