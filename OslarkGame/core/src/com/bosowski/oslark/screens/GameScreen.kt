package com.bosowski.oslark.screens

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.GL20
import com.bosowski.oslark.World
import com.bosowski.oslark.gameObjects.GameObject
import com.bosowski.oslark.managers.GameManager
import com.bosowski.oslark.managers.GameRenderer
import java.util.*

class GameScreen(game: Game, player: GameObject, seed: Long) : AbstractGameScreen(game) {

    lateinit var gameManager: GameManager
    lateinit var gameRenderer: GameRenderer
    private var paused: Boolean = false

    init {
        World.random = Random(seed)
        World.player = player
        World.createDungeon()
        setUpUI()
        player.instantiate()
    }

    override fun render(deltaTime: Float) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        gameRenderer.render()
        // Do not update game world when paused.
        if (!paused) {
            gameManager.update(deltaTime)
        }
        if (Gdx.input.isKeyPressed(Input.Keys.BACKSPACE)) {
            game.screen = CharacterSelectionScreen(game)
        }
    }

    override fun show() {
        this.gameManager = GameManager()
        this.gameRenderer = GameRenderer(gameManager)
    }

    override fun hide() {
        gameRenderer.dispose()
        Gdx.input.isCatchBackKey = false
    }

    override fun pause() {
        paused = true
    }

    override fun setUpUI() {
        //todo: create UI here.
    }

    override fun resume() {
        super.resume()
        paused = false
    }
}
