//package com.bosowski.oslark.generation.areas;
//
//import com.badlogic.gdx.math.Vector2;
//import com.badlogic.gdx.math.Vector3;
//import com.bosowski.oslark.World;
//import com.bosowski.oslark.gameObjects.GameObject;
//import com.bosowski.oslark.gameObjects.Terrain;
//
//import java.util.ArrayList;
//
//public class TileArea {
//    private ArrayList<Vector2> tiles = new ArrayList<>();
//    private ArrayList<GameObject> instantiatedGameObjects = new ArrayList<>();
//    int width;
//    int height;
//
//    public TileArea(int width, int height) {
//        this.width = width;
//        this.height = height;
//    }
//
//    public TileArea() {
//    }
//
//    public Vector2 getTile(int x, int y) {
//        System.out.println("Trying to get (" + x + ", " + y + ")");
//        int index = x + width * y;
//        System.out.println(index);
//        return tiles.get(index);
//    }
//
//    public void add(Vector2 pos) {
//        Terrain terrain = new Terrain(0, "objects/floor1", new Vector3(pos.x, pos.y, -1), false);
//        World.instance.instantiate(terrain);
//        tiles.add(pos);
//        instantiatedGameObjects.add(terrain);
//    }
//
//    public void add(Vector2 pos, Maze.MazeSize mazeSize) {
//        for (Vector2 dir : mazeSize.getDirections()) {
//            //Terrain terrain = new Terrain(0, "floor1", new Vector3(pos.x + dir.x, pos.y + dir.y, -1), false);
//            DungeonCell cell = new DungeonCell("floor1", new Vector3(pos.x + dir.x, pos.y + dir.y, -1), false);
//            cell.instantiate();
//            //World.instance.instantiate(cell);
//            tiles.add(new Vector2(pos).add(dir));
//            instantiatedGameObjects.add(cell);
//        }
//    }
//
//    public void remove(Vector2 pos) {
//        GameObject tileToRemove = null;
//        for (GameObject obj : instantiatedGameObjects) {
//            if (obj.getPosition() == new Vector3(pos.x, pos.y, -1)) {
//                tileToRemove = obj;
//                break;
//            }
//        }
//        World.instance.getGameObjects().remove(tileToRemove);
//    }
//
//
//    public ArrayList<Vector2> getTiles() {
//        return tiles;
//    }
//
//    public void setTiles(ArrayList<Vector2> tiles) {
//        this.tiles = tiles;
//    }
//}
