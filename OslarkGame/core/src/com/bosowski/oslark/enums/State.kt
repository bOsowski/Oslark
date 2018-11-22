package com.bosowski.oslark.enums

enum class State(val word: String) {
  IDLE("idle"), MOVE("move"), ATTACK("attack"), DIE("die"), HIT("hit"), INVALID("invalid");


  companion object {

    fun getState(name: String): State {
      when (name) {
        "idle" -> return IDLE
        "move" -> return MOVE
        "attack" -> return ATTACK
        "die" -> return DIE
        "hit" -> return HIT
        else -> return INVALID
      }
    }
  }
}
