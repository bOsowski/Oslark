package oslarkserver.gameObjects

import oslarkserver.User
import oslarkserver.gameObjects.components.Vector3

class GameCharacter extends Creature{

    String name

    static belongsTo = [user : User]

    static constraints = {
        name unique: true
    }

    static embedded = ['position']
}
