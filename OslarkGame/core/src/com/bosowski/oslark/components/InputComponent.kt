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
import java.lang.Thread.sleep
import java.util.concurrent.ThreadLocalRandom


class InputComponent(private val speed: Float, var animator: AnimatorComponent? = null): Component(){

  private var dungeon: Dungeon? = null

  override fun awake() {
  }

  override fun start() {}

  override fun update(deltaTime: Float) {
    when {
      //move owner UP
      Gdx.input.isKeyPressed(Input.Keys.W) -> move(Direction.UP)
      //move owner DOWN
      Gdx.input.isKeyPressed(Input.Keys.S) -> move(Direction.DOWN)
      //move owner RIGHT
      Gdx.input.isKeyPressed(Input.Keys.D) -> move(Direction.RIGHT)
      //move owner LEFT
      Gdx.input.isKeyPressed(Input.Keys.A) -> move(Direction.LEFT)
      //if no key pressed, stop entity
      else -> owner.transform.body.linearVelocity = Vector2().also{ animator?.state = State.IDLE}
    }

    //other inputs --- >
    if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
      dungeon?.clear()
//      dungeon = Dungeon(Rectangle(-5f, -5f, 900f, 10f), 2, 7, 700, ThreadLocalRandom.current().nextLong())
      dungeon = Dungeon(Rectangle(-10f, -10f, 20f, 20f), 2, 7, 15, ThreadLocalRandom.current().nextLong())
      dungeon!!.create()
      sleep(250)
    }
    if(Gdx.input.isKeyPressed(Input.Keys.O)){
      GameRenderer.debugView = !GameRenderer.debugView
    }
  }

  fun move(direction: Direction){
    val velocity = Vector2(direction.value)
    velocity.x *= speed
    velocity.y *= speed
    owner.transform.body.linearVelocity = velocity

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