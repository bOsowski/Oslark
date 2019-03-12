package com.bosowski.oslark.components

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.bosowski.oslark.gameObjects.prefabs.Monster

class CreatureComponent(
  var maxHealth: Float,
  var level: Int = 1,
  var damage:Pair<Float, Float> = Pair(1f, 1f), // min/max damage
  var attack: ActionInterface
): AbstractComponent() {

  var currentHealth: Float = maxHealth
  set(value) {
    field = when {
      value < 0 -> 0f
      value > maxHealth -> maxHealth
      else -> value
    }
  }

  var timer:Float = 0f

  override fun awake() {
  }

  override fun start() {
  }

  override fun update(deltaTime: Float) {
    if(owner is Monster){
      timer += deltaTime
      if(timer >= 1){
        attack.perform(deltaTime)
        timer = 0f
      }
    }
  }

  override fun render(batch: SpriteBatch) {
  }

  override fun destroy() {
  }
}