package com.bosowski.oslark.components

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.bosowski.oslark.Assets
import com.bosowski.oslark.World
import com.bosowski.oslark.managers.GameRenderer
import com.bosowski.oslark.utils.Constants
import kotlin.math.roundToInt

class HUDComponent(var creatureComponent: CreatureComponent): AbstractComponent(){

  var healthBar = ArrayList<TextureComponent>()
  var energyBar = ArrayList<TextureComponent>()

  var score: Long = 0
  private var timer = 0f

  override fun awake() {

  }

  override fun start() {

  }

  override fun update(deltaTime: Float) {
    timer += deltaTime
    if(timer >= 0.5f && World.dungeon != null && !World.dungeon!!.levelCompleted){
      score -= 1
      timer = 0f
    }
  }

  fun updateHud(){
    healthBar.clear()
    energyBar.clear()
    (1..creatureComponent.maxHealth.toInt()).forEach{
      healthBar.add(TextureComponent(Assets.textures["uiHeart2"]!!))
    }
    (1..creatureComponent.maxEnergy.toInt()).forEach{
      energyBar.add(TextureComponent(Assets.textures["uiEnergy1"]!!))
    }

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
        energyBar[it].texture = Assets.textures["uiEnergy0"]!!
      }
    }
  }

  override fun render(batch: SpriteBatch)
  {
    healthBar.forEachIndexed {index, it ->
      batch.draw(
        it.texture.texture,
        - Constants.VIEWPORT_WIDTH/2 + index + 1 + owner!!.transform.position.x,
        Constants.VIEWPORT_HEIGHT/2 -2.5f + owner!!.transform.position.y, 0f, 0f,
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
        - Constants.VIEWPORT_WIDTH/2 + index + 1 + owner!!.transform.position.x,
        Constants.VIEWPORT_HEIGHT/2 -3.5f + owner!!.transform.position.y, 0f, 0f,
        1f, 1f, 1f, 1f, 0f,
        it.texture.regionX,
        it.texture.regionY,
        it.texture.regionWidth,
        it.texture.regionHeight,
        false, false
      )
    }

    batch.projectionMatrix = GameRenderer.uiCamera.combined
    Assets.font.color = Color.WHITE
    Assets.font.draw(batch, "Score: $score",
      - Constants.VIEWPORT_WIDTH_TEXT/2 + 40f + owner!!.transform.position.x*35,
      Constants.VIEWPORT_HEIGHT_TEXT/2 -40 + owner!!.transform.position.y*60
    )

    Assets.font.draw(batch, "Killed ${World.dungeon?.killedMonsters}/${World.dungeon?.spawnedMonsters?.size} monsters",
      - Constants.VIEWPORT_WIDTH_TEXT/2 + 40f + owner!!.transform.position.x*35,
      Constants.VIEWPORT_HEIGHT_TEXT/2 -60f + owner!!.transform.position.y*60
    )
    batch.projectionMatrix = GameRenderer.camera.combined
  }

  override fun destroy() {
  }
}