package dungeonGeneration

import groovy.transform.CompileStatic
import oslarkserver.gameObjects.Terrain
import oslarkserver.gameObjects.components.Rectangle
import oslarkserver.gameObjects.components.Vector2

@CompileStatic
class DungeonGenerator {
  HashMap<Vector2, DungeonCell> dungeonCells = new HashMap<>()
  ArrayList<DungeonRoom> dungeonRooms = new ArrayList<>()
  DungeonMaze maze
  private boolean created = false
  private Rectangle bounds

  private int minRoomSize
  private int maxRoomSize
  private int roomCreationAttempts

  DungeonGenerator(Rectangle bounds, int minRoomSize, int maxRoomSize, int roomCreationAttempts) {
    this.bounds = bounds
    this.minRoomSize = minRoomSize
    this.maxRoomSize = maxRoomSize
    this.roomCreationAttempts = roomCreationAttempts
  }

  void create() {
    if (created) {
      return
    }
    createRooms()
    createMaze()
    shrinkMaze()
//    removeIsolatedRooms()
    createWalls()
    println("Maze created.")
    created = true
  }

  void clear() {
    for (DungeonCell cell : dungeonCells.values()) {
      cell.clear()
    }
  }

  private void createWalls(){
    for (DungeonCell cell : dungeonCells.values()) {
      cell.setUpWalls(dungeonCells)
    }
    println("Finished adding walls.")
  }

  private void removeIsolatedRooms() {
    //removing isolated rooms.
    Iterator<DungeonRoom> iter = dungeonRooms.iterator()
    while (iter.hasNext()) {
      DungeonRoom room =iter.next()
      if (room.isIsolated(dungeonCells)) {
        for (Vector2 cell : room.cells.keySet()) {
          dungeonCells.remove(cell)
        }
        iter.remove()
      }
    }
    println("Finished removing isolated rooms.")
  }

  private void createMaze(){
    maze = new DungeonMaze(bounds, dungeonRooms)
    maze.create()
    dungeonCells.putAll(maze.cells)
    println("Finished creating maze.")
  }

  private void shrinkMaze(){
    Iterator<Map.Entry<Vector2, DungeonCell>> entryIterator
    boolean deletedAny = true
    while (deletedAny) {
      entryIterator = dungeonCells.entrySet().iterator()
      deletedAny = false
      while (entryIterator.hasNext()) {
        Map.Entry<Vector2, DungeonCell> entry = entryIterator.next()
        if (entry.value.getNeighbours(dungeonCells).size() == 1) {
          entryIterator.remove()
          deletedAny = true
        }
      }
    }
    println("Finished shirking maze.")
  }

  private void createRooms(){
    (0..roomCreationAttempts).each{
      DungeonRoom room = new DungeonRoom(minRoomSize, maxRoomSize, bounds, dungeonRooms)
      if (room.create()) {
        dungeonRooms.add(room)
        dungeonCells.putAll(room.cells)
      }
    }
    println("Finished creating rooms.")
  }

  ArrayList<Terrain> generation() {
    ArrayList<Terrain> allObjects = new ArrayList<>()
    dungeonCells.each{ k, v ->
      allObjects.add(v.terrain)
      allObjects.addAll(v.walls.values())
    }
    return allObjects
  }
}