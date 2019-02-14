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

abstract class Monster(position: Vector2, name: String, scale: Vector2): GameObject(position, name = name){

    var speed = 3f
    var timer = 0f
    var action: UpdateActionInterface? = null

    var animatorComponent: AnimatorComponent
    var collider: ColliderComponent
    var steeringComponent: SteeringComponent
    var aiComponent: AIComponent
    var direction: Direction = Direction.DOWN

    init {
        animatorComponent = AnimatorComponent(Assets.stateAnimations[name]!!)
        animatorComponent.scale = scale
        addComponent(animatorComponent)

        val shape = PolygonShape()
        //println("Sprite dimension: "+animatorComponent.dimension.toString())
        shape.setAsBox(scale.x / 4, scale.y/ 9, Vector2(0f, 0f), 0f)

        collider = ColliderComponent(BodyDef.BodyType.DynamicBody, shape)

        addComponent(collider)

        steeringComponent = SteeringComponent(collider.body, speed, World.player.transform.position)
        addComponent(steeringComponent)

        aiComponent = AIComponent(action)
        addComponent(aiComponent)
    }

    fun moveRandomly(deltaTime: Float){
        //todo: fix the below. notice how everything stops working when the below is uncommented..
        if(timer >= 2f){
            direction = Direction.getRandom(World.random)
            timer = 0f
        }
        val velocity = Vector2(direction.value)
        velocity.x *= speed * deltaTime
        velocity.y *= speed * deltaTime
        transform.body.linearVelocity = velocity
        timer += deltaTime
    }
}