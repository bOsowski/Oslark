package com.bosowski.oslark

import box2dLight.PointLight
import com.badlogic.gdx.Application
import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.PolygonShape
import com.bosowski.oslark.components.AnimatorComponent
import com.bosowski.oslark.components.ColliderComponent
import com.bosowski.oslark.components.InputComponent
import com.bosowski.oslark.gameObjects.GameObject
import com.bosowski.oslark.gameObjects.prefabs.Demon
import com.bosowski.oslark.gameObjects.prefabs.Monster
import com.bosowski.oslark.gameObjects.prefabs.Skeleton
import com.bosowski.oslark.screens.GameScreen

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
    val collider = ColliderComponent(BodyDef.BodyType.DynamicBody, shape)
    World.player.addComponent(collider)
    val inputComponent = InputComponent(animator = animator, speed = 250f)
    World.player.addComponent(inputComponent)

    //light
    //todo(fix lighting filter. Currently raycasts can see the light..)
    World.rayHandler.setAmbientLight(1f)
//    val playerLight = PointLight(World.rayHandler, 5000, Color(0f,0f,0f,1f), 15f, 0f, 0f)
//    playerLight.attachToBody(World.player.transform.body, 0f, -animator.dimension.y/2f)
//    playerLight.setSoftnessLength(5f)
//    playerLight.ignoreAttachedBody = true
//    playerLight.setContactFilter(0,0,0)

//    val skeletMonster = Skeleton(position = Vector2(1f,1f))
//    skeletMonster.instantiate()

//    val demon = Demon(position = Vector2(1f,1f))
//    demon.instantiate()
  }
}
