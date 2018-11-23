//package com.bosowski.oslark
//
//import com.badlogic.gdx.Game
//import com.badlogic.gdx.graphics.Color
//import com.badlogic.gdx.graphics.g2d.SpriteBatch
//import com.badlogic.gdx.graphics.glutils.ShapeRenderer
//import com.badlogic.gdx.math.Rectangle
//import com.badlogic.gdx.math.Vector3
//import com.bosowski.oslark.enums.State
//import com.bosowski.oslark.gameObjects.Creature
//import com.bosowski.oslark.gameObjects.GameObject
//import com.bosowski.oslark.gameObjects.Player
//import com.bosowski.oslark.generation.DungeonCell
//
//import java.util.ArrayList
//import java.util.HashMap
//
///**
// * Created by bOsowski on 27/01/2018.
// *
// *
// * World is a singleton.
// * It is responsible for game objects.
// */
//
//class World private constructor()//private empty constructor. -> allows to only instantiate the class from within itself.
//{
//  var gameObjects = ArrayList<GameObject>()
//  val gameObjectsById = HashMap<Int, GameObject>()
//  internal lateinit var player: Player
//
//  private val sr = ShapeRenderer()
//
//  fun update(deltaTime: Float) {
//    sortWorld()
//    for (gameObject in gameObjects) {
//      if (gameObject is Creature && gameObject.state == State.DIE) {
//        continue
//      }
//      if (gameObject is Creature) {
//        gameObject.collisionBox.setPosition(gameObject.position.x - gameObject.origin.x / 2, gameObject.position.y - gameObject.origin.y)
//        if (gameObject.collides()) {
//          gameObject.reactOnEnvironment(deltaTime)
//        }
//      }
//      gameObject.update(deltaTime)
//    }
//  }
//
//  fun render(batch: SpriteBatch) {
//    for (gameObject in gameObjects) {
//      gameObject.render(batch)
//      if (showCollisionBoxes) {
//        showCollision(gameObject, batch)
//      }
//    }
//  }
//
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
//
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
//
//  private fun sortWorld() {
//    gameObjects.sortWith(Comparator { a, b ->
//     when {
//        a.position.z > b.position.z -> 1
//        a.position.z < b.position.z -> -1
//        a.position.y < b.position.y -> 1
//        a.position.y > b.position.y -> -1
//        else -> 0
//      }
//    })
//  }
//
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
//
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
//
//  fun getPlayer(): Player {
//    return player
//  }
//
//  fun setPlayer(player: Player) {
//    this.player = player
//    gameObjects.add(player)
//  }
//
//  companion object {
//    var showCollisionBoxes = false
//    private var projectionMatrixSet = false
//
//    val instance = World()
//  }
//}
