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

class SteeringComponent(val body: Body, var speed: Float, var target: Vector2?): AbstractComponent() {

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
        var direction:Vector2 = target.sub(body.position.x, body.position.y)
        direction = direction.nor()
        body.linearVelocity = Vector2(direction.x * speed, direction.y * speed)
    }

    fun raycast(target: Vector2): Boolean{
        var closestFraction = 1f
        var collisionPoint: Vector2? = null
        var collidedFixture: Fixture? = null

        val callback = RayCastCallback { fixture, point, normal, fraction ->
            if (fraction < closestFraction) {
                closestFraction = fraction
                collisionPoint = point
                collidedFixture = fixture
                println("Fixture = ${fixture}")
            }
            0f
        }

        World.physicsWorld.rayCast(callback, body.position, target)
        println("Collision point = $collisionPoint")
        World.rays[owner] = Pair<Vector2?, Vector2?>(body.position, Vector2(collisionPoint))

        if(World.player.transform.body == collidedFixture?.body){
            println("Can see player.")
            return true
        }
        return false

    }
}