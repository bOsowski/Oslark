package com.bosowski.oslark.screens

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.bosowski.oslark.gameObjects.GameObject
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox
import com.bosowski.oslark.World
import com.bosowski.oslark.managers.NetworkManager
import org.json.JSONObject

class EndGameScreen(game: Game, val score: Long) : AbstractGameScreen(game) {

  init {
    World.game = game
    setUpUI()
  }

  override fun render(deltaTime: Float) {
    Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
    stage.act(deltaTime)
    stage.draw()
  }

  override fun show() {
    Gdx.input.inputProcessor = stage
  }

  override fun hide() {

  }

  override fun pause() {

  }

  override fun setUpUI() {
    val scoreLabel = Label("Congratulations on completing the level with score $score!", fieldSkins)
    scoreLabel.setPosition((Gdx.graphics.width / 3).toFloat(), (Gdx.graphics.height - 100).toFloat())

    val backButton = TextButton("Back", fieldSkins)
    backButton.setPosition(scoreLabel.x, scoreLabel.y - 50f)
    val quitButton = TextButton("Quit", fieldSkins)
    quitButton.setPosition(backButton.x, backButton.y - 50f)

    backButton.addListener(object : ClickListener() {
      override fun touchDown(event: InputEvent?, x: Float, y: Float, point: Int, button: Int): Boolean {
        game.screen = CharacterSelectionScreen(game)
        return true
      }
    })

    quitButton.addListener(object : ClickListener() {
      override fun touchDown(event: InputEvent?, x: Float, y: Float, point: Int, button: Int): Boolean {
        System.exit(0)
        return true
      }
    })

    stage.addActor(scoreLabel)
    stage.addActor(backButton)
    stage.addActor(quitButton)
  }
}
