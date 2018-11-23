//package com.bosowski.oslark.screens
//
//import com.badlogic.gdx.Game
//import com.badlogic.gdx.Gdx
//import com.badlogic.gdx.graphics.GL20
//import com.badlogic.gdx.scenes.scene2d.InputEvent
//import com.badlogic.gdx.scenes.scene2d.ui.Label
//import com.badlogic.gdx.scenes.scene2d.ui.TextButton
//import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
//import com.bosowski.oslark.World
//import com.bosowski.oslark.gameObjects.Player
//import com.bosowski.oslark.main.Session
//
//import org.json.JSONArray
//import org.json.JSONObject
//
//import java.io.IOException
//import java.util.LinkedHashMap
//
//class CharacterSelectionScreen(game: Game) : AbstractGameScreen(game) {
//
//  private val characterArray: JSONArray
//
//  init {
//    var userJson: String? = null
//    try {
//      userJson = Session.instance.loadUser()
//    } catch (e: IOException) {
//      e.printStackTrace()
//    }
//
//    println(userJson)
//    val obj = JSONObject(userJson)
//    println(obj.get("username"))
//    characterArray = obj.getJSONArray("characters")
//    setUpUI()
//  }
//
//  override fun setUpUI() {
//    val label = Label("Choose your character", fieldSkins)
//    label.setPosition((Gdx.graphics.width / 3).toFloat(), (Gdx.graphics.height - 100).toFloat())
//    stage.addActor(label)
//
//    val characters = LinkedHashMap<String, Player>()
//
//    var i = 0
//    for (character in characterArray) {
//      val player = Player(character as JSONObject)
//      val characterButton = TextButton(player.name + "(level " + player.level + " " + player.characterClass, fieldSkins)
//      characters[player.name] = player
//      characterButton.setPosition(label.x, label.y - 50 * (i + 1))
//      characterButton.addListener(object : ClickListener() {
//        override fun touchDown(event: InputEvent?, x: Float, y: Float, point: Int, button: Int): Boolean {
//          println("Picked " + player.name)
//          World.instance.setPlayer(characters[player.name]!!)
//          //                    String worldJson = null;
//          //                    try {
//          //                        worldJson = Session.instance.loadWorld("characterName=" + World.instance.getPlayer().getName());
//          //
//          //                    } catch (IOException e) {
//          //                        e.printStackTrace();
//          //                    }
//          //                    System.out.println(worldJson);
//          //                    JSONObject jsonObj = new JSONObject(worldJson);
//          //                    JSONArray terrain = jsonObj.getJSONArray("terrain");
//          //                    for (Object tile : terrain) {
//          //                        Terrain terrainTile = new Terrain((JSONObject) tile);
//          //                        World.instance.instantiate(terrainTile);
//          //                        System.out.println("Loaded terrain: " + terrainTile);
//          //                    }
//
//          game.screen = GameScreen(game)
//          return true
//        }
//      })
//      stage.addActor(characterButton)
//      i++
//    }
//  }
//
//  override fun render(deltaTime: Float) {
//    // Clear the buffer
//    Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
//    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
//
//    stage.act(deltaTime)
//    stage.draw()
//  }
//
//  override fun resize(width: Int, height: Int) {
//    super.resize(width, height)
//  }
//
//  override fun show() {
//    Gdx.input.inputProcessor = stage
//  }
//
//  override fun hide() {
//
//  }
//
//  override fun pause() {
//
//  }
//}
