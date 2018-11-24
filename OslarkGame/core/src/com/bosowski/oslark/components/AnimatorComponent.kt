package com.bosowski.oslark.components

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.bosowski.oslark.enums.State
import com.bosowski.oslark.gameObjects.GameObject
import com.bosowski.oslark.main.Assets
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

  override fun awake() { animation = animations[state]!! }

  override fun start() {}

  override fun update(deltaTime: Float) {}
}