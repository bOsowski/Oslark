package com.bosowski.oslark.main

import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.Disposable
import com.bosowski.oslark.utils.Constants

class GameRenderer
(private var gameManager: GameManager) : Disposable {
    private var batch: SpriteBatch = SpriteBatch()

    init {
        camera.position.set(Vector3.Zero)
        camera.update()
    }

    fun render() {
        batch.projectionMatrix = camera.combined
        batch.begin()
        gameManager.render(batch)
        batch.end()
    }

    override fun dispose() {
        batch.dispose()
    }

    companion object {
        var camera: OrthographicCamera  = OrthographicCamera(Constants.VIEWPORT_WIDTH * 2, Constants.VIEWPORT_HEIGHT * 2)
        var currentScreen: Screen? = null
    }
}
