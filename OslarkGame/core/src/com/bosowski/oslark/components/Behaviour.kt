package com.bosowski.oslark.components

import com.badlogic.gdx.graphics.g2d.SpriteBatch

interface Behaviour{

  /**
   * This method is called during addition of components
   * to game objects and also at the start of the game.
   */
  fun awake()

  /**
   * This method is called during the start of the game,
   * after the awake method.
   */
  fun start()

  /**
   * This method is called each frame
   */
  fun update(deltaTime: Float)

  /**
   * This method is called each frame
   */
  fun render(batch: SpriteBatch)

  /**
   * This method can be called to
   * clean up anything that the component
   * might have created.
   */
  fun destroy()
}