package test

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.bosowski.oslark.components.Component
import com.bosowski.oslark.gameObjects.GameObject
import org.junit.Test

class ComponentTest {

    class TestComponent(owner: GameObject) : Component(owner) {
        override fun destroy() {}
        override fun render(batch: SpriteBatch) {}
        override fun awake() {}
        override fun start() {}
        override fun update(deltaTime: Float) {}
    }

    @Test
    fun getNameTest(){
        val testObject = GameObject()
        testObject.addComponent(TestComponent(testObject))
        assert(testObject.getComponent("TestComponent") != null)
    }
}