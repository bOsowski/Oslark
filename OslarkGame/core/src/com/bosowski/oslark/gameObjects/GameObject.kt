package com.bosowski.oslark.gameObjects

import com.badlogic.gdx.math.Vector2
import com.bosowski.oslark.World
import com.bosowski.oslark.components.AbstractComponent
import com.bosowski.oslark.components.TransformComoponent

open class GameObject(position: Vector2 = Vector2(), val layer: Short = 0, var name: String = ""){
  val TAG: String by lazy { this.javaClass.name }

  val transform: TransformComoponent = TransformComoponent(position)
  private val components: MutableMap<String, AbstractComponent> = mutableMapOf(transform.name to transform)

  init{
    transform.owner = this
    transform.layer = layer
  }

  fun instantiate(){
    components.values.forEach { it.awake() }
    World.instantiate(this)
    components.values.forEach { it.start() }
  }

  fun destroy(){
    components.values.forEach { it.destroy() }
    World.destroy(this)
  }

  fun getComponent(name: String): AbstractComponent?{
    return components[name]
  }

  fun getComponents(): List<AbstractComponent>{
    return components.values.toList()
  }

  fun addComponent(component: AbstractComponent){
    component.owner = this
    components[component.name] = component
    component.awake()
  }

  fun removeComponent(name: String){
    if(name != transform.name){
      components.remove(name)
    }
  }
}