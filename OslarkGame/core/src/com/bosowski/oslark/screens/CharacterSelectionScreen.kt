package com.bosowski.oslark.screens

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.bosowski.oslark.managers.NetworkManager
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

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
//    val label = Label("Choose your character", fieldSkins)
//    label.setPosition((Gdx.graphics.width / 3).toFloat(), (Gdx.graphics.height - 100).toFloat())
//    stage.addActor(label)
//
//    val characters = LinkedHashMap<String, Knight>()
//
//    for ((i, character) in characterArray.withIndex()) {
//      val player = Knight(character as JSONObject)
//      val characterButton = TextButton(player.name + "(level " + player.level + " " + player.characterClass, fieldSkins)
//      characters[player.name] = player
//      characterButton.setPosition(label.x, label.y - 50 * (i + 1))
//      characterButton.addListener(object : ClickListener() {
//        override fun touchDown(event: InputEvent?, x: Float, y: Float, point: Int, button: Int): Boolean {
//          println("Picked " + player.name)
//          game.screen = GameScreen(game)
//          return true
//        }
//      })
//      stage.addActor(characterButton)
//    }
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
