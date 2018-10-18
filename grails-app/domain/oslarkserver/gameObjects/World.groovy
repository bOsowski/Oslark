package oslarkserver.gameObjects

import oslarkserver.gameObjects.components.Vector3

class World {

    String name
    Vector3 spawnPoint

    static constraints = {
        name unique: true
    }

    static embedded = ['spawnPoint']
}
