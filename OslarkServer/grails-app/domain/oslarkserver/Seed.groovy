package oslarkserver

import oslarkserver.gameObjects.GameCharacter

class Seed {

    long value

    static hasMany = [highscores: Highscore]
    static transient belongsTo = [gameCharacter: GameCharacter]

    static constraints = {
        value null: false
        value unique: true
    }

    @Override
    String toString(){
        return "${value}"
    }

    String toJson(){
        return "{$value}"
    }
}
