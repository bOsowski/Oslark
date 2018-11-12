package com.bosowski.oslark.generation.areas;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.bosowski.oslark.World;

import java.util.ArrayList;
import java.util.HashMap;

public class Dungeon {
    private HashMap<Vector2, DungeonCell> dungeonCells = new HashMap<>();
    private ArrayList<DungeonRoom> dungeonRooms = new ArrayList<>();
    private ArrayList<Maze> mazes = new ArrayList<>();
    private boolean created = false;

    public Dungeon() {

    }

    public void clear() {
        for(DungeonCell cell: dungeonCells.values()){
            cell.clear();
        }
        dungeonCells.clear();
        dungeonRooms.clear();
        mazes.clear();
    }

    public void create() {
        if(created){
            return;
        }

        for (int i = 0; i < 250; i++) {
            DungeonRoom room = new DungeonRoom(2, 15, new Rectangle(0, 0, 100, 100), dungeonRooms);
            if (room.create()) {
                dungeonRooms.add(room);
                dungeonCells.putAll(room.getCells());
            }
        }

        Maze maze = new Maze(new Rectangle(0, 0, 100, 100), dungeonRooms);
        mazes.add(maze);
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

        for (DungeonCell cell : dungeonCells.values()) {
            cell.removeWalls(dungeonCells);
        }
        System.out.println("Finished removing walls.");
        created = true;
        World.instance.getPlayer().setPosition(new Vector3(dungeonRooms.get(0).getBounds().x, dungeonRooms.get(0).getBounds().y, 0f));
    }

}
