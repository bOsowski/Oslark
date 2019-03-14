package com.bosowski.oslark.generation

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.bosowski.oslark.components.TextureComponent
import com.bosowski.oslark.enums.Direction
import com.bosowski.oslark.gameObjects.GameObject
import com.bosowski.oslark.generation.utils.getWall
import com.bosowski.oslark.Assets
import com.bosowski.oslark.utils.Util
import java.util.*

class DungeonCell(position: Vector2, private val random: Random, val index: Int){

  val cell = GameObject(position,-1, bodyType = BodyDef.BodyType.StaticBody)

  private val walls = HashMap<Direction, GameObject>()

  init{
    val floorType: Int = if(random.nextFloat() <= chanceOfDifferentFloor){
      Util.randomInt(random,4,11)
    }
    else{
      4
    }
    val textureComponent = TextureComponent(Assets.textures["floor$floorType"]!!)
    cell.addComponent(textureComponent)
  }

  fun instantiate() {
    cell.instantiate()
    for (wall in walls.values) {
      wall.instantiate()
    }
  }

  fun setUpWalls(otherCells: HashMap<Vector2, DungeonCell>) {
    for (direction in Direction.directions) {
      if (!otherCells.containsKey(Vector2(cell.transform.position).add(direction.value))) {
        addWall(direction)
      }
    }
  }

  fun clear() {
    for (wall in walls.values) {
      wall.destroy()
    }
    walls.clear()
    cell.destroy()
  }

  fun getNeighbours(otherCells: HashMap<Vector2, DungeonCell>): ArrayList<DungeonCell> {
    val neighbours = ArrayList<DungeonCell>()
    for (direction in Direction.directions) {
      val neighbourPosition = Vector2(cell.transform.position).add(direction.value)
      if (otherCells.containsKey(neighbourPosition)) {
        neighbours.add(otherCells[neighbourPosition]!!)
      }
    }
    return neighbours
  }

  private fun addWall(direction: Direction) {
    walls[direction] = getWall(cell, direction, random)
  }

  companion object {
    private const val chanceOfDifferentFloor = 0.25f
  }
}
