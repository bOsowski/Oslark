package dungeonGeneration

import groovy.transform.CompileStatic
import oslarkserver.gameObjects.components.Rectangle
import oslarkserver.gameObjects.components.Vector2
import oslarkserver.gameObjects.enums.Direction
import java.util.concurrent.ThreadLocalRandom

@CompileStatic
class DungeonMaze {

  HashMap<Vector2, DungeonCell> cells = new HashMap<>()
  Rectangle parentArea
  ArrayList<DungeonRoom> rooms

  DungeonMaze(Rectangle parentArea, ArrayList<DungeonRoom> rooms) {
    this.rooms = rooms
    this.parentArea = parentArea
  }

  private boolean isMoveValid(Vector2 currentPosition, Direction chosenDirection) {
    Vector2 backStep = new Vector2(currentPosition).sub(chosenDirection.value)
    if (cells.containsKey(currentPosition) || cells.containsKey(backStep) || currentPosition.x < parentArea.x || currentPosition.x > parentArea.x + parentArea.width || currentPosition.y < parentArea.y || currentPosition.y > parentArea.y + parentArea.width) {
      return false
    }
    for (DungeonRoom room : rooms) {
      if (room.getCells().containsKey(currentPosition) || room.getCells().containsKey(backStep)) {
        return false
      }
    }
    return true
  }

  private boolean isFree(Vector2 position) {
    if (cells.containsKey(position)) {
      return false
    }

    for (DungeonRoom room : rooms) {
      if (room.getCells().containsKey(position)) {
        return false
      }
    }
    return true
  }

  void create() {
    for (int x = (int) parentArea.x; x < parentArea.x + parentArea.width; x += 2) {
      for (int y = (int) parentArea.y; y < parentArea.y + parentArea.height; y += 2) {
        Vector2 currentPosition = new Vector2(x, y)
        if (isFree(currentPosition)) {
          System.out.println("Trying to create maze at (" + x + ", " + y + ")")
          DungeonCell cell
          Stack<Vector2> stack = new Stack<>()
          stack.add(currentPosition)
          cell = new DungeonCell(new Vector2(currentPosition.x, currentPosition.y))
          cells.put(currentPosition, cell)

          while (!stack.isEmpty()) {
            final ArrayList<Direction> directions = new ArrayList<>(Direction.getDirections())
            while (!directions.isEmpty()) {
              final Direction chosenDir = directions.get(ThreadLocalRandom.current().nextInt(0, directions.size()))
              currentPosition = new Vector2(new Vector2(currentPosition).add(chosenDir.value)).add(chosenDir.value)
              if (!isMoveValid(currentPosition, chosenDir)) {
                directions.remove(chosenDir)
                currentPosition = new Vector2(stack.peek())
              } else {
                cell = new DungeonCell(new Vector2(currentPosition.x, currentPosition.y),)
                cells.put(currentPosition, cell)
                stack.add(currentPosition)
                Vector2 secondTile = new Vector2(currentPosition).sub(chosenDir.value)
                cell = new DungeonCell(new Vector2(secondTile.x, secondTile.y))
                cells.put(secondTile, cell)
                break
              }
            }
            if (directions.isEmpty()) {
              currentPosition = new Vector2(stack.pop())
            }
          }
        }
      }
    }
  }

  void clear() {
    cells.values().each { it.clear() }
    cells.clear()
    rooms.clear()
  }
}
