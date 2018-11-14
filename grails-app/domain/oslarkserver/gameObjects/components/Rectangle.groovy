package oslarkserver.gameObjects.components

class Rectangle {

  float x
  float y
  float width
  float height

  static constraints = {
  }

  Rectangle(float x, float y, float width, float height) {
    this.x = x
    this.y = y
    this.width = width
    this.height = height
  }

  Rectangle(Rectangle rect) {
    this.x = rect.x
    this.y = rect.y
    this.height = rect.height
    this.width = rect.width
  }

  boolean overlaps(Rectangle other){
    com.badlogic.gdx.math.Rectangle rect = new com.badlogic.gdx.math.Rectangle(x,y,width,height)
    return rect.overlaps(new com.badlogic.gdx.math.Rectangle(other.x, other.y, other.width, other.height))
  }


  @Override
  String toString() {
    return "{" +
        "x:" + x +
        ", y:" + y +
        ", width:" + width +
        ", height:" + height +
        '}'
  }

  String toJson() {
    return "{" +
        "x:" + x +
        ", y:" + y +
        ", width:" + width +
        ", height:" + height +
        '}'
  }
}
