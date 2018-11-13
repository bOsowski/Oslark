package oslarkserver.gameObjects

import oslarkserver.User
import oslarkserver.gameObjects.enums.CharacterClass
import oslarkserver.gameObjects.enums.Gender

class GameCharacter extends Creature {

  CharacterClass characterClass
  Gender gender = Gender.MALE

  static transient belongsTo = [user: User]

  static constraints = {
    name unique: true
    world nullable: false
  }

  static embedded = ['position']

  String toJson() {
    return "{super:${super.toJson()}, characterClass:${characterClass}, gender:${gender}}"
  }

}
