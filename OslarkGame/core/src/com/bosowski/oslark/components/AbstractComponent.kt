package com.bosowski.oslark.components

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.bosowski.oslark.gameObjects.GameObject

abstract class AbstractComponent{

  var owner: GameObject? = null

  /**
   * For example, take a class TransformComponent, which inherits
   * from this class. This property will return the
   * "TransformComponent".
   * The restriction here is that components classes
   * may not be created as inner classes.
   * @return The name of the inheriting component.
   */
  val name: String by lazy{
    assert(!this.javaClass.simpleName.contains("$"))  //this failing indicates that the component class has been created as an inner class.
    this.javaClass.simpleName
  }

  var active = true

  /**
   * This method is called during the start of the game,
   * after the awake method.
   */
  abstract fun start()

  /**
   * This method is called each frame
   */
  abstract fun update(deltaTime: Float)

  /**
   * This method is called each frame
   */
  abstract fun render(batch: SpriteBatch)

  /**
   * This method can be called to
   * clean up anything that the component
   * might have created.
   */
  abstract fun destroy()
}