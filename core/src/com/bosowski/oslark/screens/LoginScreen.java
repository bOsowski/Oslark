package com.bosowski.oslark.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.bosowski.oslark.playerDomains.User;
import com.bosowski.oslark.main.Session;

import java.io.IOException;


public class LoginScreen extends AbstractGameScreen{

    Stage stage;

    TextField username;
    TextField password;
    TextButton login;


    public LoginScreen(Game game) {
        super(game);
        stage = new Stage();
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
        login.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int point, int button){
                System.out.println("CLICKING");
                try {
                    Session.instance.login(username.getText(), password.getText());
                    String userJson = Session.instance.loadUser();
                    game.setScreen(new CharacterSelectionScreen(game, userJson));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return true;
            }
        });
    }



    @Override
    public void render(float deltaTime) {
        // Clear the buffer
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(deltaTime);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }
}
