package com.bosowski.oslark

import box2dLight.RayHandler
import com.badlogic.gdx.Game
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.bosowski.oslark.components.CreatureComponent
import com.bosowski.oslark.components.TextureComponent
import com.bosowski.oslark.gameObjects.GameObject
import com.bosowski.oslark.generation.Dungeon
import com.bosowski.oslark.managers.GameRenderer
import com.bosowski.oslark.screens.GameScreen
import java.util.*
import kotlin.Comparator
import kotlin.collections.HashMap

class World
{
  val gameObjects = ArrayList<GameObject>()
  private val objectsToInstantiate = ArrayList<GameObject>()
  private val objectsToDestroy = ArrayList<GameObject>()
  var seed: Long = -1
  lateinit var random:Random
  var dungeon: Dungeon? = null
  lateinit var player: GameObject
  var playerName: String? = null

  val rays: HashMap<GameObject, Pair<Vector2?, Vector2?>> = HashMap()
  val physicsWorld:com.badlogic.gdx.physics.box2d.World = com.badlogic.gdx.physics.box2d.World(Vector2(), false)
  val rayHandler = RayHandler(physicsWorld)

  fun instantiate(gameObject: GameObject){
    objectsToInstantiate.add(gameObject)
  }

  fun destroy(gameObject: GameObject){
    objectsToDestroy.add(gameObject)
  }

  fun update(deltaTime: Float) {
    sortWorld()
    gameObjects.forEach { gameObject ->
      gameObject.getComponents().forEach {
        try{
          if((player.getComponent("CreatureComponent") as CreatureComponent).currentHealth <= 0){
            return
          }
          if(it.active) it.update(deltaTime)
        }
        catch (e: Exception){
          println("Object failed to update. This is expected in case the object was destroyed.")
        }
      }
    }

    physicsWorld.step(deltaTime, 3, 1)

    gameObjects.addAll(objectsToInstantiate)
    objectsToInstantiate.clear()
    gameObjects.removeAll(objectsToDestroy)
    objectsToDestroy.clear()
  }

  fun render(batch: SpriteBatch) {

    gameObjects.forEach { gameObject ->
      //only render objects that are on screen.
      if(Vector2.dst(gameObject.transform.position.x, gameObject.transform.position.y, GameRenderer.camera.position.x, GameRenderer.camera.position.y) <= GameRenderer.camera.viewportWidth + GameRenderer.camera.viewportHeight ){
        gameObject.getComponents().forEach {
          if(it is TextureComponent && it.active) it.render(batch)
        }
      }
    }
  }

  private fun sortWorld() {
    gameObjects.sortWith(Comparator { a, b ->
      when {
        a.transform.layer > b.transform.layer -> 1
        a.transform.layer < b.transform.layer -> -1
        a.transform.position.y < b.transform.position.y -> 1
        a.transform.position.y > b.transform.position.y -> -1
        else -> 0
      }
    })
  }

  fun renderUI(batch: SpriteBatch) {
    gameObjects.forEach { gameObject ->
      //only render objects that are on screen.
      if(Vector2.dst(gameObject.transform.position.x, gameObject.transform.position.y, GameRenderer.camera.position.x, GameRenderer.camera.position.y) <= GameRenderer.camera.viewportWidth + GameRenderer.camera.viewportHeight ){
        gameObject.getComponents().forEach {
          if(it !is TextureComponent && it.active) it.render(batch)
        }
      }
    }
  }

  fun createDungeon(){
    var successfullyCreated: Boolean
    do{
      dungeon?.clear()
      dungeon = Dungeon(Rectangle(-50f, -10f, 100f, 10f), 2, 7, 45)
      successfullyCreated = dungeon!!.create()
    }while(!successfullyCreated)
    Thread.sleep(250)
  }

  companion object {
    var instance: World? = null
  }
}


