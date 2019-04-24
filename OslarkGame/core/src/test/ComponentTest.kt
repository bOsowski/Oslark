package test

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.physics.box2d.BodyDef
import com.bosowski.oslark.components.AbstractComponent
import com.bosowski.oslark.gameObjects.GameObject
import org.junit.Test

class ComponentTest {

    class TestComponent : AbstractComponent() {
        override fun destroy() {}
        override fun render(batch: SpriteBatch) {}
        override fun start() {}
        override fun update(deltaTime: Float) {}
    }

    @Test
    fun getNameTest(){
        val testObject = GameObject(bodyType = BodyDef.BodyType.StaticBody)
        testObject.addComponent(TestComponent())
        assert(testObject.getComponent("TestComponent") != null)
    }
}