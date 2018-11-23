package com.bosowski.oslark.gameObjects

import com.badlogic.gdx.math.Vector2
import com.bosowski.oslark.components.Component
import com.bosowski.oslark.components.Transform

open class GameObject {

    var name = ""
    val transform = Transform()
    private val components: MutableMap<String, Component> = mutableMapOf(transform.name to transform)

    fun GameObject(position: Vector2 = Vector2(), layer: Float = 0f){
        transform.position = position
        transform.layer = layer
        transform.owner = this
    }

    fun getComponent(name: String): Component?{
        return components[name]
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

    val TAG: String by lazy {
        this.javaClass.name
    }
}