package oslarkserver

class Seed {

    long value

    static hasMany = [highscores: Highscore]

    static constraints = {
        value null: false
        value unique: true
    }

    @Override
    String toString(){
        return "${value}"
    }
}
