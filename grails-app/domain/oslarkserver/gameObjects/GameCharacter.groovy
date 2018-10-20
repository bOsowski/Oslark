package oslarkserver.gameObjects

import oslarkserver.User
import oslarkserver.gameObjects.enums.CharacterClass

class GameCharacter extends Creature{

    CharacterClass characterClass = CharacterClass.DEFAULT

    static belongsTo = [user : User]

    static constraints = {
        name unique: true

        position display: false
        dimension display: false
        collisionBox display: false
        collides display: false
        collides display: false
    }

    static embedded = ['position']
}
