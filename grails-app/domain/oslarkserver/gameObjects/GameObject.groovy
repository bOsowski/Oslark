package oslarkserver.gameObjects

import com.badlogic.gdx.math.Rectangle
import oslarkserver.gameObjects.components.Vector2
import oslarkserver.gameObjects.components.Vector3
import oslarkserver.gameObjects.enums.Direction

class GameObject {

    String name
    Vector3 position = new Vector3()
    Direction direction = Direction.INVALID
    float rotation = 0
    float stateTime
    Vector2 scale
    Vector2 dimension
    Vector2 origin
    Rectangle collisionBox
    boolean collides = false

    static belongsTo = [world: World]


    static constraints = {
    }

    static embedded = ['position']
}
