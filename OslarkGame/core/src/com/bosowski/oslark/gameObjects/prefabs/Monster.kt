package com.bosowski.oslark.gameObjects.prefabs

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.PolygonShape
import com.bosowski.oslark.Assets
import com.bosowski.oslark.World
import com.bosowski.oslark.components.*
import com.bosowski.oslark.enums.Direction
import com.bosowski.oslark.gameObjects.GameObject

abstract class Monster(position: Vector2, name: String, speed: Float, density: Float, scale: Vector2): GameObject(position, name = name){

    var speed: Float = -1f
    set(value) {
        field = value
        steeringComponent.speed = value
    }
    var timer = 0f
    var action: ActionInterface? = null

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

        collider = ColliderComponent(BodyDef.BodyType.DynamicBody, shape, density)
        addComponent(collider)

        steeringComponent = SteeringComponent(collider.body, speed, World.player.transform.position, collider)
        addComponent(steeringComponent)

        aiComponent = AIComponent(action)
        addComponent(aiComponent)
        this.speed = speed
    }

    fun moveRandomly(deltaTime: Float){
        //todo: fix the below. notice how everything stops working when the below is uncommented..
        if(timer >= 2f){
            direction = Direction.getRandom(World.random)
            timer = 0f
            collider.move(direction.value, speed)
        }
        //val velocity = Vector2(direction.value)
        timer += deltaTime
    }
}