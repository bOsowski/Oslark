package com.bosowski.oslark

import com.badlogic.gdx.Application
import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.PolygonShape
import com.bosowski.oslark.components.*
import com.bosowski.oslark.gameObjects.GameObject
import com.bosowski.oslark.gameObjects.prefabs.monsters.Monster
import com.bosowski.oslark.gameObjects.prefabs.monsters.Skeleton
import com.bosowski.oslark.gameObjects.prefabs.playerClasses.Knight
import com.bosowski.oslark.gameObjects.prefabs.utility.ActionableText
import com.bosowski.oslark.screens.GameScreen


class Oslark : Game() {

  override fun create() {
    // Set Libgdx log level
    Gdx.app.logLevel = Application.LOG_DEBUG

    // Load assets
    Assets.init(AssetManager())

    setScreen(GameScreen(this))

    val player = Knight()
    World.player = player
    player.instantiate()

    val skeletMonster = Skeleton(position = Vector2(1f, 1f))
    skeletMonster.instantiate()

//    val demon = Demon(position = Vector2(1f,1f))
//    demon.instantiate()
  }
}
