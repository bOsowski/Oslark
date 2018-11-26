package com.bosowski.oslark.managers

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.badlogic.gdx.utils.Disposable
import com.bosowski.oslark.World
import com.bosowski.oslark.utils.Constants


class GameRenderer
(private var gameManager: GameManager) : Disposable {
  private var batch: SpriteBatch = SpriteBatch()
  private val debugRenderer: Box2DDebugRenderer

  init {
    camera.position.set(Vector3.Zero)
    camera.update()
    debugRenderer = Box2DDebugRenderer()
  }

  fun render() {
    batch.projectionMatrix = camera.combined
    batch.begin()
    Gdx.graphics.setTitle("FPS: ${Gdx.graphics.framesPerSecond}")
    World.rayHandler.setCombinedMatrix(camera)
    if(!debugView){
      gameManager.render(batch)
    }
    else{
      debugRenderer.render(World.physicsWorld, batch.projectionMatrix)
    }
    batch.end()
  }

  override fun dispose() {
    batch.dispose()
  }

  companion object {
    var camera: OrthographicCamera  = OrthographicCamera(Constants.VIEWPORT_WIDTH * 2, Constants.VIEWPORT_HEIGHT * 2)
    var currentScreen: Screen? = null
    var debugView = false
  }
}
