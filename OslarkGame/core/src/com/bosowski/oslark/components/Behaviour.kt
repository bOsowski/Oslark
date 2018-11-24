package com.bosowski.oslark.components

import com.badlogic.gdx.graphics.g2d.SpriteBatch

interface Behaviour{

  fun awake()
  fun start()
  fun update(deltaTime: Float)
  fun render(batch: SpriteBatch)
  fun destroy()
}