package com.bosowski.oslark.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.bosowski.oslark.main.Session;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LoginScreen extends AbstractGameScreen{

    Stage stage;

    TextField username;
    TextField password;
    TextButton login;


    public LoginScreen(Game game) {
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

        login.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int point, int button){
                System.out.println("CLICKING");
                try {
                    Session.instance.login(username.getText(), password.getText());
                    System.out.println(Session.instance.loadUser());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return true;
            }
        });
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
