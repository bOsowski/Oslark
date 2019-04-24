package com.bosowski.oslark.components

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.physics.box2d.BodyDef
import com.bosowski.oslark.Assets
import com.bosowski.oslark.World
import com.bosowski.oslark.gameObjects.GameObject
import com.bosowski.oslark.gameObjects.prefabs.monsters.Monster
import com.bosowski.oslark.gameObjects.prefabs.utility.ActionableText
import com.bosowski.oslark.utils.Util

class CreatureComponent : AbstractComponent {

  var maxEnergy: Float
    set(value) {
      field = value
      currentEnergy = field
    }
  var currentEnergy: Float
    set(value) {
      field = when {
        value < 0 -> 0f

        value > maxEnergy -> maxEnergy
        else -> value
      }

      updateHUDComponent()
    }

  var maxHealth: Float
    set(value) {
      field = value
      currentHealth = field
    }

  var currentHealth: Float
    set(value) {
      field = when {
        value < 0 -> {
          die()
          0f
        }
        value > maxHealth -> maxHealth
        else -> value
      }

      updateHUDComponent()
    }

  var timer:Float = 0f

  var level: Int = 1
  set(value) {
    if(value > 0){
      field = value
    }
  }

  private val experienceForSecondLevel:Int = 25
  var experienceToNextLevel: Int = experienceForSecondLevel
  var currentExperience: Int = 0
    set(value) {
      val expText = ActionableText(owner!!.transform.position, "+${value-field}exp!", Color.WHITE)
      field = value
      expText.instantiate()
      if(currentExperience >= experienceToNextLevel){
        level++
        maxHealth += healthPerLevel
        maxEnergy += energyPerLevel
        damage = Pair(damage.first+damagePerLevel.first, damage.second + damagePerLevel.second)
        currentHealth = maxHealth

        val levelUpText = ActionableText(owner!!.transform.position, "Leveled up to $level!", Color.WHITE)
        levelUpText.instantiate()
        experienceToNextLevel = experienceForSecondLevel * Math.pow(level.toDouble(), 2.toDouble()).toInt()
      }
    }

  var damage: Pair<Float, Float>
  var attack: ActionInterface?
  var additionalBehaviours: ArrayList<ActionInterface> = ArrayList()
  var canAttack = true
  var actionSpeed: Float


  val damagePerLevel: Pair<Float, Float>
  val healthPerLevel: Float
  val energyPerLevel: Float
  var onDeathAction: ActionInterface? = null

  var healthRegenPerAction: Float
  var energyRegenPerAction: Float

  // min/max damage
  constructor(maxHealth: Float,
              maxEnergy: Float = 0f,
              level: Int = 1,
              damage: Pair<Float, Float> = Pair(1f, 1f),
              attack: ActionInterface? = null,
              actionSpeed:Float = 1f,
              damagePerLevel: Pair<Float, Float> = Pair(0f, 0f),
              healthPerLevel: Float = 0f,
              energyPerLevel: Float = 0f,
              energyRegenPerAction: Float = 0f,
              healthRegenPerAction: Float = 0f

  ) : super() {
    this.maxHealth = maxHealth
    this.level = level
    this.damage = damage
    this.attack = attack
    this.currentHealth = maxHealth
    this.actionSpeed = actionSpeed
    this.damagePerLevel = damagePerLevel
    this.healthPerLevel = healthPerLevel
    this.maxEnergy = maxEnergy
    this.currentEnergy = maxEnergy
    this.energyPerLevel = energyPerLevel
    this.healthRegenPerAction = healthRegenPerAction
    this.energyRegenPerAction = energyRegenPerAction
  }

  override fun start() {
  }

  override fun update(deltaTime: Float) {
    //perform an attack each second if the owner is a monster.
    timer += deltaTime
    if(timer >= actionSpeed){
      canAttack = true

      //regenerate resources
      currentHealth += healthRegenPerAction
      currentEnergy += energyRegenPerAction

      timer = 0f
    }

    additionalBehaviours.forEach {
      it.perform(deltaTime)
    }

    if(owner is Monster && canAttack){
      attack?.perform(deltaTime)
    }
  }

  override fun render(batch: SpriteBatch) {
  }

  override fun destroy() {

  }

  fun die(){
    val death = GameObject(position = owner!!.transform.position, layer = 1, name = "death", bodyType = BodyDef.BodyType.KinematicBody)
    val animationComponent = AnimationComponent(Assets.animations["death"]!!)
    death.addComponent(animationComponent)
    death.transform.body?.setLinearVelocity(0f,2f)
    var timer = 0f
    val actionComponent = ActionComponent(ActionInterface { deltaTime ->
      timer += deltaTime
      if(timer >= 0.5f){
        death.destroy()
      }
    })
    death.addComponent(actionComponent)
    death.instantiate()
    owner!!.destroy()
    onDeathAction?.perform(0f)
  }

  fun getDamage(): Float{
    return Util.randomFloat(World.instance!!.random, damage.first, damage.second)
  }

  fun updateHUDComponent(){
    if(owner != null && owner!!.getComponent("HUDComponent") != null){
      val hudComponent = owner!!.getComponent("HUDComponent") as HUDComponent
      hudComponent.updateHud()
    }
  }
}