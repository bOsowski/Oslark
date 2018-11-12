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

public class Maze implements Runnable{

    //private TileArea mazeTiles = new TileArea();
    private HashMap<Vector2, DungeonCell> mazeCells = new HashMap<>();
    private ArrayList<DungeonRoom> dungeonRooms = new ArrayList<>();

    public Maze() {

    }

    public void removeWalls(){
        for(Vector2 pos: mazeCells.keySet()){
            for(Direction direction: Direction.getDirections()){
                if(mazeCells.containsKey(new Vector2(pos).add(direction.value))){
//                    try {
//                        sleep(10);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                    mazeCells.get(pos).walls.remove(direction);
                }
            }
            mazeCells.get(pos).instantiate();
        }
        System.out.println("Walls removed.");
    }

    @Override
    public void run() {
//        for(int i = 0; i<1000; i++){
////            try {
////                sleep(100);
////            } catch (InterruptedException e) {
////                e.printStackTrace();
////            }
//            DungeonRoom room = new DungeonRoom(5, 25, new Rectangle(-25,-25,100,100), dungeonRooms);
//            if(room.create()){
//                dungeonRooms.add(room);
//            }
//        }
        createMaze(new Vector2(0, 0), new Vector2(10, 10), MazeSize.SMALL);
        removeWalls();
    }



//    public void createRoom(int minSize, int maxSize, Vector2 corner, int areaWidth, int areaHeight){
//        int width = ThreadLocalRandom.current().nextInt(minSize, maxSize+1);
//        int height = ThreadLocalRandom.current().nextInt(minSize, maxSize+1);
//        Vector2 startingPosition = new Vector2();
//        startingPosition.x = ThreadLocalRandom.current().nextInt((int)corner.x, (int)corner.x+areaWidth-width*2);
//        startingPosition.y = ThreadLocalRandom.current().nextInt((int)corner.y, (int)corner.y+areaHeight-height*2);
//
//
//        Rectangle bounds = new Rectangle(startingPosition.x, startingPosition.y ,width, height);
//
//        System.out.println(roomTiles.getTiles().toString());
//
//        for(Vector2 tile: roomTiles.getTiles()){
//            if(bounds.contains(tile)){
//                return;
//            }
//        }
//
//        String name = "floor"+ThreadLocalRandom.current().nextInt(1,9);
//        for(int i = 0; i<width; i++){
//            for(int j = 0; j<height; j++){
//                Vector2 roomTile = new Vector2(startingPosition.x + i, startingPosition.y + j);
//                Terrain terrain = new Terrain(0, name, new Vector3(roomTile.x, roomTile.y, -0.5f), false);
//                roomTiles.add(roomTile);
//                World.instance.instantiate(terrain);
//            }
//        }
//    }

    public enum MazeSize {
        SMALL(1), BIG(3);

        int value;

        MazeSize(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public ArrayList<Vector2> getDirections() {
            switch (value) {
                case 1:
                    return new ArrayList<>(Arrays.asList(new Vector2(0, 0)));
                case 3:
                    return new ArrayList<>(Arrays.asList(
                            new Vector2(0, 0),  //center
                            new Vector2(0, 1),  //up
                            new Vector2(0, -1), //down
                            new Vector2(1, 0),  //right
                            new Vector2(1, 1),  //top right
                            new Vector2(1, -1), //down right
                            new Vector2(-1, -1),//down left
                            new Vector2(-1, 0), //left
                            new Vector2(-1, 1)  //top left
                    ));
                default:
                    return null;
            }
        }
    }

    public void createMaze(Vector2 leftCorner, Vector2 rightCorner, MazeSize mazeSize) {
        int mulVec = mazeSize.getValue();

        Stack<Vector2> stack = new Stack<>();
        Vector2 currentPosition = new Vector2(leftCorner);

        stack.add(currentPosition);
        DungeonCell cell = new DungeonCell("floor1", new Vector3(currentPosition.x, currentPosition.y, -1), false);
//        cell.instantiate();
        mazeCells.put(currentPosition, cell);

        while (!stack.isEmpty()) {
            final ArrayList<Direction> directions = new ArrayList<>(Direction.getDirections());

            while (!directions.isEmpty()) {
                final Direction chosenDir = directions.get(ThreadLocalRandom.current().nextInt(0, directions.size()));
                currentPosition = new Vector2(new Vector2(currentPosition).add(chosenDir.value)).add(chosenDir.value);
                if (mazeCells.containsKey(currentPosition) || mazeCells.containsKey((new Vector2(currentPosition).sub(chosenDir.value))) || currentPosition.x < leftCorner.x || currentPosition.x > rightCorner.x || currentPosition.y < leftCorner.y || currentPosition.y > rightCorner.y) {
                    directions.remove(chosenDir);
                    currentPosition = new Vector2(stack.peek());
                } else {
                    cell = new DungeonCell("floor1", new Vector3(currentPosition.x, currentPosition.y, -1), false);
                    mazeCells.put(currentPosition, cell);
                    stack.add(currentPosition);
//                    cell.instantiate();
                    Vector2 secondTile = new Vector2(currentPosition).sub(chosenDir.value);
                    cell = new DungeonCell("floor1", new Vector3(secondTile.x, secondTile.y, -1), false);
                    mazeCells.put(secondTile, cell);
//                    cell.instantiate();
                    break;
                }
            }
            if (directions.isEmpty()) {
                currentPosition = new Vector2(stack.pop());
            }
        }
        System.out.println("FINISHED");
    }
}
