package com.bosowski.oslark.gameObjects.prefabs.utility

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.bosowski.oslark.Assets
import com.bosowski.oslark.components.AbstractComponent
import com.bosowski.oslark.gameObjects.GameObject
import com.bosowski.oslark.managers.GameRenderer


class ActionableText(position: Vector2, text: String, color: Color): GameObject(position = position, layer = 10, bodyType = BodyDef.BodyType.KinematicBody) {

  init {
    val textComponent = TextComponent(text, color)
    addComponent(textComponent)
  }

  class TextComponent(val text: String, val color: Color): AbstractComponent(){
    var timer = 0f

    override fun awake() {
      owner.transform.body?.setLinearVelocity(0f, 3f)
    }

    override fun start() {}

    override fun update(deltaTime: Float) {
      timer += deltaTime

      if(timer > 0.5f){
        owner.destroy()
      }
    }

    override fun render(batch: SpriteBatch) {
      batch.projectionMatrix = GameRenderer.uiCamera.combined
      Assets.font.color = color
      Assets.font.draw(batch, text, (owner.transform.position.x)*35, (owner.transform.position.y+0.6f)*60)
      batch.projectionMatrix = GameRenderer.camera.combined
    }

    override fun destroy() {
    }

  }
}