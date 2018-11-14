package com.bosowski.oslark.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.bosowski.oslark.main.GameManager;
import com.bosowski.oslark.main.GameRenderer;

public class GameScreen extends AbstractGameScreen {

    private GameManager gameManager;
    private GameRenderer gameRenderer;
    private boolean paused;

    public GameScreen(Game game) {
        super(game);
        setUpUI();
    }

    @Override
    public void render(float deltaTime) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameRenderer.render();
        // Do not update game world when paused.
        if (!paused) {
            gameManager.update(deltaTime);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.BACKSPACE)){
            game.setScreen(new CharacterSelectionScreen(game));
        }
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
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
    protected void setUpUI() {
        //todo: create UI here.
    }

    @Override
    public void resume(){
        super.resume();
        paused = false;
    }
}
