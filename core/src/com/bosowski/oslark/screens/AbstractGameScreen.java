package com.bosowski.oslark.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.bosowski.oslark.main.Assets;

public abstract class AbstractGameScreen implements Screen{

    protected Game game;
    protected Stage stage;
    protected Skin fieldSkins = new Skin(Gdx.files.internal("uiskin.json"));

    public AbstractGameScreen(Game game) {
        this.game = game;
        stage = new Stage();
    }

    public abstract void render(float deltaTime);

    public void resize(int width, int height){
        stage = new Stage();
        setUpUI();
        show();
    }

    public abstract void show();

    public abstract void hide();

    public abstract void pause();

    protected abstract void setUpUI();

    public void resume() {
        Assets.instance.init(new AssetManager());
    }

    public void dispose() {
        Assets.instance.dispose();
    }

}
