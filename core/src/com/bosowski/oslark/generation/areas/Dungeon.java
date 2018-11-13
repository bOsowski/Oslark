package com.bosowski.oslark.generation.areas;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.bosowski.oslark.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Dungeon {
    private HashMap<Vector2, DungeonCell> dungeonCells = new HashMap<>();
    private ArrayList<DungeonRoom> dungeonRooms = new ArrayList<>();
    private Maze maze;
    private boolean created = false;
    private Rectangle bounds;

    private int minRoomSize;
    private int maxRoomSize;
    private int roomCreationAttempts;

    public Dungeon(Rectangle bounds, int minRoomSize, int maxRoomSize, int roomCreationAttempts) {
        this.bounds = bounds;
        this.minRoomSize = minRoomSize;
        this.maxRoomSize = maxRoomSize;
        this.roomCreationAttempts = roomCreationAttempts;
    }

    public void clear() {
        for (DungeonCell cell : dungeonCells.values()) {
            cell.clear();
        }
    }

    public void create() {
        if (created) {
            return;
        }

        for (int i = 0; i < roomCreationAttempts; i++) {
            DungeonRoom room = new DungeonRoom(minRoomSize, maxRoomSize, bounds, dungeonRooms);
            if (room.create()) {
                dungeonRooms.add(room);
                dungeonCells.putAll(room.getCells());
            }
        }

        maze = new Maze(bounds, dungeonRooms);
        maze.create();
        dungeonCells.putAll(maze.getCells());

        int cellsRemoved;
        do {
            cellsRemoved = 0;
            ArrayList<DungeonCell> cellsToRemove = new ArrayList<>();
            for (DungeonCell cell : maze.getCells().values()) {
                ArrayList<DungeonCell> neighbours = cell.getNeighbours(dungeonCells);
                if (neighbours.size() == 1) {
                    World.instance.destroy(cell);
                    dungeonCells.remove(cell.getVector2());
                    cellsToRemove.add(cell);
                    cellsRemoved++;
                }
            }
            for (DungeonCell cellToRemove : cellsToRemove) {
                maze.getCells().remove(cellToRemove.getVector2());
            }
        } while (cellsRemoved != 0);
        System.out.println("Finished shrinking maze.");

        Iterator<DungeonRoom> iter = dungeonRooms.iterator();
        while(iter.hasNext()){
            DungeonRoom room = iter.next();
            if(room.isIsolated(dungeonCells)){
                System.out.println("Clearing room");
                for(Vector2 cell: room.getCells().keySet()){
                    dungeonCells.remove(cell);
                }
                room.clear();
                iter.remove();
            }
        }

        maze.create();

        for (DungeonCell cell : dungeonCells.values()) {
            cell.setUpWalls(dungeonCells);
            cell.instantiate();
        }

        System.out.println("Finished removing walls.");
        created = true;
        World.instance.getPlayer().setPosition(new Vector3(dungeonRooms.get(0).getBounds().x, dungeonRooms.get(0).getBounds().y, 0f));
    }

}
