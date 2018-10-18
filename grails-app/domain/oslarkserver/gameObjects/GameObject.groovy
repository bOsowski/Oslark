package oslarkserver.gameObjects

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


    static constraints = {
    }

    static embedded = ['position', 'scale', 'dimension', 'origin', 'collisionBox']

    static mapping = {
    }
}
