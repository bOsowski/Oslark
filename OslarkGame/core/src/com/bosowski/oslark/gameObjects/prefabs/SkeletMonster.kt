package com.bosowski.oslark.gameObjects.prefabs

import com.badlogic.gdx.ai.steer.Steerable
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.PolygonShape
import com.bosowski.oslark.Assets
import com.bosowski.oslark.World
import com.bosowski.oslark.components.*
import com.bosowski.oslark.enums.Direction
import com.bosowski.oslark.gameObjects.GameObject

class SkeletMonster(position: Vector2): GameObject(position, name = "skelet"){

    val speed = 15f
    var timer = 0f

    init {
        val animatorComponent = AnimatorComponent(Assets.stateAnimations["skelet"]!!)
        addComponent(animatorComponent)

        val shape = PolygonShape()
        shape.setAsBox(0.3f, 0.125f, Vector2(0f, -animatorComponent.dimension.y/2f), 0f)

        val collider = ColliderComponent(BodyDef.BodyType.DynamicBody, shape)

        addComponent(collider)

        transform.direction = Direction.getRandom(World.random)
        val action = UpdateActionInterface {deltaTime ->
            //todo: fix the below. notice how everything stops working when the below is uncommented..
            if(timer >= 2f){
                transform.direction = Direction.getRandom(World.random)
                timer = 0f
            }
            val velocity = Vector2(transform.direction.value)
            velocity.x *= speed * deltaTime
            velocity.y *= speed * deltaTime
            transform.body.linearVelocity = velocity
            timer += deltaTime
        }

        val ai = AIComponent(action)
        addComponent(ai)
    }
}