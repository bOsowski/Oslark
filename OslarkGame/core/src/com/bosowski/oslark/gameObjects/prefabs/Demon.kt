package com.bosowski.oslark.gameObjects.prefabs

import com.badlogic.gdx.math.Vector2
import com.bosowski.oslark.World
import com.bosowski.oslark.components.UpdateActionInterface

class Demon(position: Vector2): Monster(position, "bigDemon", 5f, Vector2(3f, 3f)) {

    init {
        aiComponent.action = UpdateActionInterface {deltaTime ->
            moveRandomly(deltaTime)
            steeringComponent.raycast(World.player.transform.position)
        }
        transform.body.massData.mass = 5f
    }

}