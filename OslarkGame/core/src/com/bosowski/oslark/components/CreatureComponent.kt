package com.bosowski.oslark.components

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.bosowski.oslark.Assets
import com.bosowski.oslark.World
import com.bosowski.oslark.gameObjects.GameObject
import com.bosowski.oslark.gameObjects.prefabs.monsters.Monster
import com.bosowski.oslark.utils.Util

class CreatureComponent(
  var maxHealth: Float,
  var level: Int = 1,
  var damage:Pair<Float, Float> = Pair(1f, 1f), // min/max damage
  var attack: ActionInterface? = null
): AbstractComponent() {

  var currentHealth: Float = maxHealth
    set(value) {
      field = when {
        value < 0 -> {
          die()
          0f
        }
        value > maxHealth -> maxHealth
        else -> value
      }
    }

  var timer:Float = 0f

  override fun awake() {
  }

  override fun start() {
  }

  override fun update(deltaTime: Float) {
    //perform an attack each second if the owner is a monster.
    if(owner is Monster){
      timer += deltaTime
      if(timer >= 1){
        attack?.perform(deltaTime)
        timer = 0f
      }
    }
  }

  override fun render(batch: SpriteBatch) {
  }

  override fun destroy() {

  }

  fun die(){
    val death = GameObject(position = owner.transform.body.position, layer = owner.layer, name = "death")
    val animationComponent = AnimationComponent(Assets.animations["death"]!!)
    death.addComponent(animationComponent)
    death.transform.body.setLinearVelocity(0f,2f)
    var timer = 0f
    val actionComponent = ActionComponent(ActionInterface { deltaTime ->
      timer += deltaTime
      if(timer >= 0.5f){
        death.destroy()
      }
    })
    death.addComponent(actionComponent)
    death.instantiate()
    owner.destroy()
  }

  fun getDamage(): Float{
    return Util.randomFloat(World.random, damage.first, damage.second)
  }
}