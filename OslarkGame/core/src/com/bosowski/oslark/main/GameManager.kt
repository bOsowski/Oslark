//package com.bosowski.oslark.main
//
//import com.badlogic.gdx.Gdx
//import com.badlogic.gdx.Input
//import com.badlogic.gdx.InputAdapter
//import com.badlogic.gdx.graphics.g2d.SpriteBatch
//import com.badlogic.gdx.math.Rectangle
//import com.bosowski.oslark.World
//import com.bosowski.oslark.enums.Direction
//import com.bosowski.oslark.enums.State
//import com.bosowski.oslark.gameObjects.Terrain
//import com.bosowski.oslark.generation.Dungeon
//
//import org.json.JSONObject
//
//import java.io.IOException
//import java.util.concurrent.ThreadLocalRandom
//
//import java.lang.Thread.sleep
//
//class GameManager : InputAdapter() {
//  var world = World.instance
//
//  init {
//    init()
//  }
//
//  private fun init() {
//    Gdx.input.inputProcessor = this
//  }
//
//  fun update(deltaTime: Float) {
//    handleGameInput(deltaTime)
//    world.update(deltaTime)
//  }
//
//  fun render(batch: SpriteBatch) {
//    world.render(batch)
//  }
//
//  override fun keyUp(keycode: Int): Boolean {
//    if (keycode == Input.Keys.ESCAPE || keycode == Input.Keys.BACK) {
//      Gdx.app.exit()
//    }
//    return false
//  }
//
//  fun handleGameInput(deltaTime: Float) {
//    if (Gdx.input.isKeyPressed(Input.Keys.W)) {
//      World.instance.getPlayer().move(World.instance.getPlayer().speed, deltaTime, Direction.UP)
//    }
//    if (Gdx.input.isKeyPressed(Input.Keys.S)) {
//      World.instance.getPlayer().move(World.instance.getPlayer().speed, deltaTime, Direction.DOWN)
//    }
//    if (Gdx.input.isKeyPressed(Input.Keys.D)) {
//      World.instance.getPlayer().move(World.instance.getPlayer().speed, deltaTime, Direction.RIGHT)
//    }
//    if (Gdx.input.isKeyPressed(Input.Keys.A)) {
//      World.instance.getPlayer().move(World.instance.getPlayer().speed, deltaTime, Direction.LEFT)
//    }
//    if (Gdx.input.isKeyPressed(Input.Keys.O)) {
//      World.showCollisionBoxes = !World.showCollisionBoxes
//    }
//    if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
//      world.gameObjects.clear()
//      world.instantiate(world.getPlayer())
//      val worldJson: String
//      try {
//        worldJson = Session.instance.loadWorld("characterName=" + World.instance.getPlayer().name)
//        println(worldJson)
//        val jsonObj = JSONObject(worldJson)
//        val terrain = jsonObj.getJSONArray("terrain")
//        for (tile in terrain) {
//          val terrainTile = Terrain(tile as JSONObject)
//          World.instance.instantiate(terrainTile)
//          println("Loaded terrain: $terrainTile")
//        }
//      } catch (e: IOException) {
//        e.printStackTrace()
//      }
//
//    } else if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
//      //World.instance.getPlayer().attack();
//      world.gameObjects.clear()
//      world.instantiate(world.getPlayer())
//      dungeon = Dungeon(Rectangle(-5f, -5f, 900f, 10f), 2, 7, 700, ThreadLocalRandom.current().nextInt().toLong())
//      dungeon!!.create()
//      try {
//        sleep(250)
//      } catch (e: InterruptedException) {
//        e.printStackTrace()
//      }
//
//    } else if (!Gdx.input.isKeyPressed(Input.Keys.W) && !Gdx.input.isKeyPressed(Input.Keys.S) && !Gdx.input.isKeyPressed(Input.Keys.D) && !Gdx.input.isKeyPressed(Input.Keys.A)) {
//      World.instance.getPlayer().state = State.IDLE
//    }
//
//
//    if (Gdx.input.isKeyPressed(Input.Keys.P)) {
//      World.instance.getPlayer().receiveDamage(10f)
//    }
//  }
//
//  companion object {
//    val TAG = GameManager::class.java.name
//    private var dungeon: Dungeon? = null
//  }
//
//}
