package com.bosowski.oslark

import box2dLight.RayHandler
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.bosowski.oslark.gameObjects.GameObject
import com.bosowski.oslark.gameObjects.prefabs.Monster
import com.bosowski.oslark.generation.Dungeon
import com.bosowski.oslark.managers.GameRenderer
import java.util.*
import java.util.concurrent.ThreadLocalRandom
import kotlin.Comparator
import kotlin.collections.HashMap

object World
{
  val gameObjects = ArrayList<GameObject>()
  private val objectsToInstantiate = ArrayList<GameObject>()
  private val objectsToDestroy = ArrayList<GameObject>()
  val random = Random( /*ThreadLocalRandom.current().nextLong()*/ 1 ) //todo: change this later to get seed fro server.
  var dungeon: Dungeon? = null
  lateinit var player: GameObject

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
        if(it.active) it.update(deltaTime)
      }
    }
    
    gameObjects.addAll(objectsToInstantiate)
    objectsToInstantiate.clear()
    gameObjects.removeAll(objectsToDestroy)
    objectsToDestroy.clear()

    physicsWorld.step(deltaTime, 3, 1)
    rayHandler.updateAndRender()
  }

  fun render(batch: SpriteBatch) {
    gameObjects.forEach { gameObject ->
      //only render objects that are on screen.
      if(Vector2.dst(gameObject.transform.position.x, gameObject.transform.position.y, GameRenderer.camera.position.x, GameRenderer.camera.position.y) <= GameRenderer.camera.viewportWidth + GameRenderer.camera.viewportHeight ){
        gameObject.getComponents().forEach {
          if(it.active) it.render(batch)
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
}
