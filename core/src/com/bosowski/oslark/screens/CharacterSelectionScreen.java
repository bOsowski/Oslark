package com.bosowski.oslark.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.bosowski.oslark.gameObjects.Creature;
import com.bosowski.oslark.gameObjects.Player;
import com.bosowski.oslark.main.Session;
import com.google.gson.internal.bind.JsonTreeReader;

import org.json.*;

import java.io.IOException;
import java.util.ArrayList;

public class CharacterSelectionScreen extends AbstractGameScreen {

    Stage stage;

    public CharacterSelectionScreen(Game game, String userJson) {
        super(game);
        Skin fieldSkins = new Skin(Gdx.files.internal("uiskin.json"));
        stage = new Stage();

        System.out.println(userJson);
        JSONObject obj = new JSONObject(userJson);
        System.out.println(obj.get("username"));
        JSONArray characterArray = obj.getJSONArray("characters");
        TextField label = new TextField("Choose your character", fieldSkins);
        label.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        stage.addActor(label);
        int i = 0;
        for(Object character: characterArray){
            String characterName = ((JSONObject)character).get("name").toString();
            String characterLevel = ((JSONObject)character).get("level").toString();
            String characterClass = ((JSONObject)character).get("characterClass").toString();
            TextButton characterButton = new TextButton(characterName+"(level "+characterLevel+" "+characterClass, fieldSkins);
            characterButton.setPosition(label.getX(), label.getY()-50*i);
            characterButton.addListener(new ClickListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int point, int button){
                    System.out.println("Picked "+characterName);
                    //Player player = new Player();
                    game.setScreen(new GameScreen(game, null));
                    return true;
                }
            });
            stage.addActor(characterButton);
            i++;
        }
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
