package com.bosowski.oslark.components

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.bosowski.oslark.World
import com.bosowski.oslark.enums.Direction
import com.bosowski.oslark.enums.State
import com.bosowski.oslark.generation.Dungeon
import com.bosowski.oslark.managers.GameRenderer
import com.sun.org.apache.xpath.internal.operations.Bool
import java.lang.Thread.sleep
import java.util.concurrent.ThreadLocalRandom


class InputComponent(private val speed: Float, var creatureComponent: CreatureComponent, var collider: ColliderComponent): AbstractComponent(){

  var healTimer = 0f

  override fun awake() {
  }

  override fun start() {}

  override fun update(deltaTime: Float) {
    healTimer += deltaTime

    if (creatureComponent.currentHealth <= 0) return

    if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
      creatureComponent.attack?.perform(deltaTime)
    }

    owner!!.transform.body?.linearVelocity = Vector2()
    //move owner!! UP
    if(Gdx.input.isKeyPressed(Input.Keys.W)) collider.move(Direction.UP.value, speed)
    //move owner!! DOWN
    if(Gdx.input.isKeyPressed(Input.Keys.S)) collider.move(Direction.DOWN.value, speed)
    //move owner!! RIGHT
    if(Gdx.input.isKeyPressed(Input.Keys.D)) collider.move(Direction.RIGHT.value, speed)
    //move owner!! LEFT
    if(Gdx.input.isKeyPressed(Input.Keys.A)) collider.move(Direction.LEFT.value, speed)

    //DIAGONAL movement
    if(Gdx.input.isKeyPressed(Input.Keys.A) && Gdx.input.isKeyPressed(Input.Keys.W)) collider.move(Vector2(Direction.LEFT.value).add(Direction.UP.value), speed)
    if(Gdx.input.isKeyPressed(Input.Keys.A) && Gdx.input.isKeyPressed(Input.Keys.S)) collider.move(Vector2(Direction.LEFT.value).add(Direction.DOWN.value), speed)
    if(Gdx.input.isKeyPressed(Input.Keys.D) && Gdx.input.isKeyPressed(Input.Keys.W)) collider.move(Vector2(Direction.RIGHT.value).add(Direction.UP.value), speed)
    if(Gdx.input.isKeyPressed(Input.Keys.D) && Gdx.input.isKeyPressed(Input.Keys.S)) collider.move(Vector2(Direction.RIGHT.value).add(Direction.DOWN.value), speed)

    //If the player is going diagonal, adjust the velocity.
    if(owner!!.transform.body?.linearVelocity!!.x != 0f && owner!!.transform.body?.linearVelocity!!.y != 0f){
      owner!!.transform.body?.linearVelocity = Vector2(owner!!.transform.body?.linearVelocity!!.x/1.5f, owner!!.transform.body?.linearVelocity!!.y/1.5f)
    }

    //other inputs --- >
    if(Gdx.input.isKeyPressed(Input.Keys.ENTER)){
      var successfullyCreated: Boolean
      do{
        World.dungeon?.clear()
        //      dungeon = Dungeon(Rectangle(-5f, -5f, 900f, 10f), 2, 7, 700, ThreadLocalRandom.current().nextLong())
        World.dungeon = Dungeon(Rectangle(-50f, -10f, 100f, 10f), 2, 7, 30)
        successfullyCreated = World.dungeon!!.create()
      }while(!successfullyCreated)
      sleep(250)
    }

    if(Gdx.input.isKeyPressed(Input.Keys.Q) && creatureComponent.currentEnergy >= 1 && healTimer >= creatureComponent.actionSpeed){
      creatureComponent.currentHealth += 1
      creatureComponent.currentEnergy -= 1
      healTimer = 0f
    }

    if(Gdx.input.isKeyPressed(Input.Keys.O)){
      GameRenderer.debugView = !GameRenderer.debugView
    }
  }

  override fun render(batch: SpriteBatch) {
    GameRenderer.camera.position.set(Vector3(owner!!.transform.position.x, owner!!.transform.position.y, 0f))
    GameRenderer.camera.update()
    GameRenderer.uiCamera.position.set(Vector3(owner!!.transform.position.x*35, owner!!.transform.position.y*60, 0f))
    GameRenderer.uiCamera.update()
  }

  override fun destroy() {}
}