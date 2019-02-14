package com.bosowski.oslark.playerDomains

class Settings{
  companion object {
    val spawnTableMaze = hashMapOf(10 to Pair("Skeleton", 1), 8 to Pair("Zombie", 2), 50 to null)//LinkedHashMap<Float, String>()
    val spawnTableRooms = hashMapOf(10 to Pair("Skeleton", 1), 8 to Pair("Zombie", 2), 5 to Pair("Demon", 5), 25 to null)//LinkedHashMap<Float, String>()
  }

}