package oslarkserver.gameObjects.components

import com.badlogic.gdx.utils.NumberUtils

class Vector2 {

  float x
  float y

  Vector2(float x, float y) {
    this.x = x
    this.y = y
  }

  Vector2(double x, double y) {
    this.x = (float) x
    this.y = (float) y
  }

  Vector2(Vector2 vector2) {
    this.x = vector2.x
    this.y = vector2.y
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

  Vector2 add(Vector2 v){
    x += v.x
    y += v.y
    return this  }

  Vector2 sub(Vector2 v){
    x -= v.x
    y -= v.y
    return this  }

  boolean equals(o) {
    if (this.is(o)) return true
    if (getClass() != o.class) return false

    Vector2 vector2 = (Vector2) o

    if (Float.compare(vector2.x, x) != 0) return false
    if (Float.compare(vector2.y, y) != 0) return false

    return true
  }

  int hashCode() {
    int result
    result = (x != +0.0f ? Float.floatToIntBits(x) : 0)
    result = 31 * result + (y != +0.0f ? Float.floatToIntBits(y) : 0)
    return result
  }
}
