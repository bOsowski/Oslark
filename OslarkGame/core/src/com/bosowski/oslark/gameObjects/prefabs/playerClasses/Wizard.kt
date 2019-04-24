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

class Wizard(gender: String): GameObject(name = "player", bodyType = BodyDef.BodyType.DynamicBody) {

  init {
    val shape = PolygonShape()
    val animator = AnimatorComponent(Assets.stateAnimations["wizard$gender"]!!)
    addComponent(animator)
    shape.setAsBox(0.3f, 0.125f, Vector2(0f, 0f), 0f)
    val collider = ColliderComponent(shape, 100f)
    addComponent(collider)

    val creatureComponent = CreatureComponent(
      maxHealth = 5f,
      maxEnergy = 2f,
      level = 1,
      damage = Pair(1f,3f),
      healthPerLevel = 2f,
      energyPerLevel = 1f,
      healthRegenPerAction = 0.25f,
      energyRegenPerAction = 0.5f,
      damagePerLevel = Pair(0.25f, 0.75f)
    )
    creatureComponent.attack = ActionInterface {
      if(creatureComponent.canAttack){
        for(monster in World.instance!!.gameObjects){
          if(monster is Monster){
            if(Vector2.dst(World.instance!!.player.transform.position.x, World.instance!!.player.transform.position.y, monster.transform.position.x, monster.transform.position.y) < 1.0f && creatureComponent.canAttack){
              val damage = creatureComponent.getDamage()
              ActionableText(monster.transform.position, "%.2f".format(damage), Color.GREEN).instantiate()
              if(monster.creatureComponent.currentHealth - damage <= 0f){
                creatureComponent.currentExperience += monster.speed.toInt() + monster.creatureComponent.maxHealth.toInt()
              }
              monster.creatureComponent.currentHealth -= damage
              creatureComponent.canAttack = false
              break
            }
          }
        }
      }
    }
    addComponent(creatureComponent)

    val inputComponent = InputComponent(creatureComponent = creatureComponent, speed = 5f, collider = collider)
    addComponent(inputComponent)

    val hudComponent = HUDComponent(creatureComponent)
    addComponent(hudComponent)

    val lightComponent = LightComponent()
    addComponent(lightComponent)
  }
}