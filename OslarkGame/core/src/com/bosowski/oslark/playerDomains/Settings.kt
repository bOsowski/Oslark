package com.bosowski.oslark.playerDomains

class Settings{
  companion object {
    val spawnTableMaze = hashMapOf(200 to null, 8 to Pair("Zombie", 2), 12 to Pair("Skeleton", 1))
    val spawnTableRooms = hashMapOf(200 to null, 15 to Pair("Demon", 500), 14 to Pair("Imp", 4), 8 to Pair("Zombie", 2), 12 to Pair("Skeleton", 1))
  }

}