package com.bosowski.oslark.components

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.bosowski.oslark.enums.Direction
import com.bosowski.oslark.enums.State
import java.util.HashMap


class AnimatorComponent(private val animations: HashMap<State, Animation<TextureRegion>>) : AnimationComponent(){
  var state = State.IDLE
  set(value) {
    if(field != value){
      stateTime = 0f
      field = value
      animation = animations[field]!!
    }
  }

  init{
    animation = animations[state]!!
  }

  override fun start() {}

  override fun update(deltaTime: Float) {}

  override fun render(batch: SpriteBatch) {

    state = if(owner!!.transform.body?.linearVelocity != Vector2.Zero){
      State.MOVE
    } else{
      State.IDLE
    }

    //flip the sprite depending on the direction.
    if(owner!!.transform.body?.linearVelocity?.x != 0f){
      var direction = owner!!.transform.body?.linearVelocity?.x

      direction = if(direction != null && direction < 0) -1f
      else 1f

      scale = (Vector2(Math.abs(scale.x) * direction, scale.y))
    }
    super.render(batch)
  }
}