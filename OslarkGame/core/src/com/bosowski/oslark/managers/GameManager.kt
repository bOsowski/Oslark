package com.bosowski.oslark.managers

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.bosowski.oslark.World

class GameManager : InputAdapter() {
  val TAG = GameManager::class.java.name

  init {
    Gdx.input.inputProcessor = this
  }

  fun update(deltaTime: Float) {
    World.update(deltaTime)
  }

  fun render(batch: SpriteBatch) {
    World.render(batch)
  }

  override fun keyUp(keycode: Int): Boolean {
    if (keycode == Input.Keys.ESCAPE || keycode == Input.Keys.BACK) {
      Gdx.app.exit()
    }
    return false
  }

  fun renderUI(batch: SpriteBatch) {
    World.renderUI(batch)
  }
}
