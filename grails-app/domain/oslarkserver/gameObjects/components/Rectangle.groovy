package oslarkserver.gameObjects.components

class Rectangle{

    Vector2 position
    float width
    float height

    static constraints = {
    }

    Rectangle(Rectangle rect){
        this.position = rect.position
        this.height = rect.height
        this.width = rect.width
    }

    static embedded = ['position']

    def getX(){
        return position.x
    }

    def getY(){
        return position.y
    }
}
