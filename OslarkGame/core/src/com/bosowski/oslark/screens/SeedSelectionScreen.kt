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

class SeedSelectionScreen(game: Game, var player: GameObject, var playerData: JSONObject) : AbstractGameScreen(game) {

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
    val selectBox = SelectBox<String>(fieldSkins)
    World.playerName = playerData.getString("name")
    val array = com.badlogic.gdx.utils.Array<String>()
    println(NetworkManager.instance.getScores(World.playerName))
    val scores = JSONObject(NetworkManager.instance.getScores(World.playerName)).getJSONArray("highscores")
    println(scores.toString())
    scores.forEach {
      val foundSeed = JSONObject(it.toString()).getLong("seed")
      if(!array.contains("#$foundSeed")){
        array.add("#$foundSeed")
      }
    }
    array.add("#"+array.size.toString() + " (new)")
    selectBox.items = array
    selectBox.width = 100f
    selectBox.setPosition((Gdx.graphics.width / 3).toFloat(), (Gdx.graphics.height - 100).toFloat())
    stage.addActor(selectBox)

    val enterWorldLabel = Label("Enter world", fieldSkins)
    enterWorldLabel.setPosition(selectBox.x - 100, selectBox.y)
    stage.addActor(enterWorldLabel)

    val submitButton = TextButton("submit", fieldSkins)
    //characters[player.name] = player
    submitButton.setPosition(selectBox.x, selectBox.y - 50)
    submitButton.addListener(object : ClickListener() {
      override fun touchDown(event: InputEvent?, x: Float, y: Float, point: Int, button: Int): Boolean {
        game.screen = GameScreen(game, player, selectBox.selected.toString().replace("#","").replace(" (new)","").toLong())
        return true
      }
    })
    stage.addActor(submitButton)
    //game.screen = GameScreen(game, player, 1)
  }
}
