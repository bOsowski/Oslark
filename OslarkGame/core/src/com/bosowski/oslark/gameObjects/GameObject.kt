package com.bosowski.oslark.gameObjects

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.bosowski.oslark.World
import com.bosowski.oslark.components.AbstractComponent
import com.bosowski.oslark.components.TransformComoponent

open class GameObject(position: Vector2 = Vector2(), val layer: Short = 0, var name: String = "", bodyType: BodyDef.BodyType){
  val TAG: String by lazy { this.javaClass.name }

  val transform: TransformComoponent = TransformComoponent(position, bodyType)
  private val components: LinkedHashMap<String, AbstractComponent> = linkedMapOf(transform.name to transform)

  init{
    transform.owner = this
    transform.layer = layer
  }

  fun instantiate(){
    World.instance!!.instantiate(this)
    components.values.forEach { it.start() }
  }

  fun destroy(){
    World.instance!!.destroy(this)
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
    components.remove(transform.name)
    components[transform.name] = transform
  }

  fun removeComponent(name: String){
      components[name]?.destroy()
      components.remove(name)
  }
}