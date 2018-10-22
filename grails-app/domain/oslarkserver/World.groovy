package oslarkserver

import oslarkserver.gameObjects.GameObject

class World {

    static hasMany = [gameObjects: GameObject]

    static constraints = {
    }
}