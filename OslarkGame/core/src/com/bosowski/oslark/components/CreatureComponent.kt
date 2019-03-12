package com.bosowski.oslark.components

import com.badlogic.gdx.graphics.g2d.NinePatch
import com.badlogic.gdx.graphics.g2d.SpriteBatch

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

  override fun awake() {
  }

  override fun start() {
  }

  override fun update(deltaTime: Float) {
  }

  override fun render(batch: SpriteBatch) {
  }

  override fun destroy() {
  }
}