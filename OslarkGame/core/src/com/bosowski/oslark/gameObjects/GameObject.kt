package com.bosowski.oslark.gameObjects

import com.badlogic.gdx.math.Vector2
import com.bosowski.oslark.World
import com.bosowski.oslark.components.Component
import com.bosowski.oslark.components.TransformComoponent

class GameObject(position: Vector2 = Vector2(), var layer: Short = 0){
  val TAG: String by lazy { this.javaClass.name }

  var name = ""
  val transform: TransformComoponent = TransformComoponent(this, position)
  private val components: MutableMap<String, Component> = mutableMapOf(transform.name to transform)

  init{
    transform.layer = layer
    transform.owner = this
  }

  fun instantiate(){
    components.values.forEach { it.awake() }
    World.gameObjects.add(this)
    components.values.forEach { it.start() }
  }

  fun destroy(){
    components.values.forEach { it.destroy() }
    World.gameObjects.remove(this)
  }

  fun getComponent(name: String): Component?{
    return components[name]
  }

  fun getComponents(): List<Component>{
    return components.values.toList()
  }

  fun addComponent(component: Component){
    component.owner = this
    components[component.name] = component
  }

  fun removeComponent(name: String){
    if(name != transform.name){
      components.remove(name)
    }
  }
}