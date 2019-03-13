package com.bosowski.oslark.gameObjects.prefabs.utility

import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.bosowski.oslark.components.AbstractComponent
import com.bosowski.oslark.gameObjects.GameObject
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.bosowski.oslark.managers.GameRenderer


class ActionableText(position: Vector2, text: String, color: Color): GameObject(position = position, layer = 10) {

  init {
    val textComponent = TextComponent(text, color)
    addComponent(textComponent)
  }

  class TextComponent(val text: String, val color: Color): AbstractComponent(){
    var timer = 0f
    lateinit var font: BitmapFont

    override fun awake() {
      owner.transform.body.setLinearVelocity(0f, 3f)
    }

    override fun start() {
      val generator = FreeTypeFontGenerator(Gdx.files.internal("OpenSans-Regular.ttf"))
      val parameter = FreeTypeFontGenerator.FreeTypeFontParameter()
      parameter.color = color
      parameter.borderWidth = 1f
      parameter.borderColor = Color.BLACK
      font = generator.generateFont(parameter)
    }

    override fun update(deltaTime: Float) {
      timer += deltaTime

      if(timer > 0.5f){
        owner.destroy()
      }
    }

    override fun render(batch: SpriteBatch) {
      batch.projectionMatrix = GameRenderer.uiCamera.combined
      font.draw(batch, text, owner.transform.body.position.x*35, (owner.transform.body.position.y+0.3f)*60)
      batch.projectionMatrix = GameRenderer.camera.combined
    }

    override fun destroy() {}

  }
}