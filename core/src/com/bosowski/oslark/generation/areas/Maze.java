package com.bosowski.oslark.generation.areas;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.bosowski.oslark.World;
import com.bosowski.oslark.enums.Direction;
import com.bosowski.oslark.gameObjects.Terrain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Stack;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Thread.sleep;

public class Maze {

    private HashMap<Vector2, DungeonCell> cells = new HashMap<>();
    private Rectangle parentArea;
    private ArrayList<DungeonRoom> rooms;

    public Maze(Rectangle parentArea, ArrayList<DungeonRoom> rooms) {
        this.rooms = rooms;
        this.parentArea = parentArea;
    }

    private boolean isMoveValid(Vector2 currentPosition, Direction chosenDirection) {
        Vector2 backStep = new Vector2(currentPosition).sub(chosenDirection.value);
        if (cells.containsKey(currentPosition) || cells.containsKey(backStep) || currentPosition.x < parentArea.x || currentPosition.x > parentArea.x + parentArea.width || currentPosition.y < parentArea.y || currentPosition.y > parentArea.y + parentArea.width) {
            return false;
        }
        for (DungeonRoom room : rooms) {
            if (room.getCells().containsKey(currentPosition) || room.getCells().containsKey(backStep)) {
                return false;
            }
        }
        return true;
    }

    private boolean isFree(Vector2 position) {
        if (cells.containsKey(position)) {
            return false;
        }

        for (DungeonRoom room : rooms) {
            if (room.getCells().containsKey(position)) {
                return false;
            }
        }
        return true;
    }

    public void create() {
        for (int x = (int) parentArea.x; x < parentArea.x + parentArea.width; x++) {
            for (int y = (int) parentArea.y; y < parentArea.y + parentArea.height; y++) {
                DungeonCell cell;
                Stack<Vector2> stack = new Stack<>();
                Vector2 currentPosition = parentArea.getPosition(new Vector2(x, y));

                if (isFree(currentPosition)) {
                    stack.add(currentPosition);
                    cell = new DungeonCell("floor1", new Vector3(currentPosition.x, currentPosition.y, -1), false);
                    cells.put(currentPosition, cell);

                    while (!stack.isEmpty()) {
                        final ArrayList<Direction> directions = new ArrayList<>(Direction.getDirections());
                        while (!directions.isEmpty()) {
                            final Direction chosenDir = directions.get(ThreadLocalRandom.current().nextInt(0, directions.size()));
                            currentPosition = new Vector2(new Vector2(currentPosition).add(chosenDir.value)).add(chosenDir.value);
                            if (!isMoveValid(currentPosition, chosenDir)) {
                                directions.remove(chosenDir);
                                currentPosition = new Vector2(stack.peek());
                            } else {
                                cell = new DungeonCell("floor1", new Vector3(currentPosition.x, currentPosition.y, -1), false);
                                cells.put(currentPosition, cell);
                                stack.add(currentPosition);
                                Vector2 secondTile = new Vector2(currentPosition).sub(chosenDir.value);
                                cell = new DungeonCell("floor1", new Vector3(secondTile.x, secondTile.y, -1), false);
                                cells.put(secondTile, cell);
                                break;
                            }
                        }
                        if (directions.isEmpty()) {
                            currentPosition = new Vector2(stack.pop());
                        }
                    }
                    System.out.println("Maze created.");
                }
            }
        }

        System.out.println("FINISHED");
    }

    public HashMap<Vector2, DungeonCell> getCells() {
        return cells;
    }

    public void clear() {
        for(DungeonCell cell: cells.values()){
            cell.clear();
        }
        cells.clear();
        rooms.clear();
    }
}
