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
            public void touchUp(InputEvent e, float x, float y, int point, int button){
                try {
                    System.out.println("CLICKING");
                    login();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        stage.addActor(username);
        stage.addActor(password);
        stage.addActor(login);
    }

    public static void login() throws IOException {
        CookieManager cm = new CookieManager();
        CookieHandler.setDefault(cm);
        String message = "username=admin&password=admin";

        URL url = new URL("http://localhost:8080/login/authenticate");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setUseCaches(false);
        connection.setDoOutput(true);
        connection.setDoInput(true);
        //connection.getOutputStream().write(message.getBytes());
        DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
        wr.writeBytes(message);
        wr.flush();
        wr.close();

        int responseCode = connection.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + message);
        System.out.println("Response Code : " + responseCode+"\n");

        System.out.println(connection.getHeaderFields().toString());

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        System.out.println(response);
        in.close();
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
