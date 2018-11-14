package com.bosowski.oslark.enums;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Random;

public enum Direction {
    UP("up", new Vector2(0.0f,1.0f)), RIGHT("right", new Vector2(1.0f,0.0f)), DOWN("down", new Vector2(0.0f,-1.0f)), LEFT("left", new Vector2(-1.0f,0.0f));

    public final String name;
    public final Vector2 value;

    Direction(String name, Vector2 value){
        this.name = name;
        this.value = value;
    }

    public static Direction getDirection(String name){
        switch (name){
            case "up":
                return UP;
            case "right":
                return RIGHT;
            case "down":
                return DOWN;
            case "left":
                return LEFT;
            default:
                return null;
        }
    }

    public static Vector2 getDirection(Direction direction){
        return direction.value;
    }

    public static Direction getRandom(){
        Random rand = new Random();
        switch (rand.nextInt(4)){
            case 0:
                return UP;
            case 1:
                return RIGHT;
            case 2:
                return LEFT;
            case 3:
                return DOWN;
            default:
                return null;
        }
    }

    public static ArrayList<Direction> getDirections(){
        ArrayList<Direction> directions = new ArrayList<>();
        directions.add(UP);
        directions.add(DOWN);
        directions.add(LEFT);
        directions.add(RIGHT);
        return directions;
    }
}