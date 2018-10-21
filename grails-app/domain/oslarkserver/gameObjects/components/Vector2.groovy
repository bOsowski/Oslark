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
    public String toString() {
        return """{x:${x}, y:${y}}"""
    }
}
