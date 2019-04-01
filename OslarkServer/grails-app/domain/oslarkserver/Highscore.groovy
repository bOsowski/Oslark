package oslarkserver

import oslarkserver.gameObjects.GameCharacter

class Highscore {

    long score = 0

    static transient belongsTo = [gameCharacter: GameCharacter, seed: Seed]

    static constraints = {
        seed null: false
    }

    @Override
    String toString(){
        return "${score} (${gameCharacter})"
    }
}
