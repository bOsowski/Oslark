package com.bosowski.oslark

import com.badlogic.gdx.Application
import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.math.Vector2
import com.bosowski.oslark.components.AnimatorComponent
import com.bosowski.oslark.components.ColliderComponent
import com.bosowski.oslark.components.InputComponent
import com.bosowski.oslark.gameObjects.GameObject
import com.bosowski.oslark.screens.GameScreen

class Oslark : Game() {

  override fun create() {
    // Set Libgdx log level
    Gdx.app.logLevel = Application.LOG_DEBUG

    // Load assets
    Assets.init(AssetManager())

    setScreen(GameScreen(this))

    val testObject = GameObject()
    testObject.instantiate()
    val collider = ColliderComponent(width = 0.6f, height = 0.2f, centre = Vector2(testObject.transform.position.x, testObject.transform.position.y-0.5f))
    testObject.addComponent(collider)
    val animator = AnimatorComponent(Assets.stateAnimations["knightfemale"]!!)
    testObject.addComponent(animator)
    val inputComponent = InputComponent(animator = animator, speed = 5f)
    testObject.addComponent(inputComponent)
  }
}
