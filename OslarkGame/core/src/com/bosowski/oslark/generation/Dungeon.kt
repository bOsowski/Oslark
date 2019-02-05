package com.bosowski.oslark.generation

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ai.pfa.Connection
import com.badlogic.gdx.ai.pfa.DefaultConnection
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Array
import com.bosowski.oslark.World
import com.bosowski.oslark.components.TextureComponent
import com.bosowski.oslark.gameObjects.prefabs.Demon
import com.bosowski.oslark.gameObjects.prefabs.Monster
import com.bosowski.oslark.gameObjects.prefabs.Skeleton
import com.bosowski.oslark.utils.Util
import java.util.ArrayList
import java.util.Random

class Dungeon(private val bounds: Rectangle, private val minRoomSize: Int, private val maxRoomSize: Int, private val roomCreationAttempts: Int) {

  val dungeonCells = HashMap<Vector2, DungeonCell>()
  private val dungeonRooms = ArrayList<DungeonRoom>()
  private var maze: Maze? = null
  private var created = false
  private val random: Random = World.random

  private val spawnedMonsters = ArrayList<Monster>()

//  private val monsterTypes = [Demon::class, Skeleton::class]

  var nodeIndex: Int = 0
  get() {
    field++
    return field
  }

  fun spawnMonsters(){
    val chanceToSpawnInMaze = 0.05
    val chanceToSpawnInRoom = 0.3

    //spawn monsters in maze. Make sure the monster is 1x1.
    maze!!.cells.values.forEach { it ->
      val rand = random.nextFloat()
      if(rand <= chanceToSpawnInMaze){
        val monster = Skeleton(it.cell.transform.position.sub(-0f, -0.25f))
        monster.instantiate()
        spawnedMonsters.add(monster)
      }
    }
  }

  fun create() : Boolean{
    if (created) {
      return true
    }
    createRooms()
    createMazes()
    print("Maze size = " + maze!!.cells.size)
    shrinkMazes()
    print("Maze size = " + maze!!.cells.size)
    removeIsolatedRooms()
    if(findIsolatedAreas() > 1){
      return false
    }
    createWallsAndInstantiate()
    spawnMonsters()
    colourMazeCells()
    created = true
    return true
  }

  fun clear() {
    for (cell in dungeonCells.values) {
      cell.clear()
    }
    spawnedMonsters.forEach{
      it.destroy()
    }
  }

  private fun colourMazeCells(){
    maze!!.cells.forEach{
      (it.value.cell.getComponent(TextureComponent::class.java.simpleName) as TextureComponent?)?.color = Color.PURPLE
    }
  }

  private fun createRooms() {
    Gdx.app.log(TAG, "Creating rooms..")
    for (i in 0 until roomCreationAttempts) {
      val room = DungeonRoom(minRoomSize, maxRoomSize, bounds, dungeonRooms, random, this)
      if (room.create()) {
        dungeonRooms.add(room)
        dungeonCells.putAll(room.cells)
      }
    }
    Gdx.app.log(TAG, "Finished creating rooms.")
  }

  private fun createMazes() {
    Gdx.app.log(TAG, "Creating mazes..")
    maze = Maze(bounds, dungeonRooms, random, this)
    maze!!.create()
    dungeonCells.putAll(maze!!.cells)
    Gdx.app.log(TAG, "Finished creating mazes.")
  }

  private fun shrinkMazes() {
    Gdx.app.log(TAG, "Shrinking mazes..")
    var entryIterator: MutableIterator<Any>
    var deletedAny = true
    while (deletedAny) {
      entryIterator = maze!!.cells.entries.iterator()
      deletedAny = false
      while (entryIterator.hasNext()) {
        val entry = entryIterator.next()
        if (entry.value.getNeighbours(dungeonCells).size <= 1) {
          dungeonCells.remove(entry.key)
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

  private fun findIsolatedAreas(): Int{
    Gdx.app.log(TAG, "Joining isolated areas..")
    var allCells: HashMap<Vector2, DungeonCell> = HashMap()
    allCells.putAll(dungeonCells)
    val clusters: ArrayList<ArrayList<DungeonCell>> = ArrayList()

    val visitedCells: ArrayList<DungeonCell> = ArrayList()

    fun getCluster(neighbors: ArrayList<DungeonCell>): ArrayList<DungeonCell>{
        for(i in 0 until neighbors.size) {
          if(!visitedCells.contains(neighbors[i])){
            visitedCells.add(neighbors[i])
            allCells.remove(neighbors[i].cell.transform.position)
            neighbors.addAll(getCluster(neighbors[i].getNeighbours(allCells)))
          }
        }
      return neighbors
    }

    while(!allCells.isEmpty()){
      val firstCell = allCells.values.first()
      val neighbours = firstCell.getNeighbours(allCells)
      neighbours.add(firstCell)
      clusters.add(getCluster(neighbours))
      visitedCells.clear()
      Gdx.app.debug(TAG, "Creating cluster.")
    }

//    val colors = listOf(Color.RED, Color.BLUE, Color.GOLD, Color.GRAY, Color.PURPLE)
//    var index = 0
//    clusters.forEach {
//      val color = colors[index]
//      if(index < colors.size){
//        index++
//      }
//      else{
//        index = 0
//      }
//      it.forEach {
//        it.cell.getComponents().forEach {
//          if(it is TextureComponent){
//            it.color = color
//          }
//        }
//      }
//    }

    Gdx.app.log(TAG, "Finished joining isolated areas.. amount of clusters = ${clusters.size}")
    return clusters.size
  }

  companion object {
    val TAG = Dungeon::class.java.name
  }
}