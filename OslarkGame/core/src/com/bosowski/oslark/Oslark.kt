package com.bosowski.oslark

import com.badlogic.gdx.Application
import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.PolygonShape
import com.bosowski.oslark.components.*
import com.bosowski.oslark.gameObjects.GameObject
import com.bosowski.oslark.gameObjects.prefabs.monsters.Monster
import com.bosowski.oslark.gameObjects.prefabs.monsters.Skeleton
import com.bosowski.oslark.gameObjects.prefabs.utility.ActionableText
import com.bosowski.oslark.screens.GameScreen
import com.bosowski.oslark.utils.Util
import java.util.*


class Oslark : Game() {

  override fun create() {
    // Set Libgdx log level
    Gdx.app.logLevel = Application.LOG_DEBUG

    // Load assets
    Assets.init(AssetManager())

    setScreen(GameScreen(this))

    World.player = GameObject(name = "player")
    World.player.instantiate()
    val shape = PolygonShape()
    val animator = AnimatorComponent(Assets.stateAnimations["knightfemale"]!!)
    World.player.addComponent(animator)
    shape.setAsBox(0.3f, 0.125f, Vector2(0f, 0f), 0f)
    val collider = ColliderComponent(BodyDef.BodyType.DynamicBody, shape, 100f)
    World.player.addComponent(collider)
    val inputComponent = InputComponent(animator = animator, speed = 5f, collider = collider)
    World.player.addComponent(inputComponent)

    val creatureComponent = CreatureComponent(maxHealth = 10f, level = 1, damage = Pair(1f,3f))
    creatureComponent.attack = ActionInterface {
      World.gameObjects.forEach lit@{monster ->
        if(monster is Monster){
          // val attackArea = Rectangle(World.player.transform.body.position.x + collider.direction!!.x, World.player.transform.body.position.y + collider.direction!!.y, 1f, 1f)
          //if (attackArea.contains(monster.transform.body.position)) {
          if(Vector2.dst(World.player.transform.position.x, World.player.transform.position.y, monster.transform.position.x, monster.transform.position.y) < 300.0f){
            val damage = creatureComponent.getDamage()
            ActionableText(monster.transform.position, "%.2f".format(damage), Color.GREEN).instantiate()
              println("Attacked " + monster.name+".")
              monster.creatureComponent.currentHealth -= damage
              return@lit
          }
        }
      }
    }
    World.player.addComponent(creatureComponent)

    val hudComponent = HUDComponent(creatureComponent)
    World.player.addComponent(hudComponent)

    //light
    //todo(fix lighting filter. Currently raycasts can see the light..)
    World.rayHandler.setAmbientLight(1f)
//    val playerLight = PointLight(World.rayHandler, 5000, Color(0f,0f,0f,1f), 15f, 0f, 0f)
//    playerLight.attachToBody(World.player.transform.body, 0f, -animator.dimension.y/2f)
//    playerLight.setSoftnessLength(5f)
//    playerLight.ignoreAttachedBody = true
//    playerLight.setContactFilter(0,0,0)

    val skeletMonster = Skeleton(position = Vector2(1f, 1f))
    skeletMonster.instantiate()

//    val demon = Demon(position = Vector2(1f,1f))
//    demon.instantiate()
  }
}
