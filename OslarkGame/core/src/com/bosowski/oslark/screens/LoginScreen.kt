package com.bosowski.oslark.screens

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.ui.TextField
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.bosowski.oslark.playerDomains.User
import com.bosowski.oslark.main.Session

import java.io.IOException


class LoginScreen(game: Game) : AbstractGameScreen(game) {

  lateinit var username: TextField
  lateinit var password: TextField
  lateinit var login: TextButton


  init {
    setUpUI()
  }

  override fun render(deltaTime: Float) {
    // Clear the buffer
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
        println("CLICKING")
        try {
          Session.instance.login(username.text, password.text)
          game.screen = CharacterSelectionScreen(game)
        } catch (e: IOException) {
          e.printStackTrace()
        }

        return true
      }
    })
  }
}
