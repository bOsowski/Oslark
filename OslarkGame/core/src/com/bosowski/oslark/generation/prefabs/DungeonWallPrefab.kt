package com.bosowski.oslark.generation.prefabs

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

class DungeonWallPrefab(cell: GameObject, direction: Direction, random: Random): GameObject(cell.transform.position) {

  init {
    when (direction) {
      Direction.LEFT -> {
        val textureComponent = TextureComponent(Assets.textures["wallLeft"]!!)
        textureComponent.origin = Vector2(0.5f, 0.5f)
        textureComponent.dimension = Vector2(0.2f, 2f)
        addComponent(textureComponent)
        val colliderComponent = ColliderComponent(type = BodyDef.BodyType.StaticBody, centre = Vector2(-textureComponent.origin.x + 0.1f, 0f), width = 0.2f, height = 1f)
        addComponent(colliderComponent)
      }
      Direction.RIGHT -> {
        val textureComponent = TextureComponent(Assets.textures["wallRight"]!!)
        textureComponent.origin = Vector2(-0.5f, 0.5f)
        textureComponent.dimension = Vector2(0.2f, 2f)
        addComponent(textureComponent)
        val colliderComponent = ColliderComponent(type = BodyDef.BodyType.StaticBody, centre = Vector2(-textureComponent.origin.x, 0f), width = 0.2f, height = 1f)
        addComponent(colliderComponent)
      }
      Direction.DOWN -> {
        val textureComponent = TextureComponent(Assets.textures["wallDown"]!!)
        textureComponent.origin = Vector2(0.5f, 0.5f)
        addComponent(textureComponent)
        val colliderComponent = ColliderComponent(type = BodyDef.BodyType.StaticBody, centre = Vector2(0f, -textureComponent.origin.y), width = 1f, height = 0.01f)
        addComponent(colliderComponent)
      }
      else -> {
        transform.position.set(Vector2(cell.transform.position.x, cell.transform.position.y + 1))
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
          addComponent(wallImage)
        } else {
          wallImage = TextureComponent(Assets.textures["wallUp$wallType"]!!)
          addComponent(wallImage)
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
        addComponent(colliderComponent)
      }
    }
  }

  companion object {
    private const val chanceOfDifferentWall = 0.05f
  }
}