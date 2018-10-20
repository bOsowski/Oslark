package oslarkserver.gameObjects

import oslarkserver.User
import oslarkserver.gameObjects.enums.CharacterClass

class GameCharacter extends Creature{

    CharacterClass characterClass = CharacterClass.DEFAULT

    static transient belongsTo = [user : User]

    static constraints = {
        name unique: true
    }

    static embedded = ['position']

    String getDisplayString() { return name }

    String toJson(){
        return "{id:${id}, name:${name}, postion:${position}, scale:${scale}, characterClass:${characterClass.name}, state:${state}, speed:${speed}, level:${level}, " +
                "direction:${direction}, rotation:${rotation}, stateTime:${stateTime}, dimension:${dimension}, origin:${origin}, collisionBox:${collisionBox}, collides:${collides}}"
    }

}
