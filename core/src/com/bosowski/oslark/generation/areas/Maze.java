package com.bosowski.oslark.generation.areas;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
import java.util.concurrent.ThreadLocalRandom;

public class Maze {

    private TileArea tiles = new TileArea();

    public Maze() {
        tiles.add(new Vector2(7, 1), MazeSize.BIG);
        createMaze(new Vector2(0, 0), new Vector2(10, 10), MazeSize.BIG);
    }

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
        tiles.add(stack.peek(), mazeSize);

        while (!stack.isEmpty()) {
            System.out.println("\n\n\nStarting algorithm. Amount of tiles = " + tiles.getTiles().size());
            final ArrayList<Vector2> directions = new ArrayList<>();
            directions.add(new Vector2(0, mulVec));
            directions.add(new Vector2(0, -mulVec));
            directions.add(new Vector2(mulVec, 0));
            directions.add(new Vector2(-mulVec, 0));

            while (!directions.isEmpty()) {
                final Vector2 chosenDir = directions.get(ThreadLocalRandom.current().nextInt(0, directions.size()));
                System.out.println("Tried direction = " + chosenDir);
                currentPosition = new Vector2(new Vector2(currentPosition).add(chosenDir)).add(chosenDir);
                if (containsPos(currentPosition, mazeSize.getDirections()) || containsPos(new Vector2(currentPosition).sub(chosenDir), mazeSize.getDirections()) || currentPosition.x < leftCorner.x || currentPosition.x > rightCorner.x || currentPosition.y < leftCorner.y || currentPosition.y > rightCorner.y) {
                    System.out.println("Blockage encountered");
                    directions.remove(chosenDir);
                    currentPosition = new Vector2(stack.peek());
                } else {
                    System.out.println("Move " + chosenDir + " successful.");
                    tiles.add(currentPosition, mazeSize);
                    stack.add(currentPosition);
                    Vector2 secondTile = new Vector2(currentPosition).sub(chosenDir);
                    tiles.add(secondTile, mazeSize);
                    break;
                }
            }
            if (directions.isEmpty()) {
                System.out.println("Popping the stack.");
                currentPosition = new Vector2(stack.pop());
            }
        }
        System.out.println("FINISHED");
    }

    public boolean containsPos(Vector2 position, ArrayList<Vector2> neighbouringPositionsToCheck) {

        for (Vector2 tile : tiles.getTiles()) {
            System.out.println("tile = "+tile.toString());
        }
        for (Vector2 tile : tiles.getTiles()) {
            for (Vector2 other : neighbouringPositionsToCheck) {
                Vector2 posToCheck = new Vector2(other).add(position);
                System.out.println("Checking pos = "+posToCheck);
                if (tile.x == posToCheck.x && tile.y == posToCheck.y) {
                    return true;
                }
            }
        }
        System.out.println();
        return false;
    }
}
