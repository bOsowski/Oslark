package com.bosowski.oslark.gameObjects

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.bosowski.oslark.components.Animator
import com.bosowski.oslark.enums.Direction
import com.bosowski.oslark.enums.State
import com.bosowski.oslark.main.Assets
import com.bosowski.oslark.main.GameRenderer

import org.json.JSONObject

/**
 * Created by bOsowski on 11/02/2018.
 */

class Player : Creature {

  lateinit var gender: Gender
  lateinit var characterClass: CharacterClass

  constructor(jsonObject: JSONObject) : super(jsonObject.getJSONObject("super")) {
    this.gender = Gender.valueOf(jsonObject.getString("gender"))
    this.characterClass = CharacterClass.valueOf(jsonObject.getString("characterClass"))
    Gdx.app.error(GameObject.TAG, "Trying to load animator " + "'" + name + gender.name + "'" + "for '" + name + "' (" + id + ")")
    this.animator = Animator(Assets.instance.stateAnimations[characterClass.name + gender.name.toLowerCase()]!!)
    this.state = state
  }

  constructor(name: String, animator: Animator, scale: Vector2, collides: Boolean, collisionBox: Rectangle, totalHitPoints: Float, hitPoints: Float, damage: Float, state: State, direction: Direction, speed: Float, level: Int, position: Vector3, gender: Gender, characterClass: CharacterClass) : super(name, animator, scale, collides, collisionBox, totalHitPoints, hitPoints, damage, state, direction, speed, level, position) {
    this.gender = gender
    this.characterClass = characterClass
  }

  constructor(original: Creature) : super(original) {}

  override fun update(deltaTime: Float) {
    GameRenderer.camera.position.set(position)
    GameRenderer.camera.update()
  }

  enum class Gender(s: String) {
    MALE("male"), FEMALE("male")
  }

  enum class CharacterClass(s: String) {
    KNIGHT("knight"), WIZARD("knight"), ELF("knight")
  }
}
