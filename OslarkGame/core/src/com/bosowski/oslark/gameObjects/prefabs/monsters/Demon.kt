package com.bosowski.oslark.gameObjects.prefabs.monsters

import com.badlogic.gdx.math.Vector2
import com.bosowski.oslark.World
import com.bosowski.oslark.components.ActionInterface

class Demon(position: Vector2): Monster(position, "bigDemon", 5f, 350f, Vector2(3f, 3f)) {

    init {
        actionComponent.action = ActionInterface { deltaTime ->
            moveRandomly(deltaTime)
            steeringComponent.raycast(World.player?.transform!!.position)
            //steeringComponent.goTo(World.player.transform.position)
        }

        creatureComponent.maxHealth = 20f
        creatureComponent.damage = Pair(3f,5f)
    }
}