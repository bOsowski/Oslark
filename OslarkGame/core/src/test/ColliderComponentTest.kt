package test

import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.PolygonShape
import com.badlogic.gdx.physics.box2d.Shape
import com.bosowski.oslark.components.ColliderComponent
import com.bosowski.oslark.gameObjects.GameObject
import org.junit.Test

class ColliderComponentTest {

  @Test
  fun conceptTest(){
    val testObject = GameObject()
    val body = testObject.transform.body
    val collider = ColliderComponent(BodyDef.BodyType.StaticBody, PolygonShape(), 0f)
    testObject.addComponent(collider)
    assert(body == collider.body)
  }
}