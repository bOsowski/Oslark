package oslarkserver.gameObjects

import oslarkserver.User
import oslarkserver.gameObjects.components.Vector3

class GameCharacter extends Creature{

    String name
    Vector3 position = new Vector3(0,0,0)

    static belongsTo = [user : User]

    static constraints = {
    }

    static embedded = ['position']
}
