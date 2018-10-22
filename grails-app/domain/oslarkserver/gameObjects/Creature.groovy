package oslarkserver.gameObjects

import oslarkserver.gameObjects.enums.State

class Creature extends GameObject{

    State state = State.IDLE
    float speed = 0
    int level = 1
    float totalHitpoints = 1
    float hitpoints = 1
    float damage = 0

    static constraints = {
        level range: 1..99
    }

    static mapping = {
    }

    String toJson(){
        return "{super:${super.toJson()}, state:${state.name}, speed:${speed}, level:${level}, " +
                "totalHitpoints:${totalHitpoints}, hitpoints:${hitpoints}, damage:${damage}}"
    }
}
