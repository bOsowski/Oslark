package com.bosowski.oslark

import com.badlogic.gdx.Application
import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.math.Vector2
import com.bosowski.oslark.gameObjects.prefabs.monsters.Skeleton
import com.bosowski.oslark.gameObjects.prefabs.playerClasses.Knight
import com.bosowski.oslark.screens.GameScreen
import com.bosowski.oslark.screens.LoginScreen


class Oslark : Game() {

  override fun create() {
    // Set Libgdx log level
    Gdx.app.logLevel = Application.LOG_DEBUG

    // Load assets
    Assets.init(AssetManager())

    setScreen(LoginScreen(this))



//    val demon = Demon(position = Vector2(1f,1f))
//    demon.instantiate()
  }
}
