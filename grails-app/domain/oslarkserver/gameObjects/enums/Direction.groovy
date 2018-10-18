package oslarkserver.gameObjects.enums

import oslarkserver.gameObjects.components.Vector3

enum Direction {
    UP("up", new Vector3(0.0f,1.0f,0.0f)), RIGHT("right", new Vector3(1.0f,0.0f,0.0f)), DOWN("down", new Vector3(0.0f,-1.0f,0.0f)), LEFT("left", new Vector3(-1.0f,0.0f,0.0f)), INVALID("invalid", new Vector3(0.0f,0.0f,0.0f));

    final String name;
    final Vector3 value;

    Direction(String name, Vector3 value){
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
                return INVALID;
        }
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
                System.err.println("Direction.getRandom - ERROR.");
                return null;
        }
    }
}