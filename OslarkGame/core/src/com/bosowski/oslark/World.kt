package com.bosowski.oslark

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.World
import com.bosowski.oslark.gameObjects.GameObject
import java.util.ArrayList
import kotlin.Comparator

object World
{
  var gameObjects = ArrayList<GameObject>()
  val physicsWorld = com.badlogic.gdx.physics.box2d.World(Vector2(), true)

  fun update(deltaTime: Float) {
    sortWorld()
    gameObjects.forEach { gameObject ->
      gameObject.getComponents().forEach {
        if(it.active) it.update(deltaTime)
      }
    }
    physicsWorld.step(deltaTime, 6, 2)
  }

  fun render(batch: SpriteBatch) {
    gameObjects.forEach { gameObject ->
      gameObject.getComponents().forEach {
        if(it.active) it.render(batch)
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

//      private val sr = ShapeRenderer()

//  private fun showCollision(gameObject: GameObject, batch: SpriteBatch) {
//    batch.end()
//    if (gameObject.collisionBox != null && gameObject !is DungeonCell) {
//      if (!projectionMatrixSet) {
//        sr.projectionMatrix = batch.projectionMatrix
//        projectionMatrixSet = true
//      }
//      sr.begin(ShapeRenderer.ShapeType.Filled)
//      sr.color = Color.RED
//      sr.rect(gameObject.collisionBox.x, gameObject.collisionBox.y, gameObject.collisionBox.width, gameObject.collisionBox.height)
//      sr.end()
//    }
//    batch.begin()
//  }

//  fun instantiate(gameObject: GameObject) {
//    gameObjects.add(gameObject)
//
//    if (gameObject.javaClass == Player::class.java) {
//      player = gameObject as Player
//    }
//  }
//
//  fun destroy(gameObject: GameObject) {
//    gameObjects.remove(gameObject)
//  }



//  fun willCollide(subject: GameObject, futurePos: Vector3): Boolean {
//    for (other in gameObjects) {
//      if (subject !== other && subject.collides() && other.collides()) {
//        val subjectFutureRect = Rectangle(futurePos.x - subject.origin.x / 2, futurePos.y - subject.origin.y, subject.collisionBox.width, subject.collisionBox.height)
//        if (subjectFutureRect.overlaps(other.collisionBox)) {
//          return true
//        }
//      }
//    }
//    return false
//  }

//  /**
//   * @param bounds
//   * @return
//   */
//  @Deprecated("")
//  fun isOnTerrain(bounds: Rectangle): Boolean {
//    for (terrain in gameObjects) {
//      if (terrain is DungeonCell) {
//        if (terrain.collisionBox.contains(bounds)) {
//          return true
//        }
//      }
//    }
//    return false
//  }

//  fun getPlayer(): Player {
//    return player
//  }

//  fun setPlayer(player: Player) {
//    this.player = player
//    gameObjects.add(player)
//  }
}
