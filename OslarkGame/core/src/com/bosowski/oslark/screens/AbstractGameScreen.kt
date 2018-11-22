package com.bosowski.oslark.screens

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.utils.viewport.FitViewport
import com.bosowski.oslark.main.Assets

abstract class AbstractGameScreen(protected var game: Game) : Screen {
  protected var stage: Stage
  protected var fieldSkins = Skin(Gdx.files.internal("uiskin.json"))

  init {
    stage = Stage()
  }

  abstract override fun render(deltaTime: Float)

  override fun resize(width: Int, height: Int) {
    stage = Stage()
    setUpUI()
    show()
  }

  abstract override fun show()

  abstract override fun hide()

  abstract override fun pause()

  protected abstract fun setUpUI()

  override fun resume() {
    Assets.instance.init(AssetManager())
  }

  override fun dispose() {
    Assets.instance.dispose()
  }

}
