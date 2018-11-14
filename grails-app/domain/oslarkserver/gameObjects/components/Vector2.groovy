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

  @Override
  boolean equals (Object obj) {
    if (super.equals(obj)) return true
    if (obj == null) return false
    if (getClass() != obj.getClass()) return false
    com.badlogic.gdx.math.Vector2 other = new com.badlogic.gdx.math.Vector2(x,y)
    if (NumberUtils.floatToIntBits(x) != NumberUtils.floatToIntBits(other.x)) return false
    if (NumberUtils.floatToIntBits(y) != NumberUtils.floatToIntBits(other.y)) return false
    return true
  }

  @Override
  int hashCode () {
    final int prime = 31
    int result = 1
    result = prime * result + NumberUtils.floatToIntBits(x)
    result = prime * result + NumberUtils.floatToIntBits(y)
    return result
  }
}
