package com.bosowski.oslark.playerDomains

class Settings{
  companion object {
    val spawnTableMaze = hashMapOf(12 to Pair("Skeleton", 1), 8 to Pair("Zombie", 2), 200 to null)//LinkedHashMap<Float, String>()

    val spawnTableRooms = hashMapOf(12 to Pair("Skeleton", 1), 8 to Pair("Zombie", 2), 3 to Pair("Imp", 4), 2 to Pair("Demon", 5), 200 to null)//LinkedHashMap<Float, String>()
  }

}