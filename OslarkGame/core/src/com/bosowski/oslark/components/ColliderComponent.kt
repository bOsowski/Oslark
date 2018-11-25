package com.bosowski.oslark.components

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.EdgeShape
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.badlogic.gdx.physics.box2d.Shape
import com.bosowski.oslark.World

class ColliderComponent(
    private var type: BodyDef.BodyType = BodyDef.BodyType.DynamicBody,
    val shape: Shape
): AbstractComponent() {

  val body get() = owner.transform.body

  override fun awake() {
    World.physicsWorld.destroyBody(body)
    val bdef = BodyDef()
    bdef.position.set(owner.transform.position)
    bdef.type = type
    owner.transform.body = World.physicsWorld.createBody(bdef)

    val fdef = FixtureDef()
    fdef.shape = shape
    fdef.friction = 0f
//    fdef.filter.categoryBits = owner.transform.layer
    body.createFixture(fdef)
  }

  override fun start() {}

  override fun update(deltaTime: Float) {}

  override fun render(batch: SpriteBatch) {}

  override fun destroy() {
    World.physicsWorld.destroyBody(body)
  }
}