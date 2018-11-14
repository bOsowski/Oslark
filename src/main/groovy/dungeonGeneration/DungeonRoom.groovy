package dungeonGeneration

import groovy.transform.CompileStatic
import oslarkserver.gameObjects.components.Rectangle
import oslarkserver.gameObjects.components.Vector2
import oslarkserver.gameObjects.enums.Direction

import java.util.concurrent.ThreadLocalRandom

@CompileStatic
class DungeonRoom {

  private HashMap<Vector2, DungeonCell> cells = new HashMap<>()
  private Rectangle bounds

  private int minSize
  private int maxSize
  private Rectangle parentArea
  private ArrayList<DungeonRoom> otherRooms

  DungeonRoom(int minSize, int maxSize, Rectangle parentArea, ArrayList<DungeonRoom> otherRooms) {
    this.minSize = minSize
    this.maxSize = maxSize
    this.parentArea = parentArea
    this.otherRooms = otherRooms
  }

  boolean create() {
    bounds = new Rectangle()
    bounds.height = rand(minSize, maxSize)
    bounds.width = rand(minSize, maxSize)
    bounds.x = rand((int) parentArea.x, (int) (parentArea.x + parentArea.width - bounds.width))
    bounds.y = rand((int) parentArea.y, (int) (parentArea.y + parentArea.height - bounds.height))

    //check if the generated room is colliding with other rooms
    otherRooms.each {
      if (bounds.overlaps(it.getBounds())) {
        return false
      }
    }

    //create the tiles
    (0..bounds.width).each { x ->
      (0..bounds.height).each { y ->
        add(bounds.x + x as float, bounds.y + y as float)
      }
    }
    return true
  }

  private void add(float x, float y) {
    DungeonCell cell = new DungeonCell(new Vector2((int) x, (int) y))
    cells.put(new Vector2(x, y), cell)
  }

  private int rand(float min, float max) {
    return ThreadLocalRandom.current().nextInt((int) min, (int) max + 1)
  }

  HashMap<Vector2, DungeonCell> getCells() {
    return cells
  }

  boolean isIsolated(HashMap<Vector2, DungeonCell> otherCells) {
    for (Direction direction : Direction.getDirections()) {
      for (int x = (int) bounds.x; x < bounds.x + bounds.width; x++) {
        if (
        (otherCells.containsKey(new Vector2(x, bounds.y).add(direction.value))
            || otherCells.containsKey(new Vector2(x, bounds.y + bounds.height as float).add(direction.value)))
            && !cells.containsKey(new Vector2(x, bounds.y).add(direction.value))
            && !cells.containsKey(new Vector2(x, bounds.y + bounds.height as float).add(direction.value))
        ) {
          return false
        }
      }
      for (int y = (int) bounds.y; y < bounds.y + bounds.height; y++) {
        if (
        (otherCells.containsKey(new Vector2(bounds.x, y).add(direction.value))
            || otherCells.containsKey(new Vector2(bounds.x + bounds.width as float, y).add(direction.value)))
            && !cells.containsKey(new Vector2(bounds.x, y).add(direction.value))
            && !cells.containsKey(new Vector2(bounds.x + bounds.width as float, y).add(direction.value))
        ) {
          return false
        }
      }
    }
    return true
  }

  Rectangle getBounds() {
    return bounds
  }

  void clear() {
    for (DungeonCell cell : cells.values()) {
      cell.clear()
    }
    cells.clear()
  }

}
