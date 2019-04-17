package com.bosowski.oslark.screens

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.bosowski.oslark.managers.NetworkManager
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.bosowski.oslark.Assets
import com.bosowski.oslark.World
import com.bosowski.oslark.enums.State
import com.bosowski.oslark.gameObjects.GameObject

class CharacterSelectionScreen(game: Game) : AbstractGameScreen(game) {
  private val TAG = CharacterSelectionScreen::javaClass.name

  private val characterArray: JSONArray

  init {
    World.game = game
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
    var player:GameObject? = null

    val mainTable = Table()
    stage.addActor(mainTable)
    mainTable.setFillParent(true)
    mainTable.debug = false

    val label = Label("Choose your character", fieldSkins)
    mainTable.add(label).padBottom(25f)
    mainTable.row()

    val charactersTable = Table()
    val scrollPane = ScrollPane(charactersTable, fieldSkins)
    mainTable.add(scrollPane).padBottom(50f)


    val seedGroup = Table()
    mainTable.add(seedGroup)

    val selectBox = SelectBox<String>(fieldSkins)
    seedGroup.add(selectBox)
    seedGroup.row().pad(15f)

    val submitButton = TextButton("Enter World", fieldSkins)
    seedGroup.add(submitButton)





    for ((i, character) in characterArray.withIndex()) {
      val playerData = character as JSONObject
      //val characterButton = TextButton(playerData.getString("name") + " " + playerData.get("characterClass"), fieldSkins)

      val characterTable = Table()
      characterTable.debug = false
      charactersTable.add(characterTable).pad(15f).align(-1)
      if((i+1) % 2 == 0){
        charactersTable.row()
      }
      val characterImage = Image(Assets.stateAnimations[playerData.getString("characterClass").toLowerCase()+playerData.getString("gender").toLowerCase()]!![State.IDLE]!!.getKeyFrame(0f))
      characterTable.add(characterImage).padRight(20f)

      val characterInfoGroup = Table()
      val characterName = Label(playerData.getString("name"), fieldSkins)
      characterInfoGroup.add(characterName).align(-1)
      characterInfoGroup.row()
      val characterClass = Label(playerData.getString("characterClass"), fieldSkins)
      characterInfoGroup.add(characterClass).align(-1)
      characterTable.add(characterInfoGroup)

      characterInfoGroup.addListener(object : ClickListener() {
        override fun touchDown(event: InputEvent?, x: Float, y: Float, point: Int, button: Int): Boolean {
          println("Picked " + playerData.getString("name"))
          val kClass = Class.forName("com.bosowski.oslark.gameObjects.prefabs.playerClasses.${playerData.getString("characterClass").toLowerCase().capitalize()}").kotlin
          player = kClass.constructors.first().call(playerData.getString("gender").toLowerCase()) as GameObject
          World.playerName = playerData.getString("name")

          //set up available seeds.
          val array = com.badlogic.gdx.utils.Array<String>()
          println(NetworkManager.instance.getScores(World.playerName!!))
          val scores = JSONObject(NetworkManager.instance.getScores(World.playerName!!)).getJSONArray("highscores")
          println(scores.toString())
          scores.forEach {
            val foundSeed = JSONObject(it.toString()).getLong("seed")
            if(!array.contains("#$foundSeed")){
              array.add("#$foundSeed")
            }
          }
          array.add("#"+array.size.toString() + " (new)")
          selectBox.items = array

          //set up the listener after some character is picked.
          submitButton.listeners.clear()
          submitButton.addListener(object : ClickListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, point: Int, button: Int): Boolean {
              if(player != null){
                game.screen = GameScreen(game, player!!, selectBox.selected.toString().replace("#","").replace(" (new)","").toLong())
                return true
              }
              return false
            }
          })
          return true
        }
      })
    }
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

  override fun hide() {}

  override fun pause() {}
}
