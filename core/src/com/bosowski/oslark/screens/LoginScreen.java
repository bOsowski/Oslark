package com.bosowski.oslark.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;


public class LoginScreen extends AbstractGameScreen{

    Stage stage;

    TextField username;
    TextField password;
    TextButton login;


    public LoginScreen(Game game){
        super(game);
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        Skin fieldSkins = new Skin(Gdx.files.internal("uiskin.json"));
        username = new TextField("username", fieldSkins);
        password = new TextField("password", fieldSkins);
        login = new TextButton("Login", fieldSkins);
        username.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        password.setPosition(username.getX(), username.getY()-50);
        login.setPosition(password.getX(), password.getY()-50);
        stage.addActor(username);
        stage.addActor(password);
        stage.addActor(login);
    }

    @Override
    public void render(float deltaTime) {
        stage.act(deltaTime);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }
}
