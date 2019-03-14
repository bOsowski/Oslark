package com.bosowski.oslark.components

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.badlogic.gdx.physics.box2d.Shape
import com.bosowski.oslark.World

class ColliderComponent(
    private var type: BodyDef.BodyType = BodyDef.BodyType.DynamicBody,
    private val shape: Shape,
    private val density: Float
): AbstractComponent() {

  var direction: Vector2? = Vector2.Zero

  override fun awake() {
    World.physicsWorld.destroyBody(owner.transform.body)
    val bdef = BodyDef()
    bdef.position.set(owner.transform.position)
    bdef.type = type
    bdef.fixedRotation = true
    owner.transform.body = World.physicsWorld.createBody(bdef)

    val fdef = FixtureDef()
    fdef.shape = shape
    fdef.friction = 0f
    fdef.density = density

//    fdef.filter.categoryBits = owner.transform.layer
    owner.transform.body.createFixture(fdef)
  }

  fun move(direction: Vector2, speed: Float){
    this.direction = Vector2(direction)
    val velocity = Vector2(direction)
    velocity.x *= speed
    velocity.y *= speed
    owner.transform.body.linearVelocity = velocity
    //owner.transform.body.linearVelocity = velocity
  }

  override fun start() {}

  override fun update(deltaTime: Float) {}

  override fun render(batch: SpriteBatch) {}

  override fun destroy() {}
}