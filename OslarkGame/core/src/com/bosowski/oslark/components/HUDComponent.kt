package com.bosowski.oslark.components

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.bosowski.oslark.Assets
import com.bosowski.oslark.utils.Constants
import kotlin.math.roundToInt

class HUDComponent(var creatureComponent: CreatureComponent): AbstractComponent(){

  var healthBar = ArrayList<TextureComponent>()
  var energyBar = ArrayList<TextureComponent>()

  override fun awake() {
    (1..creatureComponent.maxHealth.toInt()).forEach{
      healthBar.add(TextureComponent(Assets.textures["uiHeart2"]!!))
    }
    (1..creatureComponent.maxEnergy.toInt()).forEach{
      energyBar.add(TextureComponent(Assets.textures["uiEnergy1"]!!))
    }
  }

  override fun start() {

  }

  override fun update(deltaTime: Float) {
    (0 until creatureComponent.maxHealth.toInt()).forEach{
      if(it <= creatureComponent.currentHealth-1.0){
        healthBar[it].texture = Assets.textures["uiHeart2"]!!
      }
      else if(it <= creatureComponent.currentHealth.roundToInt()-1){
        healthBar[it].texture = Assets.textures["uiHeart1"]!!
      }
      else{
        healthBar[it].texture = Assets.textures["uiHeart0"]!!
      }
    }

    (0 until creatureComponent.maxEnergy.toInt()).forEach{
      if(it <= creatureComponent.currentEnergy-1.0){
        energyBar[it].texture = Assets.textures["uiEnergy1"]!!
      }
      else{
        energyBar[it].texture = Assets.textures["uiHeart0"]!!
      }
    }
  }

  override fun render(batch: SpriteBatch)
  {
    healthBar.forEachIndexed {index, it ->
      batch.draw(
        it.texture.texture,
        - Constants.VIEWPORT_WIDTH/2 + index + 1 + owner.transform.position.x,
        Constants.VIEWPORT_HEIGHT/2 -1.5f + owner.transform.position.y, 0f, 0f,
        1f, 1f, 1f, 1f, 0f,
        it.texture.regionX,
        it.texture.regionY,
        it.texture.regionWidth,
        it.texture.regionHeight,
        false, false
      )
    }

    energyBar.forEachIndexed {index, it ->
      batch.draw(
        it.texture.texture,
        - Constants.VIEWPORT_WIDTH/2 + index + 1 + owner.transform.position.x,
        Constants.VIEWPORT_HEIGHT/2 -2.5f + owner.transform.position.y, 0f, 0f,
        1f, 1f, 1f, 1f, 0f,
        it.texture.regionX,
        it.texture.regionY,
        it.texture.regionWidth,
        it.texture.regionHeight,
        false, false
      )
    }
  }

  override fun destroy() {
  }
}