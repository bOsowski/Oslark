package com.bosowski.oslark.gameObjects.prefabs

import com.badlogic.gdx.math.Vector2
import com.bosowski.oslark.World
import com.bosowski.oslark.components.UpdateActionInterface

class Ogre(position: Vector2): Monster(position, "ogre", 5f, Vector2(1f,1f)) {

    init {
        aiComponent.action = UpdateActionInterface { deltaTime ->
            if(steeringComponent.raycast(World.player.transform.position) && Vector2.dst(World.player.transform.position.x, World.player.transform.position.y, transform.position.x, transform.position.y) < 5f){
               steeringComponent.goTo(World.player.transform.position)
            }
            else{
                moveRandomly(deltaTime)
            }
        }
    }

}