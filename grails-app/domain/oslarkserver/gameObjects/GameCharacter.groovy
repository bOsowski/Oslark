package oslarkserver.gameObjects

import oslarkserver.User
import oslarkserver.World
import oslarkserver.gameObjects.enums.CharacterClass
import oslarkserver.gameObjects.enums.Gender

class GameCharacter extends Creature{

    CharacterClass characterClass
    Gender gender = Gender.MALE

    static transient belongsTo = [user : User]

    static constraints = {
        name unique: true
        world nullable: false
    }

    static embedded = ['position']

    String getDisplayString() { return name }

//    String toJson(){
//        return "{id:${id}, name:${name}, position:${position}, scale:${scale}, characterClass:${characterClass}, state:${state.name}, speed:${speed}, level:${level}, " +
//                "direction:${direction}, rotation:${rotation}, stateTime:${stateTime}, dimension:${dimension}, origin:${origin}, collisionBox:${collisionBox}, collides:${collides}, " +
//                "totalHitpoints:${totalHitpoints}, hitpoints:${hitpoints}, damage:${damage}, gender:${gender}}"
//    }

    String toJson(){
        return "{super:${super.toJson()}, characterClass:${characterClass}, gender:${gender}}"
    }

}
