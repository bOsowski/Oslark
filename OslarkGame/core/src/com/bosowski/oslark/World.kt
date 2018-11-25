package com.bosowski.oslark

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.bosowski.oslark.gameObjects.GameObject
import com.bosowski.oslark.managers.GameRenderer
import java.util.ArrayList
import kotlin.Comparator

object World
{
  private val gameObjects = ArrayList<GameObject>()
  private val objectsToInstantiate = ArrayList<GameObject>()
  private val objectsToDestroy = ArrayList<GameObject>()

  val physicsWorld = com.badlogic.gdx.physics.box2d.World(Vector2(), true)

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
  }

  fun render(batch: SpriteBatch) {
    gameObjects.forEach { gameObject ->
      //only render objects that are on screen.
      if(Vector2.dst(gameObject.transform.position.x, gameObject.transform.position.y, GameRenderer.camera.position.x, GameRenderer.camera.position.y) <= 20 ){
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
