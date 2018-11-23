package test.components

import com.bosowski.oslark.components.Component
import com.bosowski.oslark.gameObjects.GameObject
import org.junit.Test

class ComponentTest {

    class TestComponent : Component() {
        override fun awake() {}
        override fun start() {}
        override fun update(deltaTime: Float) {}
    }

    @Test
    fun getNameTest(){
        val testObject = GameObject()
        testObject.addComponent(TestComponent())
        assert(testObject.getComponent("TestComponent") != null)
    }
}