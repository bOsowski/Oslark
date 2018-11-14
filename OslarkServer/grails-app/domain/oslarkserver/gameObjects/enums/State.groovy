package oslarkserver.gameObjects.enums

enum State {
    IDLE("idle"), MOVE("move"), ATTACK("attack"), DIE("die"), HIT("hit"), INVALID("invalid");

    public final String name;

    State(String name){
        this.name = name;
    }
}