package com.bosowski.oslark.gameObjects.prefabs.monsters

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.PolygonShape
import com.bosowski.oslark.Assets
import com.bosowski.oslark.World
import com.bosowski.oslark.components.*
import com.bosowski.oslark.enums.Direction
import com.bosowski.oslark.gameObjects.GameObject
import com.bosowski.oslark.gameObjects.prefabs.utility.ActionableText

abstract class Monster(position: Vector2, name: String, speed: Float, density: Float, scale: Vector2): GameObject(position, name = name, bodyType = BodyDef.BodyType.DynamicBody){

    var speed: Float = -1f
        set(value) {
            field = value
            steeringComponent.speed = value
        }
    var timer = 0f
    var action: ActionInterface? = null

    var animatorComponent: AnimatorComponent
    var creatureComponent: CreatureComponent
    var collider: ColliderComponent
    var steeringComponent: SteeringComponent
    var actionComponent: ActionComponent
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

        steeringComponent = SteeringComponent(speed, collider)
        addComponent(steeringComponent)

        creatureComponent = CreatureComponent(maxHealth = 1f)
        creatureComponent.attack = ActionInterface {
            if(Vector2.dst(World.instance!!.player.transform.position.x, World.instance!!.player.transform.position.y, transform.position.x, transform.position.y) < 1.0f && creatureComponent.canAttack){
                creatureComponent.canAttack = false
                val damage = creatureComponent.getDamage()
                ActionableText(World.instance!!.player.transform.position, "%.2f".format(damage), Color.RED).instantiate()

                val playerCreatureComponent = World.instance!!.player.getComponent("CreatureComponent") as CreatureComponent
                playerCreatureComponent.currentHealth -= damage


            }
        }
        creatureComponent.additionalBehaviours.add(ActionInterface {
            creatureComponent.attack!!.perform(it)
        })
        creatureComponent.onDeathAction = ActionInterface {
            if(World.instance!!.dungeon != null){
                World.instance!!.dungeon!!.killedMonsters++
                (World.instance!!.player.getComponent("HUDComponent") as HUDComponent).score += (speed * 10).toInt()
            }
        }
        addComponent(creatureComponent)

        actionComponent = ActionComponent(action)
        addComponent(actionComponent)
        this.speed = speed
    }

    fun moveRandomly(deltaTime: Float){
        if(timer >= 2f){
            direction = Direction.getRandom(World.instance!!.random)
            timer = 0f
            collider.move(direction.value, speed)
        }
        timer += deltaTime
    }
}