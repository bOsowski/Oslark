package com.bosowski.oslark.playerDomains

class Settings{
  companion object {
    val spawnTableMaze = linkedMapOf(70 to null, 20 to Pair("Imp", 300), 19 to Pair("Zombie", 250), 25 to Pair("Skeleton", 150))
    val spawnTableRooms = linkedMapOf(70 to null, 21 to Pair("Demon", 500), 20 to Pair("Imp", 300), 19 to Pair("Zombie", 250), 25 to Pair("Skeleton", 1))
  }
}