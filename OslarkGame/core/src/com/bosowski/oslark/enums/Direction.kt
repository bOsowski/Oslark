package com.bosowski.oslark.enums

import com.badlogic.gdx.math.Vector2

import java.util.ArrayList
import java.util.Random

enum class Direction(val word: String, val value: Vector2) {
  UP("up", Vector2(0.0f, 1.0f)), RIGHT("right", Vector2(1.0f, 0.0f)), DOWN("down", Vector2(0.0f, -1.0f)), LEFT("left", Vector2(-1.0f, 0.0f));


  companion object {

    fun getDirection(name: String): Direction? {
      when (name) {
        "up" -> return UP
        "right" -> return RIGHT
        "down" -> return DOWN
        "left" -> return LEFT
        else -> return null
      }
    }

    fun getRandom(rand: Random): Direction{
      return when (rand.nextInt(4)) {
        0 -> UP
        1 -> RIGHT
        2 -> LEFT
        else -> DOWN
      }
    }

    val directions: ArrayList<Direction>
      get() {
        val directions = ArrayList<Direction>()
        directions.add(UP)
        directions.add(DOWN)
        directions.add(LEFT)
        directions.add(RIGHT)
        return directions
      }
  }
}