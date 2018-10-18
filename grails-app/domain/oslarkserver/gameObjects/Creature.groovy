package oslarkserver.gameObjects

import oslarkserver.gameObjects.enums.State

class Creature extends GameObject{

    State state = State.INVALID
    float speed = 0
    int level = 1

    static constraints = {
        level range: 1..99
    }

    static mapping = {
    }
}
