package com.bosowski.oslark.components

import com.bosowski.oslark.gameObjects.GameObject

abstract class AbstractComponent: BehaviourInterface{

  lateinit var owner: GameObject
  /**
   * For example, take a class Physics, which inherits
   * from this class. This property will return the
   * "Physics".
   * The restriction here is that components classes
   * may not be created as inner classes.
   * @return The name of the inheriting component.
   */
  val name: String by lazy{
    assert(!this.javaClass.simpleName.contains("$"))  //this failing indicates that the component class has been created as an inner class.
    this.javaClass.simpleName
  }

  var active = true
}