package com.bosowski.oslark.generation.areas;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.bosowski.oslark.World;
import com.bosowski.oslark.enums.Direction;
import com.bosowski.oslark.gameObjects.Terrain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

public class DungeonCell extends Terrain {

    HashMap<Direction, Terrain> walls = new HashMap<>();
    private static final float chanceOfDifferentTexture = 0.03f;

    public DungeonCell(String name, Vector3 position, boolean collides) {
        super(ThreadLocalRandom.current().nextFloat() <= chanceOfDifferentTexture ? "floor"+ThreadLocalRandom.current().nextInt(4,11) : "floor4", position, collides);
    }

    public void instantiate() {
        World.instance.instantiate(this);
        for (Terrain wall : walls.values()) {
            World.instance.instantiate(wall);
        }
    }

    public Vector2 getVector2() {
        return new Vector2(position.x, position.y);
    }

    public void setUpWalls(HashMap<Vector2, DungeonCell> otherCells) {
        for (Direction direction : Direction.getDirections()) {
            if (!otherCells.containsKey(getVector2().add(direction.value))) {
                addWall(direction);
            }
        }
    }

    public void clear() {
        for (Terrain wall : walls.values()) {
            World.instance.destroy(wall);
        }
        walls.clear();
        World.instance.destroy(this);
    }

    public ArrayList<DungeonCell> getNeighbours(HashMap<Vector2, DungeonCell> otherCells) {
        ArrayList<DungeonCell> neighbours = new ArrayList<>();
        for (Direction direction : Direction.getDirections()) {
            Vector2 neighbourPosition = getVector2().add(direction.value);
            if (otherCells.containsKey(neighbourPosition)) {
                neighbours.add(otherCells.get(neighbourPosition));
            }
        }
        return neighbours;
    }

    private void addWall(Direction direction) {
        Terrain wall;

        if (direction == Direction.LEFT) {
            wall = new Terrain("wallLeft", new Vector3(position.x, position.y, 0), true);
            wall.getCollisionBox().y = wall.getCollisionBox().y - wall.getOrigin().y;
            wall.setDimension(new Vector2(0.2f, 2f));
            wall.getCollisionBox().width = wall.getDimension().x;
            wall.setOrigin(new Vector2(0.5f, 0.5f));
            walls.put(Direction.LEFT, wall);
        } else if (direction == Direction.RIGHT) {
            wall = new Terrain("wallRight", new Vector3(position.x, position.y, 0), true);
            wall.setDimension(new Vector2(-0.2f, 2f));
            wall.getCollisionBox().x = wall.getPosition().x + wall.getOrigin().x;
            wall.getCollisionBox().y = wall.getPosition().y - wall.getOrigin().y;
            wall.getCollisionBox().width = wall.getDimension().x;
            wall.setOrigin(new Vector2(-0.5f, 0.5f));
            walls.put(Direction.RIGHT, wall);
        } else if (direction == Direction.DOWN) {
            wall = new Terrain("wallDown", new Vector3(position.x, position.y, 0), true);
            wall.getCollisionBox().y = wall.getCollisionBox().y - wall.getOrigin().y;
            wall.getCollisionBox().height = 0.1f;
            walls.put(Direction.DOWN, wall);
        } else {
            float chance = ThreadLocalRandom.current().nextFloat();
            int wallType = 4;
            if(chance <= chanceOfDifferentTexture){
                wallType = ThreadLocalRandom.current().nextInt(0, 11);
            }
            if (wallType < 4) {
                name = "floor" + wallType;
                setUp();
            }
            wall = new Terrain("wallUp" + wallType, new Vector3(position.x, position.y + 1f, 0), true);
            if (wallType < 2) {
                wall.setDimension(new Vector2(1f, 1.117f));
            } else if (wallType == 2) {
                wall.setDimension(new Vector2(1f, 1.495f));
            }
            wall.getCollisionBox().y = wall.getCollisionBox().y - wall.getOrigin().y;
            walls.put(Direction.UP, wall);
        }
    }
}
