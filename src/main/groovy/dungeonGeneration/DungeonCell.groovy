package dungeonGeneration

import groovy.transform.CompileStatic
import oslarkserver.gameObjects.Terrain
import oslarkserver.gameObjects.components.Rectangle
import oslarkserver.gameObjects.components.Vector2
import oslarkserver.gameObjects.components.Vector3
import oslarkserver.gameObjects.enums.Direction

import java.util.concurrent.ThreadLocalRandom

@CompileStatic
class DungeonCell {

  HashMap<Direction, Terrain> walls = new HashMap<>()
  private static final float chanceOfDifferentWall = 0.05f
  private static final float chanceOfDifferentFloor = 0.25f

  Terrain terrain

  DungeonCell(Vector2 position) {
    terrain = new Terrain(name: ThreadLocalRandom.current().nextFloat() <= chanceOfDifferentFloor ? "floor" + ThreadLocalRandom.current().nextInt(4, 11) : "floor4", position: new Vector3(position.x, position.y, -1), collides: false, terrain: Terrain.TerrainType.NORMAL)
  }

  Vector2 getVector2() {
    return new Vector2(terrain.position.x, terrain.position.y)
  }

  void setUpWalls(HashMap<Vector2, DungeonCell> otherCells) {
    for (Direction direction : Direction.getDirections()) {
      if (!otherCells.containsKey(getVector2().add(direction.value))) {
        addWall(direction)
      }
    }
  }

  void clear() {
    walls.clear()
  }

  ArrayList<DungeonCell> getNeighbours(HashMap<Vector2, DungeonCell> otherCells) {
    ArrayList<DungeonCell> neighbours = new ArrayList<>()
    Direction.directions.each {
      Vector2 neighbourPosition = getVector2().add(it.value)
      if (otherCells.containsKey(neighbourPosition)) {
        neighbours.add(otherCells.get(neighbourPosition))
      }
    }
    return neighbours
  }

  private void addWall(Direction direction) {
    Terrain wall;
    Rectangle collisionBox = new Rectangle(terrain.position.x - terrain.origin.x as float, terrain.position.y, 1, 1)
    Vector3 position = new Vector3(terrain.position.x, terrain.position.y, 0)
    if (direction == Direction.LEFT) {
      wall = new Terrain(name: "wallLeft", position: position, collides: true, collisionBox: collisionBox);
      wall.getCollisionBox().y = (float) (wall.getCollisionBox().y - wall.getOrigin().y);
      wall.setDimension(new Vector2(0.2f, 2f));
      wall.getCollisionBox().width = wall.getDimension().x;
      wall.setOrigin(new Vector2(0.5f, 0.5f));
      walls.put(Direction.LEFT, wall);
    } else if (direction == Direction.RIGHT) {
      wall = new Terrain(name: "wallRight", position: position, collides: true, collisionBox: collisionBox);
      wall.setDimension(new Vector2(-0.2f, 2f));
      wall.getCollisionBox().x = (float) (wall.getPosition().x + wall.getOrigin().x);
      wall.getCollisionBox().y = (float) (wall.getPosition().y - wall.getOrigin().y);
      wall.getCollisionBox().width = wall.getDimension().x;
      wall.setOrigin(new Vector2(-0.5f, 0.5f));
      walls.put(Direction.RIGHT, wall);
    } else if (direction == Direction.DOWN) {
      wall = new Terrain(name: "wallDown", position: position, collides: true, collisionBox: collisionBox);
      wall.getCollisionBox().y = (float) (wall.getCollisionBox().y - wall.getOrigin().y);
      wall.getCollisionBox().height = 0.1f;
      walls.put(Direction.DOWN, wall);
    } else {
      float chance = ThreadLocalRandom.current().nextFloat();
      int wallType = 4;
      if (chance <= chanceOfDifferentWall) {
        wallType = ThreadLocalRandom.current().nextInt(0, 11);
      }
      if (wallType < 4) {
        terrain.name = "floor" + wallType;
      }
      wall = new Terrain(name: "wallUp" + wallType, position: new Vector3(terrain.position.x, terrain.position.y + 1f as float, 0), collides: true, collisionBox: collisionBox);
      if (wallType < 2) {
        wall.setDimension(new Vector2(1f, 1.117f));
      } else if (wallType == 2) {
        wall.setDimension(new Vector2(1f, 1.495f));
      }
      wall.getCollisionBox().y = (float) (wall.getCollisionBox().y + wall.getOrigin().y);
      walls.put(Direction.UP, wall);
    }
  }

}
