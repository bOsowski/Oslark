package com.bosowski.oslark.generation.areas;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public class Dungeon {
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
        for (int i = 0; i < roomCreationAttempts; i++) {
            DungeonRoom room = new DungeonRoom(minRoomSize, maxRoomSize, bounds, dungeonRooms, random);
            if (room.create()) {
                dungeonRooms.add(room);
                dungeonCells.putAll(room.getCells());
            }
        }
    }

    private void createMazes() {
        maze = new Maze(bounds, dungeonRooms, random);
        maze.create();
        dungeonCells.putAll(maze.getCells());
    }

    private void shrinkMazes() {
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
    }

    private void removeIsolatedRooms() {
        Iterator<DungeonRoom> iter = dungeonRooms.iterator();
        while (iter.hasNext()) {
            DungeonRoom room = iter.next();
            if (room.isIsolated(dungeonCells)) {
                System.out.println("Clearing room");
                for (Vector2 cell : room.getCells().keySet()) {
                    dungeonCells.remove(cell);
                }
                room.clear();
                iter.remove();
            }
        }
    }

    private void createWallsAndInstantiate(){
        for (DungeonCell cell : dungeonCells.values()) {
            cell.setUpWalls(dungeonCells);
            cell.instantiate();
        }
    }
}