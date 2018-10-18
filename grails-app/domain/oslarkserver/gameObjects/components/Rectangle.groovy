package oslarkserver.gameObjects.components

class Rectangle{

    float x
    float y
    float width
    float height

    static constraints = {
    }

    Rectangle(Rectangle rect){
        this.x = rect.x
        this.y = rect.y
        this.height = rect.height
        this.width = rect.width
    }
}
