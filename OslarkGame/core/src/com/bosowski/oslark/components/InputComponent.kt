package com.bosowski.oslark.components

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.bosowski.oslark.enums.Direction
import com.bosowski.oslark.enums.State
import com.bosowski.oslark.generation.Dungeon
import com.bosowski.oslark.managers.GameRenderer
import com.sun.org.apache.xpath.internal.operations.Bool
import java.lang.Thread.sleep
import java.util.concurrent.ThreadLocalRandom


class InputComponent(private val speed: Float, var animator: AnimatorComponent? = null): AbstractComponent(){

  private var dungeon: Dungeon? = null

  override fun awake() {
  }

  override fun start() {}

  override fun update(deltaTime: Float) {
    owner.transform.body.linearVelocity = Vector2()
    //move owner UP
    if(Gdx.input.isKeyPressed(Input.Keys.W)) move(Direction.UP, deltaTime)
    //move owner DOWN
    if(Gdx.input.isKeyPressed(Input.Keys.S)) move(Direction.DOWN, deltaTime)
    //move owner RIGHT
    if(Gdx.input.isKeyPressed(Input.Keys.D)) move(Direction.RIGHT, deltaTime)
    //move owner LEFT
    if(Gdx.input.isKeyPressed(Input.Keys.A)) move(Direction.LEFT, deltaTime)

    //If the player is going diagonal, adjust the velocity.
    if(owner.transform.body.linearVelocity.x != 0f && owner.transform.body.linearVelocity.y != 0f){
      println("Adjusting velocity. Before = ${owner.transform.body.linearVelocity}")
      owner.transform.body.linearVelocity = Vector2(owner.transform.body.linearVelocity.x/1.5f, owner.transform.body.linearVelocity.y/1.5f)
      println("Adjusting velocity. After = ${owner.transform.body.linearVelocity}")
    }

      if(owner.transform.body.linearVelocity == Vector2()){
        animator?.state = State.IDLE
      }
    else{
        animator?.state = State.MOVE
      }

    //other inputs --- >
    if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
      var successfullyCreated: Boolean
      do{
        dungeon?.clear()
        //      dungeon = Dungeon(Rectangle(-5f, -5f, 900f, 10f), 2, 7, 700, ThreadLocalRandom.current().nextLong())
        dungeon = Dungeon(Rectangle(-10f, -10f, 20f, 20f), 2, 7, 15, ThreadLocalRandom.current().nextLong())
        successfullyCreated = dungeon!!.create()
      }while(!successfullyCreated)
      sleep(250)
    }
    if(Gdx.input.isKeyPressed(Input.Keys.O)){
      GameRenderer.debugView = !GameRenderer.debugView
    }
  }

  fun move(direction: Direction, deltaTime: Float){
    val velocity = Vector2(direction.value)
    velocity.x *= speed*deltaTime
    velocity.y *= speed*deltaTime
    owner.transform.body.linearVelocity = owner.transform.body.linearVelocity.add(velocity)

    if(animator != null){
      animator!!.state = State.MOVE
      if(owner.transform.direction != direction && (direction == Direction.LEFT || direction == Direction.RIGHT)){
        animator!!.scale = (Vector2(Math.abs(animator!!.scale.x) * direction.value.x, animator!!.scale.y))
      }
    }
    owner.transform.direction = direction

    GameRenderer.camera.position.set(Vector3(owner.transform.position.x, owner.transform.position.y, 0f))
    GameRenderer.camera.update()
  }

  override fun render(batch: SpriteBatch) {}

  override fun destroy() {}
}