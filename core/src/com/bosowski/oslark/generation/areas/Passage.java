package com.bosowski.oslark.generation.areas;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.bosowski.oslark.World;
import com.bosowski.oslark.gameObjects.Terrain;

import org.lwjgl.util.vector.Vector2f;

import java.util.ArrayList;
import java.util.Stack;
import java.util.concurrent.ThreadLocalRandom;

public class Passage extends Thread{

    private TileArea tiles = new TileArea();

    Vector2 mulVec = new Vector2(3,3);

    final static int minWidth = 1;
    final static int maxWidth = 2;

    private TileArea firstLocation;
    private TileArea secondLocation;


    public Passage(TileArea locationA, TileArea locationB){
        createMaze();
//        float aX = locationA.getTile(0,0).x;
//        float bX = locationB.getTile(0,0).x;
//        if(aX < bX){
//            firstLocation = locationA;
//            secondLocation = locationB;
//        }
//        else{
//            firstLocation = locationB;
//            secondLocation = locationA;
//        }
//        System.out.println(secondLocation.width + " " + secondLocation.height);
    }

    public Vector2 getCentre(TileArea location){
        int locationWidth = location.width;
        int locationHeight = location.height;

        return new Vector2(locationWidth/2, locationHeight/2);
    }

    @Override
    public void run() {

    }

    public void createMaze(){
        Vector2 leftCorner = new Vector2(0,0);//= firstLocation.getTile(0, 0);
        Vector2 rightCorner = new Vector2(100,100);//secondLocation.getTile(secondLocation.width-1, secondLocation.height-1);
        Stack<Vector2> stack = new Stack<>();

        Vector2 currentPosition = new Vector2(leftCorner);

        stack.add(currentPosition);
        tiles.add(stack.peek());

        while (!stack.isEmpty()){
            System.out.println("\n\n\nStarting algorithm. Amount of tiles = "+tiles.getTiles().size());
            ArrayList<Vector2> directions = new ArrayList<>();

            directions.add(new Vector2(0,1));
            directions.add(new Vector2(0,-1));
            directions.add(new Vector2(1,0));
            directions.add(new Vector2(-1,0));

            while(!directions.isEmpty()){
                Vector2 chosenDir = directions.get(ThreadLocalRandom.current().nextInt(0, directions.size()));
                Vector2 triedDirection = new Vector2(chosenDir).mulAdd(chosenDir, mulVec);
                System.out.println("Tried direction = "+triedDirection);
                currentPosition = new Vector2(currentPosition).add(new Vector2(triedDirection));
                if(containsPos(currentPosition) || currentPosition.x < leftCorner.x || currentPosition.x > rightCorner.x || currentPosition.y < leftCorner.y || currentPosition.y > rightCorner.y){
                    System.out.println("Blockage encountered");
                    directions.remove(chosenDir);
                    currentPosition = new Vector2(stack.peek());
                }
                else{
                    System.out.println("Move "+ triedDirection +" successful.");
                    stack.add(new Vector2(currentPosition));
                    tiles.add(stack.peek());
                    tiles.add(new Vector2(new Vector2(currentPosition).sub(new Vector2(chosenDir))));
                    break;
                }
            }
            if(directions.isEmpty()){
                System.out.println("Popping the stack.");
                currentPosition = new Vector2(stack.pop());
            }

//            try {
//                sleep(5);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }
        System.out.println("FINISHED");
    }

    public boolean containsPos(Vector2 position){
        for(Vector2 pos: tiles.getTiles()){
            if(pos.x == position.x && pos.y == position.y){
                System.out.println(position.toString() + "---------------- encountered at : "+pos.toString());
                return true;
            }
        }
        return false;
    }
}
