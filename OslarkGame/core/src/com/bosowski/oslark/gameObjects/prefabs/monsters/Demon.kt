package com.bosowski.oslark.gameObjects.prefabs.monsters

import com.badlogic.gdx.math.Vector2
import com.bosowski.oslark.World
import com.bosowski.oslark.components.ActionInterface

class Demon(position: Vector2): Monster(position, "bigDemon", 5f, 350f, Vector2(3f, 3f)) {

    init {
        actionComponent.action = ActionInterface { deltaTime ->
            moveRandomly(deltaTime)
            steeringComponent.raycast(World.instance!!.player.transform.position)
            steeringComponent.goTo(World.instance!!.player.transform.position)
        }

        creatureComponent.maxHealth = 15f
        creatureComponent.damage = Pair(1f,3f)
    }
}