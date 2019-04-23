package com.bosowski.oslark.managers

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.badlogic.gdx.utils.Disposable
import com.bosowski.oslark.World
import com.bosowski.oslark.utils.Constants

class GameRenderer
(private var world: World) : Disposable {
  private var batch: SpriteBatch = SpriteBatch()
  private val debugRenderer: Box2DDebugRenderer
  private val shapeRanderer: ShapeRenderer


  init {
    camera.position.set(Vector3.Zero)
    camera.update()
    uiCamera.position.set(Vector3.Zero)
    uiCamera.update()
    debugRenderer = Box2DDebugRenderer()
    shapeRanderer = ShapeRenderer()
  }

  fun render() {
    batch.projectionMatrix = camera.combined
    world.rayHandler.setCombinedMatrix(camera)
    batch.begin()
    Gdx.graphics.setTitle("FPS: ${Gdx.graphics.framesPerSecond}")
    if(!debugView){
      world.render(batch)
    }
    else{
      shapeRanderer.projectionMatrix = batch.projectionMatrix
      debugRenderer.render(world.physicsWorld, batch.projectionMatrix)
      shapeRanderer.begin(ShapeRenderer.ShapeType.Line)
      world.rays.values.forEach { u ->
        if(u.first != null && u.second != null){
          shapeRanderer.line(u.first, u.second)
        }
      }
      shapeRanderer.end()
    }

    batch.end()

    world.rayHandler.updateAndRender()

    batch.begin()

    if(!debugView){
      world.renderUI(batch)
    }

    batch.end()
  }

  override fun dispose() {
    batch.dispose()
  }

  companion object {
    var camera: OrthographicCamera  = OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT)
    var uiCamera: OrthographicCamera = OrthographicCamera(Constants.VIEWPORT_WIDTH_TEXT, Constants.VIEWPORT_HEIGHT_TEXT)
    var debugView = false
  }
}
