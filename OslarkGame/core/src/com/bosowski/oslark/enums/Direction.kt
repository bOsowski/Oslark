package com.bosowski.oslark.enums

import com.badlogic.gdx.math.Vector2

import java.util.ArrayList
import java.util.Random

enum class Direction(val word: String, val value: Vector2) {
  UP("up", Vector2(0.0f, 1.0f)), RIGHT("right", Vector2(1.0f, 0.0f)), DOWN("down", Vector2(0.0f, -1.0f)), LEFT("left", Vector2(-1.0f, 0.0f));


  companion object {

    fun getDirection(name: String): Direction? {
      return when (name) {
        "up" -> UP
        "right" -> RIGHT
        "down" -> DOWN
        "left" -> LEFT
        else -> null
      }
    }

    val random: Direction?
      get() {
        val rand = Random()
        return when (rand.nextInt(4)) {
          0 -> UP
          1 -> RIGHT
          2 -> LEFT
          3 -> DOWN
          else -> null
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