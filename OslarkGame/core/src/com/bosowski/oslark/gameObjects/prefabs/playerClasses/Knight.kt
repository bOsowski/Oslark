package com.bosowski.oslark.gameObjects.prefabs.playerClasses

import box2dLight.PointLight
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.PolygonShape
import com.bosowski.oslark.Assets
import com.bosowski.oslark.World
import com.bosowski.oslark.components.*
import com.bosowski.oslark.gameObjects.GameObject
import com.bosowski.oslark.gameObjects.prefabs.monsters.Monster
import com.bosowski.oslark.gameObjects.prefabs.utility.ActionableText

class Knight: GameObject(name = "player", bodyType = BodyDef.BodyType.DynamicBody) {

  init {
    val shape = PolygonShape()
    val animator = AnimatorComponent(Assets.stateAnimations["knightfemale"]!!)
    addComponent(animator)
    shape.setAsBox(0.3f, 0.125f, Vector2(0f, 0f), 0f)
    val collider = ColliderComponent(BodyDef.BodyType.DynamicBody, shape, 100f)
    addComponent(collider)
    val inputComponent = InputComponent(animator = animator, speed = 5f, collider = collider)
    addComponent(inputComponent)

    val creatureComponent = CreatureComponent(maxHealth = 10f, maxEnergy = 2f, level = 1, damage = Pair(1f,3f))
    creatureComponent.attack = ActionInterface {
      if(creatureComponent.canAttack){
        for(monster in World.gameObjects){
          if(monster is Monster){
            if(Vector2.dst(World.player.transform.position.x, World.player.transform.position.y, monster.transform.position.x, monster.transform.position.y) < 1.0f && creatureComponent.canAttack){
              val damage = creatureComponent.getDamage()
              ActionableText(monster.transform.position, "%.2f".format(damage), Color.GREEN).instantiate()
              if(monster.creatureComponent.currentHealth - damage <= 0f){
                creatureComponent.currentExperience += monster.speed.toInt() + monster.creatureComponent.maxHealth.toInt()

              }
              monster.creatureComponent.currentHealth -= damage
              //creatureComponent.canAttack = false
              break
            }
          }
        }
      }
    }
    addComponent(creatureComponent)

    val hudComponent = HUDComponent(creatureComponent)
    addComponent(hudComponent)

    //light
    World.rayHandler.setAmbientLight(0f)
    val playerLight = PointLight(World.rayHandler, 500, Color(0f,0f,0f,1f), 15f, 0f, 0f)
    playerLight.attachToBody(transform.body, 0f, 0f)
    playerLight.setSoftnessLength(5f)
    playerLight.ignoreAttachedBody = true
  }
}