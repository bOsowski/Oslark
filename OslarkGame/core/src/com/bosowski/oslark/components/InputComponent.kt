package com.bosowski.oslark.components

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.bosowski.oslark.enums.Direction
import com.bosowski.oslark.gameObjects.GameObject

class InputComponent(owner: GameObject, val colliderComponent: ColliderComponent): Component(owner){

  override fun awake() {}

  override fun start() {}

  override fun update(deltaTime: Float) {
    //move owner UP
    if (Gdx.input.isKeyPressed(Input.Keys.W)) {

    }
    //move owner DOWN
    if (Gdx.input.isKeyPressed(Input.Keys.S)) {

    }
    //move owner RIGHT
    if (Gdx.input.isKeyPressed(Input.Keys.D)) {

    }
    //move owner LEFT
    if (Gdx.input.isKeyPressed(Input.Keys.A)) {

    }



    //other inputs --- >
    if(Gdx.input.isKeyPressed(Input.Keys.O)){

    }
  }

  override fun render(batch: SpriteBatch) {}

  override fun destroy() {}
}