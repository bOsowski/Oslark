package com.bosowski.oslark.screens

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.bosowski.oslark.Assets
import com.bosowski.oslark.World
import com.bosowski.oslark.managers.NetworkManager
import java.io.IOException
import java.lang.Exception


class LoginScreen(game: Game) : AbstractGameScreen(game) {

  init {
    setUpUI()
  }

  override fun render(deltaTime: Float) {
    Gdx.gl.glClearColor(49f/255, 38f/255, 45f/255, 1f)
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
    val backgroundContainer = Container<Image>()
    stage.addActor(backgroundContainer)
    backgroundContainer.setFillParent(true)
    val background = Image(Assets.textures["background"])
    backgroundContainer.actor = background
    stage.addActor(backgroundContainer)

    val mainTable = Table()
    stage.addActor(mainTable)
    mainTable.setFillParent(true)
    mainTable.debug = false

    val usernameGroup = Table()
    val usernameLabel = Label("Username:", fieldSkins)
    val usernameField = TextField("admin", fieldSkins)
    //usernameGroup.setPosition((Gdx.graphics.width / 2).toFloat(), (Gdx.graphics.height / 2).toFloat())
    usernameGroup.add(usernameLabel).width(100f)
    usernameGroup.add(usernameField)
    mainTable.add(usernameGroup).padBottom(15f)

    mainTable.row()
    val passwordGroup = Table()
    val passwordLabel = Label("Password:", fieldSkins)
    val passwordField = TextField("admin", fieldSkins)
    passwordField.isPasswordMode = true
    passwordField.setPasswordCharacter('*')
    //usernameGroup.setPosition((Gdx.graphics.width / 2).toFloat(), (Gdx.graphics.height / 2).toFloat())
    passwordGroup.add(passwordLabel).width(100f)
    passwordGroup.add(passwordField)
    mainTable.add(passwordGroup).padBottom(25f)

    mainTable.row()
    val loginButton = TextButton("Login", fieldSkins)
    mainTable.add(loginButton).width(100f)

    loginButton.addListener(object : ClickListener() {
      override fun touchDown(event: InputEvent?, x: Float, y: Float, point: Int, button: Int): Boolean {
        try {
          println("login result")
          if(NetworkManager.instance.login(usernameField.text, passwordField.text).contains("<title>        Login    </title>")){
            throw Exception("Invalid Credentials")
          }
          game.screen = CharacterSelectionScreen(game)
        } catch (e: IOException) {
          //todo: Display some message to user indicating why login was not successful.
          e.printStackTrace()
        } catch(e: Exception){
          e.printStackTrace()
        }
        return true
      }
    })

  }
}
