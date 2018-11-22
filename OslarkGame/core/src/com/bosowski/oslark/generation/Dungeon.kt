package com.bosowski.oslark.generation

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2

import java.util.ArrayList
import java.util.HashMap
import java.util.Random

class Dungeon(private val bounds: Rectangle, private val minRoomSize: Int, private val maxRoomSize: Int, private val roomCreationAttempts: Int, seed: Long) {

  private val dungeonCells = HashMap<Vector2, DungeonCell>()
  private val dungeonRooms = ArrayList<DungeonRoom>()
  private var maze: Maze? = null
  private var created = false
  private val random: Random = Random(seed)

  fun create() {
    if (created) {
      return
    }
    createRooms()
    createMazes()
    shrinkMazes()
    removeIsolatedRooms()
    createWallsAndInstantiate()
    created = true
  }

  fun clear() {
    for (cell in dungeonCells.values) {
      cell.clear()
    }
  }

  private fun createRooms() {
    Gdx.app.log(TAG, "Creating rooms..")
    for (i in 0 until roomCreationAttempts) {
      val room = DungeonRoom(minRoomSize, maxRoomSize, bounds, dungeonRooms, random)
      if (room.create()) {
        dungeonRooms.add(room)
        dungeonCells.putAll(room.cells)
      }
    }
    Gdx.app.log(TAG, "Finished creating rooms.")
  }

  private fun createMazes() {
    Gdx.app.log(TAG, "Creating mazes..")
    maze = Maze(bounds, dungeonRooms, random)
    maze!!.create()
    dungeonCells.putAll(maze!!.cells)
    Gdx.app.log(TAG, "Finished creating mazes.")
  }

  private fun shrinkMazes() {
    Gdx.app.log(TAG, "Shrinking mazes..")
    var entryIterator: MutableIterator<Any>
    var deletedAny = true
    while (deletedAny) {
      entryIterator = dungeonCells.entries.iterator()
      deletedAny = false
      while (entryIterator.hasNext()) {
        val entry = entryIterator.next()
        if (entry.value.getNeighbours(dungeonCells).size <= 1) {
          entryIterator.remove()
          deletedAny = true
        }
      }
    }
    Gdx.app.log(TAG, "Finished shrinking mazes..")
  }

  private fun removeIsolatedRooms() {
    Gdx.app.log(TAG, "Removing isolated rooms..")
    val iter = dungeonRooms.iterator()
    while (iter.hasNext()) {
      val room = iter.next()
      if (room.isIsolated(dungeonCells)) {
        for (cell in room.cells.keys) {
          dungeonCells.remove(cell)
        }
        room.clear()
        iter.remove()
      }
    }
    Gdx.app.log(TAG, "Finished removing isolated rooms..")
  }

  private fun createWallsAndInstantiate() {
    Gdx.app.log(TAG, "Creating walls and instantiating..")
    for (cell in dungeonCells.values) {
      cell.setUpWalls(dungeonCells)
      cell.instantiate()
    }
    Gdx.app.log(TAG, "Finished creating walls and instantiating..")
  }

  companion object {
    val TAG = Dungeon::class.java.name
  }
}