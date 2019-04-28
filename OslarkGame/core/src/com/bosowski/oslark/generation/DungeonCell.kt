package com.bosowski.oslark.generation

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.EdgeShape
import com.bosowski.oslark.components.TextureComponent
import com.bosowski.oslark.enums.Direction
import com.bosowski.oslark.gameObjects.GameObject
import com.bosowski.oslark.Assets
import com.bosowski.oslark.components.AnimationComponent
import com.bosowski.oslark.components.ColliderComponent
import com.bosowski.oslark.utils.Util
import java.util.*

class DungeonCell(position: Vector2, private val random: Random, val index: Int): Generation{

  val cell = GameObject(position,-1, bodyType = BodyDef.BodyType.StaticBody)
  private val chanceOfDifferentFloor = 0.25f

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

  override fun clear() {
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

  private fun getWall(cell: GameObject, direction: Direction, random: Random): GameObject {

    val chanceOfDifferentWall = 0.05f

    var wall = GameObject(cell.transform.position, bodyType = BodyDef.BodyType.StaticBody)
    when (direction) {
      Direction.LEFT -> {
        val textureComponent = TextureComponent(Assets.textures["wallLeft"]!!)
        textureComponent.origin = Vector2(0.5f, 0.0f)
        textureComponent.dimension = Vector2(0.2f, 2f)
        wall.addComponent(textureComponent)
        //type = BodyDef.BodyType.StaticBody, centre = Vector2(- textureComponent.origin.x + 0.1f, 0f), width = 0.2f, height = 1f
        val shape = EdgeShape()
        shape.set(Vector2(-textureComponent.origin.x + textureComponent.dimension.x, -0.0f), Vector2(-textureComponent.origin.x + textureComponent.dimension.x, 1f))
        val colliderComponent = ColliderComponent(shape, 0f)
        wall.addComponent(colliderComponent)
        return wall
      }
      Direction.RIGHT -> {
        val textureComponent = TextureComponent(Assets.textures["wallRight"]!!)
        textureComponent.origin = Vector2(-0.5f, 0.0f)
        textureComponent.dimension = Vector2(0.2f, 2f)
        wall.addComponent(textureComponent)
        //type = BodyDef.BodyType.StaticBody, centre = Vector2( - textureComponent.origin.x, 0f), width = 0.2f, height = 1f
        val shape = EdgeShape()
        shape.set(Vector2(-textureComponent.origin.x, -0.0f), Vector2(-textureComponent.origin.x, 1f))
        val colliderComponent = ColliderComponent(shape, 0f)
        wall.addComponent(colliderComponent)
        return wall
      }
      Direction.DOWN -> {
        val textureComponent = TextureComponent(Assets.textures["wallDown"]!!)
        textureComponent.origin = Vector2(0.5f, 0.0f)
        wall.addComponent(textureComponent)
        //type = BodyDef.BodyType.StaticBody, centre = Vector2(0f,  - textureComponent.origin.y), width = 1f, height = 0.01f
        val shape = EdgeShape()
        shape.set(Vector2(-textureComponent.origin.x, -textureComponent.origin.y), Vector2(textureComponent.origin.x, -textureComponent.origin.y))
        val colliderComponent = ColliderComponent(shape, 0f)
        wall.addComponent(colliderComponent)
        return wall
      }
      else -> {
        wall = GameObject(Vector2(cell.transform.position.x, cell.transform.position.y + 1), bodyType = BodyDef.BodyType.StaticBody)
        val chance = random.nextFloat()
        var wallType = 4
        if (chance <= chanceOfDifferentWall) {
          wallType = Util.randomInt(random, 0, 10)
        }
        val wallImage: TextureComponent
        if (wallType < 2) {
          cell.removeComponent("TextureComponent")
          cell.addComponent(AnimationComponent(Assets.animations["floor$wallType"]!!))
          wallImage = AnimationComponent(Assets.animations["wallUp$wallType"]!!)
          wall.addComponent(wallImage)
        } else {
          wallImage = TextureComponent(Assets.textures["wallUp$wallType"]!!)
          wall.addComponent(wallImage)
        }

        if (wallType in 2..3) {
          cell.addComponent(TextureComponent(Assets.textures["floor$wallType"]!!))
        }
        if (wallType < 2) {
          wallImage.dimension = Vector2(1f, 1.117f)
        } else if (wallType == 2) {
          wallImage.dimension = Vector2(1f, 1.495f)
        }
        //type = BodyDef.BodyType.StaticBody, centre = Vector2(0f, -wallImage.origin.y), width = 1f, height = 0.01f
        val shape = EdgeShape()
        shape.set(Vector2(-wallImage.origin.x, -wallImage.origin.y), Vector2(wallImage.origin.x, -wallImage.origin.y))
        val colliderComponent = ColliderComponent(shape, 0f)
        wall.addComponent(colliderComponent)
        return wall
      }
    }
  }
}
