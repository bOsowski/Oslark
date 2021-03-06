package com.bosowski.oslark.screens

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.bosowski.oslark.World
import com.bosowski.oslark.components.CreatureComponent
import com.bosowski.oslark.gameObjects.GameObject
import com.bosowski.oslark.managers.GameRenderer
import java.util.*

class GameScreen(game: Game, player: GameObject, seed: Long) : AbstractGameScreen(game) {

    lateinit var gameRenderer: GameRenderer
    private var paused: Boolean = false

    val scoreLabel = Label("You have died.", fieldSkins)


    init {
        World.instance!!.seed = seed
        World.instance!!.random = Random(seed)
        World.instance!!.player = player
        World.instance!!.createDungeon()
        World.instance!!.dungeon!!.gameScreen = this
        player.instantiate()
        setUpUI()
    }

    override fun render(deltaTime: Float) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        gameRenderer.render()



        // Do not update game world when paused.
        if (!paused) {
            World.instance!!.update(deltaTime)
        }
        if (Gdx.input.isKeyPressed(Input.Keys.BACKSPACE)) {
            game.screen = CharacterSelectionScreen(game)
        }

        if((World.instance!!.player.getComponent("CreatureComponent") as CreatureComponent).currentHealth <= 0f || World.instance!!.dungeon?.killedMonsters == World.instance!!.dungeon?.spawnedMonsters?.size){
            World.instance!!.rayHandler.setAmbientLight(1f)
            Gdx.input.inputProcessor = stage
            stage.act(deltaTime)
            stage.draw()
        }

    }

    override fun show() {
        this.gameRenderer = GameRenderer(World.instance!!)
    }

    override fun hide() {
        gameRenderer.dispose()
        Gdx.input.isCatchBackKey = false
    }

    override fun pause() {
        paused = true
    }

    override fun setUpUI() {
        val mainTable = Table()
        stage.addActor(mainTable)
        mainTable.setFillParent(true)

        val backButton = TextButton("Back", fieldSkins)
        val quitButton = TextButton("Quit", fieldSkins)
        mainTable.add(scoreLabel).padBottom(20f).row()
        mainTable.add(backButton).width(100f).padBottom(10f).row()
        mainTable.add(quitButton).width(100f)


        backButton.addListener(object : ClickListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, point: Int, button: Int): Boolean {
                println("back button pressed..")
                game.screen = CharacterSelectionScreen(game)
                return true
            }
        })

        quitButton.addListener(object : ClickListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, point: Int, button: Int): Boolean {
                println("Quitting..")
                System.exit(0)
                return true
            }
        })
    }

    override fun resume() {
        super.resume()
        paused = false
    }
}
