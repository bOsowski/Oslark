package com.bosowski.oslark.generation.areas;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.bosowski.oslark.World;
import com.bosowski.oslark.gameObjects.GameObject;
import com.bosowski.oslark.gameObjects.Terrain;

import java.util.ArrayList;

public class TileArea {
    private ArrayList<Vector2> tiles = new ArrayList<>();
    private ArrayList<GameObject> instantiatedGameObjects = new ArrayList<>();
    int width;
    int height;

    public TileArea(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public TileArea() {
    }

    public Vector2 getTile(int x, int y) {
        System.out.println("Trying to get (" + x + ", " + y + ")");
        int index = x + width * y;
        System.out.println(index);
        return tiles.get(index);
    }

    public ArrayList<Vector2> add(Vector2 pos, Maze.MazeSize mazeSize) {

        ArrayList<Vector2> createdTiles = new ArrayList<>();
        //Vector2 dir = new Vector2(0,0);
        for (Vector2 dir : mazeSize.getDirections()) {
            Terrain terrain = new Terrain(0, "floor1", new Vector3(pos.x + dir.x, pos.y + dir.y, -1), false);
            World.instance.instantiate(terrain);
            System.out.println("Adding a tile at " + terrain.getPosition().toString());
            tiles.add(new Vector2(pos).add(dir));
            createdTiles.add(new Vector2(pos).add(dir));
            instantiatedGameObjects.add(terrain);
        }

        return createdTiles;
    }

    public void remove(Vector2 pos) {
        GameObject tileToRemove = null;
        for (GameObject obj : instantiatedGameObjects) {
            if (obj.getPosition() == new Vector3(pos.x, pos.y, -1)) {
                tileToRemove = obj;
                break;
            }
        }
        World.instance.getGameObjects().remove(tileToRemove);
    }


    public ArrayList<Vector2> getTiles() {
        return tiles;
    }

    public void setTiles(ArrayList<Vector2> tiles) {
        this.tiles = tiles;
    }
}
