package com.bosowski.oslark.components

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.bosowski.oslark.Assets
import com.bosowski.oslark.gameObjects.GameObject
import com.bosowski.oslark.utils.Constants
import kotlin.math.roundToInt

class HUDComponent(var creatureComponent: CreatureComponent): AbstractComponent(){

  var parentObjects = ArrayList<GameObject>()
  var healthBar = ArrayList<TextureComponent>()

  override fun awake() {
    (1..creatureComponent.maxHealth.toInt()).forEach{ it ->
      parentObjects.add(GameObject(layer = 10, bodyType = BodyDef.BodyType.StaticBody))
      parentObjects.last().instantiate()
      healthBar.add(TextureComponent(Assets.textures["uiHeart2"]!!))
      parentObjects.last().addComponent(healthBar.last())
    }
  }

  override fun start() {

  }

  override fun update(deltaTime: Float) {
    parentObjects.forEachIndexed {index, it ->
      it.transform.body.setTransform(Vector2(- Constants.VIEWPORT_WIDTH/2 + index + 1 + owner.transform.position.x, Constants.VIEWPORT_HEIGHT/2 -1.5f + owner.transform.position.y), 0f)
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
  }

  override fun render(batch: SpriteBatch) {

  }

  override fun destroy() {
    parentObjects.forEach {
      it.destroy()
    }
  }
}