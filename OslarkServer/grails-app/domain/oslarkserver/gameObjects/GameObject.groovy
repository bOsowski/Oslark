package oslarkserver.gameObjects

import oslarkserver.World
import oslarkserver.gameObjects.components.Rectangle
import oslarkserver.gameObjects.components.Vector2
import oslarkserver.gameObjects.components.Vector3

class GameObject {

    String name = "undefinted"
    Vector3 position = new Vector3()
    float rotation = 0
    float stateTime
    Vector2 scale = new Vector2(1,1)
    Vector2 dimension = new Vector2(1,1)
    Vector2 origin = new Vector2(0.5, 0.5)
    Rectangle collisionBox
    boolean collides = false

    static belongsTo = [world: World]

    static constraints = {
        collisionBox nullable: true
    }

    static embedded = ['position', 'scale', 'dimension', 'origin', 'collisionBox']

    static mapping = {
    }

    String toJson(){
        return "{id:${id}, name:${name}, position:${position}, scale:${scale}, " +
                "rotation:${rotation}, stateTime:${stateTime}, dimension:${dimension}, origin:${origin}, " +
                "collisionBox:${collisionBox}, collides:${collides}}"
    }

}
