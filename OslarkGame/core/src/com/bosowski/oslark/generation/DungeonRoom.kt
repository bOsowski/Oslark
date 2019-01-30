package com.bosowski.oslark.generation

import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.bosowski.oslark.enums.Direction
import com.bosowski.oslark.utils.Util

import java.util.ArrayList
import java.util.HashMap
import java.util.Random

class DungeonRoom(private val minSize: Int, private val maxSize: Int, private val parentArea: Rectangle, private val otherRooms: ArrayList<DungeonRoom>, private val random: Random, val dungeon: Dungeon) {

  val cells = HashMap<Vector2, DungeonCell>()
  var bounds: Rectangle? = null
    private set

  fun create(): Boolean {
    bounds = Rectangle()
    bounds!!.height = rand(minSize.toFloat(), maxSize.toFloat()).toFloat()
    bounds!!.width = rand(minSize.toFloat(), maxSize.toFloat()).toFloat()
    bounds!!.x = rand(parentArea.x.toInt().toFloat(), (parentArea.x + parentArea.width - bounds!!.width).toInt().toFloat()).toFloat()
    bounds!!.y = rand(parentArea.y.toInt().toFloat(), (parentArea.y + parentArea.height - bounds!!.height).toInt().toFloat()).toFloat()

    //check if the generated room is colliding with other rooms
    for (otherRoom in otherRooms) {
      if (bounds!!.overlaps(otherRoom.bounds!!)) {
        return false
      }
    }

    //create the tiles
    var x = 0
    while (x < bounds!!.width) {
      var y = 0
      while (y < bounds!!.height) {
        add(bounds!!.x + x, bounds!!.y + y)
        y++
      }
      x++
    }
    return true
  }

  private fun add(x: Float, y: Float) {
    val cell = DungeonCell(Vector2(x.toInt().toFloat(), y.toInt().toFloat()), random, dungeon.nodeIndex)
    cells[cell.cell.transform.position] = cell
  }

  private fun rand(min: Float, max: Float): Int {
    return Util.randomInt(random, min.toInt(), max.toInt() + 1)
  }

  fun isIsolated(otherCells: HashMap<Vector2, DungeonCell>): Boolean {
    for (direction in Direction.directions) {
      var x = bounds!!.x.toInt()
      while (x < bounds!!.x + bounds!!.width) {
        if ((otherCells.containsKey(Vector2(x.toFloat(), bounds!!.y).add(direction.value)) || otherCells.containsKey(Vector2(x.toFloat(), bounds!!.y + bounds!!.height).add(direction.value)))
            && !cells.containsKey(Vector2(x.toFloat(), bounds!!.y).add(direction.value))
            && !cells.containsKey(Vector2(x.toFloat(), bounds!!.y + bounds!!.height).add(direction.value))) {
          return false
        }
        x++
      }
      var y = bounds!!.y.toInt()
      while (y < bounds!!.y + bounds!!.height) {
        if ((otherCells.containsKey(Vector2(bounds!!.x, y.toFloat()).add(direction.value)) || otherCells.containsKey(Vector2(bounds!!.x + bounds!!.width, y.toFloat()).add(direction.value)))
            && !cells.containsKey(Vector2(bounds!!.x, y.toFloat()).add(direction.value))
            && !cells.containsKey(Vector2(bounds!!.x + bounds!!.width, y.toFloat()).add(direction.value))) {
          return false
        }
        y++
      }
    }
    return true
  }

  fun clear() {
    for (cell in cells.values) {
      cell.clear()
    }
    cells.clear()
  }

}
