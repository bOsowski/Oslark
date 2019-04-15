package oslarkserver.gameObjects

import oslarkserver.Seed
import oslarkserver.User
import oslarkserver.gameObjects.enums.CharacterClass
import oslarkserver.gameObjects.enums.Gender

class GameCharacter {

  CharacterClass characterClass
  Gender gender = Gender.MALE
  String name

  static transient belongsTo = [user: User]
  static hasMany = [seeds: Seed]

  static constraints = {
    name unique: true
    user display: false
  }

  @Override
  String toString() {
    return "${characterClass} (${name})"
  }

  String toJson(){
    return "{name: ${name}, characterClass: ${characterClass}, gender: ${gender}, seeds:${seeds.toSorted()*.value.toString()}}"
  }
}
