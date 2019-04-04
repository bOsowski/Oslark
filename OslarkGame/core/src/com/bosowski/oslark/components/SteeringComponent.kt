package com.bosowski.oslark.components

import com.badlogic.gdx.ai.steer.Steerable
import com.badlogic.gdx.ai.steer.SteerableAdapter
import com.badlogic.gdx.ai.utils.Location
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.Fixture
import com.badlogic.gdx.physics.box2d.RayCastCallback
import com.bosowski.oslark.World

class SteeringComponent(var speed: Float, var collider: ColliderComponent): AbstractComponent() {

    override fun awake() {
    }

    override fun start() {
    }

    override fun update(deltaTime: Float) {

    }

    override fun render(batch: SpriteBatch) {
    }

    override fun destroy() {
    }

    fun goTo(target: Vector2){
        var direction:Vector2 = target.sub(owner!!.transform.position.x, owner!!.transform.position.y)
        direction = direction.nor()
        collider.move(direction, speed)
        //body.linearVelocity = Vector2(direction.x * speed, direction.y * speed)
    }

    //todo: This method sometimes throws an assertion error.
    fun raycast(target: Vector2): Boolean{
        if(Vector2.dst(target.x, target.y, owner!!.transform.position.x, owner!!.transform.position.y) < 5){
            return true
        }
        return false

//        var closestFraction = 10f
//        var collisionPoint: Vector2? = null
//        var collidedFixture: Fixture? = null
//
//        val callback = RayCastCallback { fixture, point, normal, fraction ->
//            if (fraction < closestFraction) {
//                closestFraction = fraction
//                collisionPoint = Vector2(point)
//                collidedFixture = fixture
//            }
//            0f
//        }
//
//        if(body.isActive){
//            World.physicsWorld.rayCast(callback, body.position, target)
//            if(World.rays[owner!!] != null && collisionPoint != null){
//                World.rays[owner!!] = Pair<Vector2?, Vector2?>(body.position, Vector2(collisionPoint))
//            }
//        }
//
//        if(World.player.transform.body == collidedFixture?.body){
//            return true
//        }
//        return false

    }
}