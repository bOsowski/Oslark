package com.bosowski.oslark.enums

enum class State(val word: String) {
  IDLE("idle"), MOVE("move"), ATTACK("attack"), DIE("die"), HURT("hurt"), INVALID("invalid");


  companion object {

    fun getState(name: String): State {
      return when (name) {
        "idle" -> IDLE
        "move" -> MOVE
        "attack" -> ATTACK
        "die" -> DIE
        "hurt" -> HURT
        else -> INVALID
      }
    }
  }
}
