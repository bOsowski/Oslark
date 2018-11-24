package com.bosowski.oslark.components

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.Gdx
import com.bosowski.oslark.gameObjects.GameObject

open class AnimationComponent(owner: GameObject, var animation: Animation<TextureRegion>): TextureComponent(owner, animation.getKeyFrame(0f)){

  var stateTime = 0f

  override fun awake() {}

  override fun start() {}

  override fun update(deltaTime: Float) {}

  override fun render(batch: SpriteBatch) {
    animation.playMode = Animation.PlayMode.LOOP
      stateTime += Gdx.graphics.deltaTime
      texture = animation.getKeyFrame(stateTime) as TextureRegion

    batch.draw(
        texture.texture,
        owner.transform.position.x - origin.x,
        owner.transform.position.y - origin.y, origin.x, origin.y,
        dimension.x, dimension.y, scale.x, scale.y, rotation,
        texture.regionX,
        texture.regionY,
        texture.regionWidth,
        texture.regionHeight,
        false, false
    )
  }
}