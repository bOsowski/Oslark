package com.bosowski.oslark.gameObjects.prefabs

import com.badlogic.gdx.math.Vector2
import com.bosowski.oslark.World
import com.bosowski.oslark.components.UpdateActionInterface

class Skeleton(position: Vector2): Monster(position, "skelet", Vector2(1f,1f)) {

    init {
        aiComponent.action = UpdateActionInterface {deltaTime ->
           steeringComponent.goTo(World.player.transform.position)
        }
    }

}