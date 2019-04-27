package com.bosowski.oslark.components

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.Fixture
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.badlogic.gdx.physics.box2d.Shape
import com.bosowski.oslark.World

class ColliderComponent(
    val shape: Shape,
    val density: Float
): AbstractComponent() {

  var direction: Vector2? = Vector2.Zero
  var fixture: Fixture? = null

  fun move(direction: Vector2, speed: Float){
    this.direction = Vector2(direction)
    val velocity = Vector2(direction)
    velocity.x *= speed
    velocity.y *= speed
    owner!!.transform.body?.linearVelocity = velocity
  }

  override fun start() {
    val fdef = FixtureDef()
    fdef.shape = shape
    fdef.friction = 0f
    fdef.density = density

    fixture = owner!!.transform.body?.createFixture(fdef)
  }

  override fun update(deltaTime: Float) {}

  override fun render(batch: SpriteBatch) {}

  override fun destroy() {
    if(fixture != null){
      owner!!.transform.body?.destroyFixture(fixture)
    }
  }
}