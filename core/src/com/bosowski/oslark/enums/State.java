package com.bosowski.oslark.enums;

public enum State {
    IDLE("idle"), MOVE("move"), ATTACK("attack"), DIE("die"), HIT("hit"), INVALID("invalid");

    public final String name;

    State(String name){
        this.name = name;
    }

    public static State getState(String name){
        switch (name){
            case "idle":
                return IDLE;
            case "move":
                return MOVE;
            case "attack":
                return ATTACK;
            case "die":
                return DIE;
            case "hit":
                return HIT;
            default:
                return INVALID;
        }
    }
}
