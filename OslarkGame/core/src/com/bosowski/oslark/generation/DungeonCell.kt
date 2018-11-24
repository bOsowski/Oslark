//package com.bosowski.oslark.generation
//
//import com.badlogic.gdx.math.Vector2
//import com.badlogic.gdx.math.Vector3
//import com.bosowski.oslark.World
//import com.bosowski.oslark.components.TextureComponent
//import com.bosowski.oslark.enums.Direction
//import com.bosowski.oslark.gameObjects.GameObject
//import com.bosowski.oslark.gameObjects.Terrain
//import com.bosowski.oslark.main.Assets
//import com.bosowski.oslark.utils.Util
//
//import java.util.ArrayList
//import java.util.HashMap
//import java.util.Random
//
//class DungeonCell(position: Vector2, collides: Boolean, private val random: Random)
//  : Terrain(if (random.nextFloat() <= chanceOfDifferentFloor) "floor" + Util.randomInt(random, 4, 11) else "floor4", position, collides) {
//
//  val cell = GameObject()
//
//  internal var walls = HashMap<Direction, GameObject>()
//
//  fun instantiate() {
//    World.instance.instantiate(this)
//    for (wall in walls.values) {
//      World.instance.instantiate(wall)
//    }
//  }
//
//  fun setUpWalls(otherCells: HashMap<Vector2, DungeonCell>) {
//    for (direction in Direction.directions) {
//      if (!otherCells.containsKey(Vector2(cell.transform.position).add(direction.value))) {
//        addWall(direction)
//      }
//    }
//  }
//
//  fun clear() {
//    for (wall in walls.values) {
//      wall.destroy()
//    }
//    walls.clear()
//    cell.destroy()
//  }
//
//  fun getNeighbours(otherCells: HashMap<Vector2, DungeonCell>): ArrayList<DungeonCell> {
//    val neighbours = ArrayList<DungeonCell>()
//    for (direction in Direction.directions) {
//      val neighbourPosition = vector2.add(direction.value)
//      if (otherCells.containsKey(neighbourPosition)) {
//        neighbours.add(otherCells[neighbourPosition]!!)
//      }
//    }
//    return neighbours
//  }
//
//  private fun addWall(direction: Direction) {
//    val wall = GameObject()
//
//    when (direction) {
//      Direction.LEFT -> {
//        wall.addComponent(TextureComponent(Assets.textures["wallLeft"]!!))
//        wall.transform.position = cell.transform.position
//        wall.collisionBox.y = wall.collisionBox.y - wall.origin.y
//        wall.dimension = Vector2(0.2f, 2f)
//        wall.collisionBox.width = wall.dimension.x
//        wall.origin = Vector2(0.5f, 0.5f)
//        walls[Direction.LEFT] = wall
//      }
//      Direction.RIGHT -> {
//        wall = Terrain("wallRight", Vector3(position.x, position.y, 0f), true)
//        wall.dimension = Vector2(-0.2f, 2f)
//        wall.collisionBox.x = wall.position.x + wall.origin.x
//        wall.collisionBox.y = wall.position.y - wall.origin.y
//        wall.collisionBox.width = wall.dimension.x
//        wall.origin = Vector2(-0.5f, 0.5f)
//        walls[Direction.RIGHT] = wall
//      }
//      Direction.DOWN -> {
//        wall = Terrain("wallDown", Vector3(position.x, position.y, 0f), true)
//        wall.collisionBox.y = wall.collisionBox.y - wall.origin.y
//        wall.collisionBox.height = 0.1f
//        walls[Direction.DOWN] = wall
//      }
//      else -> {
//        val chance = random.nextFloat()
//        var wallType = 4
//        if (chance <= chanceOfDifferentWall) {
//          wallType = Util.randomInt(random, 0, 10)
//        }
//        if (wallType < 4) {
//          name = "floor$wallType"
//          setUp()
//        }
//        wall = Terrain("wallUp$wallType", Vector3(position.x, position.y + 1f, 0f), true)
//        if (wallType < 2) {
//          wall.dimension = Vector2(1f, 1.117f)
//        } else if (wallType == 2) {
//          wall.dimension = Vector2(1f, 1.495f)
//        }
//        wall.collisionBox.y = wall.collisionBox.y - wall.origin.y
//        walls[Direction.UP] = wall
//      }
//    }
//  }
//
//  companion object {
//    private val chanceOfDifferentWall = 0.05f
//    private val chanceOfDifferentFloor = 0.25f
//  }
//}
