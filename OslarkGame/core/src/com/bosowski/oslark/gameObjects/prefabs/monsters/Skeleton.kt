package com.bosowski.oslark.gameObjects.prefabs.monsters

import com.badlogic.gdx.math.Vector2
import com.bosowski.oslark.World
import com.bosowski.oslark.components.ActionInterface

class Skeleton(position: Vector2): Monster(position, "skelet", 0.75f, 45f, Vector2(1f,1f)) {

    init {
        actionComponent.action = ActionInterface { deltaTime ->
          if (steeringComponent.raycast(World.player.transform.position) && Vector2.dst(World.player.transform.position.x, World.player.transform.position.y, transform.position.x, transform.position.y) < 2f) {
            steeringComponent.goTo(World.player.transform.position)
          } else {
            moveRandomly(deltaTime)
          }
        }
      creatureComponent.maxHealth = 2f
    }
}