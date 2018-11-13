package com.bosowski.oslark.generation.areas;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.bosowski.oslark.World;
import com.bosowski.oslark.enums.Direction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

public class DungeonRoom {

    private HashMap<Vector2, DungeonCell> cells = new HashMap<>();
    private Rectangle bounds;

    private int minSize;
    private int maxSize;
    private Rectangle parentArea;
    private ArrayList<DungeonRoom> otherRooms;

    public DungeonRoom(int minSize, int maxSize, Rectangle parentArea, ArrayList<DungeonRoom> otherRooms) {
        this.minSize = minSize;
        this.maxSize = maxSize;
        this.parentArea = parentArea;
        this.otherRooms = otherRooms;
    }

    public boolean create() {
        bounds = new Rectangle();
        bounds.height = rand(minSize, maxSize);
        bounds.width = rand(minSize, maxSize);
        bounds.x = rand((int) parentArea.x, (int) (parentArea.x + parentArea.width - bounds.width));
        bounds.y = rand((int) parentArea.y, (int) (parentArea.y + parentArea.height - bounds.height));

        //check if the generated room is colliding with other rooms
        for (DungeonRoom otherRoom : otherRooms) {
            if (bounds.overlaps(otherRoom.getBounds())) {
                return false;
            }
        }

        //create the tiles
        for (int x = 0; x < bounds.width; x++) {
            for (int y = 0; y < bounds.height; y++) {
                add(bounds.x + x, bounds.y + y);
            }
        }
        return true;
    }

    private void add(float x, float y) {
        DungeonCell cell = new DungeonCell("floor1", new Vector3((int) x, (int) y, -1), false);
        cells.put(new Vector2(x, y), cell);
    }

    private int rand(float min, float max) {
        return ThreadLocalRandom.current().nextInt((int) min, (int) max + 1);
    }

    public HashMap<Vector2, DungeonCell> getCells() {
        return cells;
    }

    public boolean isIsolated(HashMap<Vector2, DungeonCell> otherCells) {
        for (Direction direction : Direction.getDirections()) {
            for (int x = (int) bounds.x; x < bounds.x + bounds.width; x++) {
                if (
                        (otherCells.containsKey(new Vector2(x, bounds.y).add(direction.value))
                                || otherCells.containsKey(new Vector2(x, bounds.y + bounds.height).add(direction.value)))
                                && !cells.containsKey(new Vector2(x, bounds.y).add(direction.value))
                                && !cells.containsKey(new Vector2(x, bounds.y + bounds.height).add(direction.value))
                        ) {
                    return false;
                }
            }
            for (int y = (int) bounds.y; y < bounds.y + bounds.height; y++) {
                if (
                        (otherCells.containsKey(new Vector2(bounds.x, y).add(direction.value))
                                || otherCells.containsKey(new Vector2(bounds.x + bounds.width, y).add(direction.value)))
                                && !cells.containsKey(new Vector2(bounds.x, y).add(direction.value))
                                && !cells.containsKey(new Vector2(bounds.x + bounds.width, y).add(direction.value))
                        ) {
                    return false;
                }
            }
        }
        return true;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void clear() {
        for (DungeonCell cell : cells.values()) {
            cell.clear();
            System.out.println("Room clearing..");
        }
        cells.clear();
    }

}
