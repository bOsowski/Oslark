package oslarkserver.gameObjects

import oslarkserver.World
import oslarkserver.gameObjects.components.Rectangle
import oslarkserver.gameObjects.components.Vector2
import oslarkserver.gameObjects.components.Vector3
import oslarkserver.gameObjects.enums.Direction

class GameObject {

    String name = "undefinted"
    Vector3 position = new Vector3()
    Direction direction = Direction.DOWN
    float rotation = 0
    float stateTime = 0
    Vector2 scale = new Vector2(1,1)
    Vector2 dimension = new Vector2(1,1)
    Vector2 origin = new Vector2(0.5, 0.5)
    Rectangle collisionBox = new Rectangle(x: 0, y: 1, width: 10, height: 10)
    boolean collides = false

    static belongsTo = [world: World]

    static constraints = {
    }

    static embedded = ['position', 'scale', 'dimension', 'origin', 'collisionBox']

    static mapping = {
    }


    String toJson(){
        return "{id:${id}, name:${name}, position:${position}, scale:${scale}, direction:${direction}, " +
                "rotation:${rotation}, stateTime:${stateTime}, dimension:${dimension}, origin:${origin}, " +
                "collisionBox:${collisionBox}, collides:${collides}}"
    }

}
