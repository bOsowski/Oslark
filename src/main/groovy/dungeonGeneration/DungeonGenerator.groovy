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

  void clear() {
    for (DungeonCell cell : dungeonCells.values()) {
      cell.clear()
    }
  }

  void create() {
    if (created) {
      return
    }

    //creating rooms.
    for (int i = 0; i < roomCreationAttempts; i++) {
      DungeonRoom room = new DungeonRoom(minRoomSize, maxRoomSize, bounds, dungeonRooms)
      if (room.create()) {
        dungeonRooms.add(room)
        dungeonCells.putAll(room.cells)
      }
    }
    println("Finished creating rooms.")

    //creating the maze.
    maze = new DungeonMaze(bounds, dungeonRooms)
    maze.create()
    dungeonCells.putAll(maze.cells)
    println("Finished creating maze.")

    //shrinking the maze.
    Iterator<Map.Entry<Vector2, DungeonCell>> entryIterator = dungeonCells.entrySet().iterator()
    boolean deletedAny = true
    while (deletedAny) {
      while (entryIterator.hasNext()) {
        deletedAny = false
        Map.Entry<Vector2, DungeonCell> entry = entryIterator.next()
        if (entry.value.getNeighbours(dungeonCells).size() == 1) {
          entryIterator.remove()
          deletedAny = true
        }
      }
    }
    println("Finished shirking maze.")

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

    //trying to create the maze since there might be more space.
    maze.create()
    dungeonCells.putAll(maze.cells)
    println("Finished recreating maze.")

    //create walls around all the cells.
    for (DungeonCell cell : dungeonCells.values()) {
      cell.setUpWalls(dungeonCells)
    }
    println("Finished adding walls.")

    println("Maze created.")
    created = true
  }

  ArrayList<Terrain> generation() {
    ArrayList<Terrain> allObjects = new ArrayList<>()
    dungeonCells.each{ k, v ->
      allObjects.addAll(v.walls.values())
      allObjects.add(v.terrain)
    }
    return allObjects
  }
}