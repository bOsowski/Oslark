package com.bosowski.oslark.components

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.badlogic.gdx.physics.box2d.PolygonShape
import com.bosowski.oslark.World
import com.bosowski.oslark.gameObjects.GameObject

class ColliderComponent(
    owner: GameObject,
    type: BodyDef.BodyType = BodyDef.BodyType.DynamicBody,
    width: Float = 1f, height: Float = 1f,
    centre: Vector2 = Vector2(),
    angle: Float = 0f
): Component(owner) {

  val body get() = owner.transform.body

  init{
    val bdef = BodyDef()
    bdef.position.set(owner.transform.position)
    bdef.type = type
    World.physicsWorld.destroyBody(body)
    owner.transform.body = World.physicsWorld.createBody(bdef)

    val shape = PolygonShape()
    shape.setAsBox(width/2, height/2, centre, angle)

    val fdef = FixtureDef()
    fdef.shape = PolygonShape()
    fdef.filter.categoryBits = owner.transform.layer
    body.createFixture(fdef)
  }

  override fun awake() {}

  override fun start() {}

  override fun update(deltaTime: Float) {}

  override fun render(batch: SpriteBatch) {}

  override fun destroy() {
    World.physicsWorld.destroyBody(body)
  }
}