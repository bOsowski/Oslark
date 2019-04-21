package com.bosowski.oslark.components

import box2dLight.PointLight
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.bosowski.oslark.World
import com.bosowski.oslark.enums.Direction
import com.bosowski.oslark.enums.State
import java.util.HashMap


class LightComponent() : AbstractComponent(){

  val playerLight = PointLight(World.rayHandler, 500, Color(0f,0f,0f,1f), 15f, 0f, 0f)

  override fun destroy() {
    playerLight.dispose()
  }

  override fun awake() {}

  override fun start() {
    playerLight.attachToBody(owner?.transform!!.body, 0f, 0f)
    playerLight.setSoftnessLength(5f)
    playerLight.ignoreAttachedBody = true
  }

  override fun update(deltaTime: Float) {}

  override fun render(batch: SpriteBatch){

  }
}