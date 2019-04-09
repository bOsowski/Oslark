package com.bosowski.oslark.screens

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.ui.TextField
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.bosowski.oslark.gameObjects.GameObject
import com.bosowski.oslark.managers.NetworkManager
import java.io.IOException


class SeedSelectionScreen(game: Game, var player: GameObject) : AbstractGameScreen(game) {

  lateinit var username: TextField
  lateinit var password: TextField
  lateinit var login: TextButton

  init {
    setUpUI()
  }

  override fun render(deltaTime: Float) {
    Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

    stage.act(deltaTime)
    stage.draw()
    game.screen = GameScreen(game, player, 1)
  }

  override fun show() {
    Gdx.input.inputProcessor = stage
  }

  override fun hide() {

  }

  override fun pause() {

  }

  override fun setUpUI() {
    username = TextField("admin", fieldSkins)
    password = TextField("admin", fieldSkins)
    login = TextButton("Login", fieldSkins)
    username.setPosition((Gdx.graphics.width / 2).toFloat(), (Gdx.graphics.height / 2).toFloat())
    password.setPosition(username.x, username.y - 50)
    login.setPosition(password.x, password.y - 50)
    stage.addActor(username)
    stage.addActor(password)
    stage.addActor(login)
    login.addListener(object : ClickListener() {
      override fun touchDown(event: InputEvent?, x: Float, y: Float, point: Int, button: Int): Boolean {
        try {
          NetworkManager.instance.login(username.text, password.text)
          game.screen = CharacterSelectionScreen(game)
        } catch (e: IOException) {
          //todo: Display some message to user indicating why login was not successful.
          e.printStackTrace()
        }
        return true
      }
    })
  }
}
