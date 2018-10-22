package oslarkserver.gameObjects.components

class Vector2 {

    float x
    float y

    Vector2(float x, float y){
        this.x = x
        this.y = y
    }

    static constraints = {
    }


    @Override
    String toString() {
        return """{x:${x}, y:${y}}"""
    }

    String toJson() {
        return """{x:${x}, y:${y}}"""
    }
}
