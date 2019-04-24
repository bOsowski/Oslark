package com.bosowski.oslark.components

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2

open class TextureComponent(var texture: TextureRegion): AbstractComponent(){

  var scale = Vector2(1f, 1f)
  var dimension = Vector2(1f, 1f)
  var rotation = 0f
  var origin = Vector2(dimension.x / 2, 0f)
  var color = Color.WHITE

  override fun start() {}

  override fun update(deltaTime: Float) {}

  override fun render(batch: SpriteBatch) {
    batch.color = color

    batch.draw(
        texture.texture,
        owner!!.transform.position.x - origin.x,
        owner!!.transform.position.y - origin.y, origin.x, origin.y,
        dimension.x, dimension.y, scale.x, scale.y, rotation,
        texture.regionX,
        texture.regionY,
        texture.regionWidth,
        texture.regionHeight,
        false, false
    )
    batch.color = Color.WHITE
  }

  override fun destroy() {}
}