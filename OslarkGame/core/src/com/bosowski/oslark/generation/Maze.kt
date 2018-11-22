package com.bosowski.oslark.generation

import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.bosowski.oslark.enums.Direction
import com.bosowski.oslark.utils.Util

import java.util.ArrayList
import java.util.HashMap
import java.util.Random
import java.util.Stack

class Maze(private val parentArea: Rectangle, private val rooms: ArrayList<DungeonRoom>, private val random: Random) {

  val cells = HashMap<Vector2, DungeonCell>()

  private fun isMoveValid(currentPosition: Vector2, chosenDirection: Direction): Boolean {
    val backStep = Vector2(currentPosition).sub(chosenDirection.value)
    if (cells.containsKey(currentPosition) || cells.containsKey(backStep) || currentPosition.x < parentArea.x || currentPosition.x > parentArea.x + parentArea.width || currentPosition.y < parentArea.y || currentPosition.y > parentArea.y + parentArea.height) {
      return false
    }
    for (room in rooms) {
      if (room.cells.containsKey(currentPosition) || room.cells.containsKey(backStep)) {
        return false
      }
    }
    return true
  }

  private fun isFree(position: Vector2): Boolean {
    if (cells.containsKey(position)) {
      return false
    }

    for (room in rooms) {
      if (room.cells.containsKey(position)) {
        return false
      }
    }
    return true
  }

  fun create() {
    var x = parentArea.x.toInt()
    while (x < parentArea.x + parentArea.width) {
      var y = parentArea.y.toInt()
      while (y < parentArea.y + parentArea.height) {
        var cell: DungeonCell
        val stack = Stack<Vector2>()
        var currentPosition = Vector2(x.toFloat(), y.toFloat())

        if (isFree(currentPosition)) {
          stack.add(currentPosition)
          cell = DungeonCell(Vector3(currentPosition.x, currentPosition.y, -1f), false, random)
          cells[currentPosition] = cell

          while (!stack.isEmpty()) {
            val directions = ArrayList(Direction.getDirections())
            while (!directions.isEmpty()) {
              val chosenDir = directions[Util.randomInt(random, 0, directions.size - 1)]
              currentPosition = Vector2(Vector2(currentPosition).add(chosenDir.value)).add(chosenDir.value)
              if (!isMoveValid(currentPosition, chosenDir)) {
                directions.remove(chosenDir)
                currentPosition = Vector2(stack.peek())
              } else {
                cell = DungeonCell(Vector3(currentPosition.x, currentPosition.y, -1f), false, random)
                cells[currentPosition] = cell
                stack.add(currentPosition)
                val secondTile = Vector2(currentPosition).sub(chosenDir.value)
                cell = DungeonCell(Vector3(secondTile.x, secondTile.y, -1f), false, random)
                cells[secondTile] = cell
                break
              }
            }
            if (directions.isEmpty()) {
              currentPosition = Vector2(stack.pop())
            }
          }
          println("Maze created.")
        }
        y += 2
      }
      x += 2
    }

    println("FINISHED")
  }

  fun clear() {
    for (cell in cells.values) {
      cell.clear()
    }
    cells.clear()
    rooms.clear()
  }
}
