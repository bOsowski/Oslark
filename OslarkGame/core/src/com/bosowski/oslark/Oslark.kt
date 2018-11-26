package com.bosowski.oslark

import com.badlogic.gdx.Application
import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.EdgeShape
import com.badlogic.gdx.physics.box2d.PolygonShape
import com.badlogic.gdx.physics.box2d.Shape
import com.bosowski.oslark.components.AnimatorComponent
import com.bosowski.oslark.components.ColliderComponent
import com.bosowski.oslark.components.InputComponent
import com.bosowski.oslark.gameObjects.GameObject
import com.bosowski.oslark.gameObjects.prefabs.SkeletMonster
import com.bosowski.oslark.screens.GameScreen

class Oslark : Game() {

  override fun create() {
    // Set Libgdx log level
    Gdx.app.logLevel = Application.LOG_DEBUG

    // Load assets
    Assets.init(AssetManager())

    setScreen(GameScreen(this))

    val skeletMonster = SkeletMonster(position = Vector2(1f,1f))
    skeletMonster.instantiate()

    World.player = GameObject(name = "player")
    World.player.instantiate()
    val shape = PolygonShape()
    val animator = AnimatorComponent(Assets.stateAnimations["knightfemale"]!!)
    World.player.addComponent(animator)
    shape.setAsBox(0.3f, 0.125f, Vector2(0f, -animator.dimension.y/2f), 0f)
    val collider = ColliderComponent(BodyDef.BodyType.DynamicBody, shape)
    World.player.addComponent(collider)
    val inputComponent = InputComponent(animator = animator, speed = 250f)
    World.player.addComponent(inputComponent)
  }
}
