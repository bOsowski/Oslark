package com.bosowski.oslark.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.bosowski.oslark.main.GameManager;
import com.bosowski.oslark.main.GameRenderer;
import com.bosowski.oslark.screens.AbstractGameScreen;

public class GameScreen extends AbstractGameScreen {

    private GameManager gameManager;
    private GameRenderer gameRenderer;
    private boolean paused;

    public GameScreen(Game game) {
        super(game);
    }

    @Override
    public void render(float deltaTime) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameRenderer.render();
        // Do not update game world when paused.
        if (!paused) {
            gameManager.update(deltaTime);
        }
    }

    @Override
    public void resize(int width, int height) {
//        GameRenderer.camera.viewportWidth = width%20;
//        GameRenderer.camera.viewportHeight = height%20;
    }

    @Override
    public void show() {
        this.gameManager = new GameManager();
        this.gameRenderer = new GameRenderer(gameManager);
    }

    @Override
    public void hide() {
        gameRenderer.dispose();
        Gdx.input.setCatchBackKey(false);
    }

    @Override
    public void pause() {
        paused = true;
    }

    @Override
    public void resume(){
        super.resume();
        paused = false;
    }
}
