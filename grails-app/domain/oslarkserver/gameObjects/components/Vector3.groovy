package oslarkserver.gameObjects.components

class Vector3 extends Vector2{

    float z

    Vector3(float x, float y, float z){
        super(x, y)
        this.z = z
    }

    static constraints = {
    }


    @Override
    String toString() {
        return "{x:${x}, y:${y}, z:${z}}"
    }

    String toJson() {
        return "{x:${x}, y:${y}, z:${z}}"
    }
}
