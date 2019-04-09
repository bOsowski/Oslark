package com.bosowski.oslark.screens

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.bosowski.oslark.managers.NetworkManager
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.bosowski.oslark.gameObjects.GameObject

class CharacterSelectionScreen(game: Game) : AbstractGameScreen(game) {
  private val TAG = CharacterSelectionScreen::javaClass.name

  private val characterArray: JSONArray

  init {
    var userJson: String? = null
    try {
      userJson = NetworkManager.instance.loadUser()
    } catch (e: IOException) {
      e.printStackTrace()
    }

    Gdx.app.debug(TAG, userJson)

    println(userJson)
    val obj = JSONObject(userJson)
    println(obj.get("username"))
    characterArray = obj.getJSONArray("characters")
    setUpUI()
  }

  override fun setUpUI() {
    //todo: Fix this.
    val label = Label("Choose your character", fieldSkins)
    label.setPosition((Gdx.graphics.width / 3).toFloat(), (Gdx.graphics.height - 100).toFloat())
    stage.addActor(label)

    for ((i, character) in characterArray.withIndex()) {
      val playerData = character as JSONObject
      val characterButton = TextButton(playerData.getString("name") + " " + playerData.get("characterClass"), fieldSkins)
      //characters[player.name] = player
      characterButton.setPosition(label.x, label.y - 50 * (i + 1))
      characterButton.addListener(object : ClickListener() {
        override fun touchDown(event: InputEvent?, x: Float, y: Float, point: Int, button: Int): Boolean {
          println("Picked " + playerData.getString("name"))
          val kClass = Class.forName("com.bosowski.oslark.gameObjects.prefabs.playerClasses.${playerData.getString("characterClass").toLowerCase().capitalize()}").kotlin
          val player = kClass.constructors.first().call(playerData.getString("gender").toLowerCase()) as GameObject
          game.screen = SeedSelectionScreen(game, player)
          return true
        }
      })
      stage.addActor(characterButton)
    }
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

  override fun hide() {}

  override fun pause() {}
}
