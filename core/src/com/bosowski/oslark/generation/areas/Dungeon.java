package com.bosowski.oslark.generation.areas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public class Dungeon {
    public static final String TAG = Dungeon.class.getName();

    private HashMap<Vector2, DungeonCell> dungeonCells = new HashMap<>();
    private ArrayList<DungeonRoom> dungeonRooms = new ArrayList<>();
    private Maze maze;
    private boolean created = false;
    private Rectangle bounds;

    private int minRoomSize;
    private int maxRoomSize;
    private int roomCreationAttempts;

    public final Random random;

    public Dungeon(Rectangle bounds, int minRoomSize, int maxRoomSize, int roomCreationAttempts, long seed) {
        this.bounds = bounds;
        this.minRoomSize = minRoomSize;
        this.maxRoomSize = maxRoomSize;
        this.roomCreationAttempts = roomCreationAttempts;
        this.random = new Random(seed);
    }

    public void create() {
        if (created) {
            return;
        }
        createRooms();
        createMazes();
        shrinkMazes();
        removeIsolatedRooms();
        createWallsAndInstantiate();
        created = true;
    }

    public void clear() {
        for (DungeonCell cell : dungeonCells.values()) {
            cell.clear();
        }
    }

    private void createRooms() {
        Gdx.app.log(TAG, "Creating rooms..");
        for (int i = 0; i < roomCreationAttempts; i++) {
            DungeonRoom room = new DungeonRoom(minRoomSize, maxRoomSize, bounds, dungeonRooms, random);
            if (room.create()) {
                dungeonRooms.add(room);
                dungeonCells.putAll(room.getCells());
            }
        }
        Gdx.app.log(TAG, "Finished creating rooms.");
    }

    private void createMazes() {
        Gdx.app.log(TAG, "Creating mazes..");
        maze = new Maze(bounds, dungeonRooms, random);
        maze.create();
        dungeonCells.putAll(maze.getCells());
        Gdx.app.log(TAG, "Finished creating mazes.");
    }

    private void shrinkMazes() {
        Gdx.app.log(TAG, "Shrinking mazes..");
        Iterator<Map.Entry<Vector2, DungeonCell>> entryIterator;
        boolean deletedAny = true;
        while (deletedAny) {
            entryIterator = dungeonCells.entrySet().iterator();
            deletedAny = false;
            while (entryIterator.hasNext()) {
                Map.Entry<Vector2, DungeonCell> entry = entryIterator.next();
                if (entry.getValue().getNeighbours(dungeonCells).size() <= 1) {
                    entryIterator.remove();
                    deletedAny = true;
                }
            }
        }
        Gdx.app.log(TAG, "Finished shrinking mazes..");
    }

    private void removeIsolatedRooms() {
        Gdx.app.log(TAG, "Removing isolated rooms..");
        Iterator<DungeonRoom> iter = dungeonRooms.iterator();
        while (iter.hasNext()) {
            DungeonRoom room = iter.next();
            if (room.isIsolated(dungeonCells)) {
                for (Vector2 cell : room.getCells().keySet()) {
                    dungeonCells.remove(cell);
                }
                room.clear();
                iter.remove();
            }
        }
        Gdx.app.log(TAG, "Finished removing isolated rooms..");
    }

    private void createWallsAndInstantiate(){
        Gdx.app.log(TAG, "Creating walls and instantiating..");
        for (DungeonCell cell : dungeonCells.values()) {
            cell.setUpWalls(dungeonCells);
            cell.instantiate();
        }
        Gdx.app.log(TAG, "Finished creating walls and instantiating..");
    }
}