package com.bosowski.oslark.components

abstract class Behaviour{

  abstract fun awake()
  abstract fun start()
  abstract fun update(deltaTime: Float)
}