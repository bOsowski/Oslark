package com.bosowski.oslark.generation

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.bosowski.oslark.World
import com.bosowski.oslark.components.HUDComponent
import com.bosowski.oslark.components.TextureComponent
import com.bosowski.oslark.gameObjects.prefabs.monsters.Monster
import com.bosowski.oslark.managers.NetworkManager
import com.bosowski.oslark.playerDomains.Settings
import com.bosowski.oslark.screens.GameScreen
import com.bosowski.oslark.utils.Util
import java.util.ArrayList
import java.util.Random

class Dungeon(private val bounds: Rectangle, private val minRoomSize: Int, private val maxRoomSize: Int, private val roomCreationAttempts: Int) {

  val dungeonCells = HashMap<Vector2, DungeonCell>()
  var levelCompleted = false
  private val dungeonRooms = ArrayList<DungeonRoom>()
  private var maze: Maze? = null
  private var created = false
  private val random: Random = World.instance!!.random
  var gameScreen: GameScreen? = null

  val spawnedMonsters = ArrayList<Monster>()
  var killedMonsters = 0
    set(value) {
      field = value
      //End game logic
      if(field == spawnedMonsters.size && !levelCompleted){
        levelCompleted = true
        val score = (World.instance!!.player.getComponent("HUDComponent") as HUDComponent).score
        NetworkManager.instance.addScore(score= score, seed = World.instance!!.seed, characterName = World.instance!!.playerName!!)
        gameScreen!!.scoreLabel.setText("You have completed the level with a score of $score!")
        World.instance!!.rayHandler.setAmbientLight(1f)
      }
    }

  var nodeIndex: Int = 0
    get() {
      field++
      return field
    }

  fun spawnMonsters(){

    var totalSpawnRate = 0
    var highestMonsterLevel = 0
    Settings.spawnTableMaze.forEach {
      totalSpawnRate += it.key
      if(it.value != null && highestMonsterLevel < it.value!!.second){
        highestMonsterLevel = it.value!!.second
      }
    }

    var lowestX = Float.MAX_VALUE
    var highestX = Float.MIN_VALUE
    dungeonCells.keys.forEach{
      if (lowestX > it.x) lowestX = it.x
      if (highestX < it.x) highestX = it.x
    }

    //spawn monsters in maze. Make sure the monster is 1x1.
    maze!!.cells.values.forEach { it ->
      val rand = Util.randomInt(random, 0, totalSpawnRate)
      val cellDifficulty = Util.map(it.cell.transform.position.x, lowestX, highestX, 0f, highestMonsterLevel.toFloat()) //(it.cell.transform.position.y - lowestY) / (highestMonsterLevel-1)) + 1
      var canSpawnMonster = false
      for(pair in Settings.spawnTableMaze){
        println("Trying to spawn ${pair.value?.first}")
        if(pair.value != null && pair.value!!.second <= cellDifficulty + 1){
          canSpawnMonster = true
        }
        if(canSpawnMonster && rand <= pair.key){
          val position = it.cell.transform.position.sub(-0f, -0.25f)
          val kClass = Class.forName("com.bosowski.oslark.gameObjects.prefabs.monsters.${pair.value!!.first}").kotlin
          val monster = kClass.constructors.first().call(position) as Monster
          monster.instantiate()
          spawnedMonsters.add(monster)
          break
        }
        else if(canSpawnMonster){
          break
        }
      }
    }

    println("Instantiated ${spawnedMonsters.size} monsters.")

    totalSpawnRate = 0
    highestMonsterLevel = 0

    Settings.spawnTableRooms.forEach {
      totalSpawnRate += it.key
      if(it.value != null && highestMonsterLevel < it.value!!.second){
        highestMonsterLevel = it.value!!.second
      }
    }

    lowestX = Float.MAX_VALUE
    highestX = Float.MIN_VALUE
    dungeonRooms.forEach{
      if (lowestX > it.cells.keys.first().x) lowestX = it.cells.keys.first().x
      if (highestX < it.cells.keys.first().x) highestX = it.cells.keys.first().x
    }

    println("monster order =  ${Settings.spawnTableRooms}")

    dungeonRooms.forEach { room ->
      val roomDifficulty = Util.map(room.cells.keys.first().x, lowestX, highestX, 0f, highestMonsterLevel.toFloat()) //(it.cell.transform.position.y - lowestY) / (highestMonsterLevel-1)) + 1

      room.cells.forEach{ cell_k, cell_v ->
        val rand = Util.randomInt(random, 0, totalSpawnRate)
        var canSpawnMonster = false
        for(pair in Settings.spawnTableRooms){
          println("Trying to spawn ${pair.value?.first}")
          if(pair.value != null && pair.value!!.second <= roomDifficulty + 1){
            canSpawnMonster = true
          }
          if(canSpawnMonster && rand <= pair.key) {
            val position = cell_k.sub(-0f, -0.25f)
            val kClass = Class.forName("com.bosowski.oslark.gameObjects.prefabs.monsters.${pair.value!!.first}").kotlin
            val monster = kClass.constructors.first().call(position) as Monster
            monster.instantiate()
            spawnedMonsters.add(monster)
            break
          }
          else if(canSpawnMonster){
            break
          }
        }
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
    //colourMazeCells()
    //colourRooms()
    created = true
    if(dungeonCells.isEmpty()){
      return false
    }
    var playerStartingPos = dungeonCells.keys.first()
    dungeonCells.keys.forEach {
      if(playerStartingPos.x > it.x){
        playerStartingPos = it
      }
    }

    World.instance!!.player.transform.body!!.setTransform(Vector2(playerStartingPos.x, playerStartingPos.y + 0.25f), 0f)
    return true
  }

  fun clear() {
    for (cell in dungeonCells.values) {
      cell.clear()
    }
    spawnedMonsters.forEach{
      //destroy the monster only if it hasn't been previously destroyed.
      if(World.instance!!.gameObjects.contains(it)){
        it.destroy()
      }
    }
  }

  private fun colourMazeCells(){
    maze!!.cells.forEach{
      (it.value.cell.getComponent(TextureComponent::class.java.simpleName) as TextureComponent?)?.color = Color.FIREBRICK
    }
  }

  private fun colourRooms(){
    val colors = listOf(Color.RED, Color.BLUE, Color.GOLD, Color.GRAY, Color.PURPLE)
    var index = 0
    dungeonRooms.forEach {room ->
      val color = colors[index]
      if(index < colors.size -1){
        index++
      }
      else{
        index = 0
      }
      room.cells.forEach { cell_k, cell_v ->
        cell_v.cell.getComponents().forEach {
          if(it is TextureComponent){
            it.color = color
          }
        }
      }
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