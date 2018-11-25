package com.bosowski.oslark

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.EdgeShape
import com.badlogic.gdx.physics.box2d.Fixture
import com.badlogic.gdx.physics.box2d.RayCastCallback
import com.badlogic.gdx.physics.box2d.Transform
import com.bosowski.oslark.components.ColliderComponent
import com.bosowski.oslark.components.TextureComponent
import com.bosowski.oslark.gameObjects.GameObject
import com.bosowski.oslark.managers.GameRenderer
import java.util.ArrayList
import javax.swing.Renderer
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
      if(Vector2.dst(gameObject.transform.position.x, gameObject.transform.position.y, GameRenderer.camera.position.x, GameRenderer.camera.position.y) <= 100f){
        val gameObjectPosition = Vector2(gameObject.transform.position)

        var texture: TextureComponent? = null
        if(gameObject.getComponent("TextureComponent") != null){
          texture = gameObject.getComponent("TextureComponent") as TextureComponent
        }
        if(texture != null){
          gameObjectPosition.add(texture.dimension)
        }

        var closestFraction = 1f
        var collisionPoint: Vector2? = null
        val callback = RayCastCallback { fixture, point, normal, fraction ->
          if (fraction < closestFraction) {
            closestFraction = fraction
              collisionPoint = point
          }
          1f
        }

        val position = Vector2(GameRenderer.camera.position.x, GameRenderer.camera.position.y)
        if(position != gameObjectPosition){
          physicsWorld.rayCast(callback, position, gameObjectPosition)
        }

        if(collisionPoint == null || Vector2.dst(gameObjectPosition.x, gameObjectPosition.y, collisionPoint!!.x, collisionPoint!!.y) < 0.5f){
          gameObject.getComponents().forEach {
            if(it.active) it.render(batch)
          }
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
