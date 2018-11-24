package test

import com.bosowski.oslark.components.ColliderComponent
import com.bosowski.oslark.gameObjects.GameObject
import org.junit.Test

class ColliderComponentTest {

  @Test
  fun conceptTest(){
    val testObject = GameObject()
    val body = testObject.transform.body
    val collider = ColliderComponent()
    testObject.addComponent(collider)
    assert(body == collider.body)
  }
}