package com.bosowski.oslark.generation

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.bosowski.oslark.components.AnimationComponent
import com.bosowski.oslark.components.ColliderComponent
import com.bosowski.oslark.components.TextureComponent
import com.bosowski.oslark.enums.Direction
import com.bosowski.oslark.gameObjects.GameObject
import com.bosowski.oslark.main.Assets
import com.bosowski.oslark.utils.Util
import java.util.*

class DungeonCell(position: Vector2, private val random: Random){

  val cell = GameObject(position,-1)

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
    var wall = GameObject(cell.transform.position)
    when (direction) {
      Direction.LEFT -> {
        val textureComponent = TextureComponent(Assets.textures["wallLeft"]!!)
        textureComponent.origin = Vector2(0.5f, 0.5f)
        textureComponent.dimension = Vector2(0.2f, 2f)
        wall.addComponent(textureComponent)
        val colliderComponent = ColliderComponent(type = BodyDef.BodyType.StaticBody, centre = Vector2(- textureComponent.origin.x + 0.1f, 0f), width = 0.2f, height = 1f)
        wall.addComponent(colliderComponent)
        walls[Direction.LEFT] = wall
      }
      Direction.RIGHT -> {
        val textureComponent = TextureComponent(Assets.textures["wallRight"]!!)
        textureComponent.origin = Vector2(-0.5f, 0.5f)
        textureComponent.dimension = Vector2(0.2f, 2f)
        wall.addComponent(textureComponent)
        val colliderComponent = ColliderComponent(type = BodyDef.BodyType.StaticBody, centre = Vector2( - textureComponent.origin.x, 0f), width = 0.2f, height = 1f)
        wall.addComponent(colliderComponent)
        walls[Direction.RIGHT] = wall
      }
      Direction.DOWN -> {
        val textureComponent = TextureComponent(Assets.textures["wallDown"]!!)
        textureComponent.origin = Vector2(0.5f, 0.5f)
        wall.addComponent(textureComponent)
        val colliderComponent = ColliderComponent(type = BodyDef.BodyType.StaticBody, centre = Vector2(0f,  - textureComponent.origin.y), width = 1f, height = 0.01f)
        wall.addComponent(colliderComponent)
        walls[Direction.DOWN] = wall
      }
      else -> {
        wall = GameObject(Vector2(cell.transform.position.x, cell.transform.position.y+1))
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
        }
        else{
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
        val colliderComponent = ColliderComponent(type = BodyDef.BodyType.StaticBody, centre = Vector2(0f, -wallImage.origin.y), width = 1f, height = 0.01f)
        wall.addComponent(colliderComponent)
        walls[Direction.UP] = wall
      }
    }
  }

  companion object {
    private const val chanceOfDifferentWall = 0.05f
    private const val chanceOfDifferentFloor = 0.25f
  }
}
