package oslarkserver.gameObjects

class Highscore {

    long score = 0

    static transient belongsTo = [gameCharacter: GameCharacter, seed: Seed]

    static constraints = {
        seed null: false
        gameCharacter null: false
    }

    @Override
    String toString(){
        return "${score} (${gameCharacter})"
    }

    String toJson(){
        return "{seed:${seed.value}, score:$score}"
    }
}
