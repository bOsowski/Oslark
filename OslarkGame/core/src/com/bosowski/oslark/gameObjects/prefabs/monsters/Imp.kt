package com.bosowski.oslark.gameObjects.prefabs.monsters

import com.badlogic.gdx.math.Vector2
import com.bosowski.oslark.World
import com.bosowski.oslark.components.ActionInterface

class Imp(position: Vector2): Monster(position, "imp", 4f, 35f,  Vector2(1f,1f)) {

    init {
        actionComponent.action = ActionInterface { deltaTime ->
          if (steeringComponent.raycast(World.player.transform.position) && Vector2.dst(World.player.transform.position.x, World.player.transform.position.y, transform.position.x, transform.position.y) < 5f) {
            steeringComponent.goTo(World.player.transform.position)
          } else {
            moveRandomly(deltaTime)
          }
        }
      creatureComponent.damage = Pair(1f,2f)
      creatureComponent.maxHealth = 4f
    }

}