package com.bosowski.oslark.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.bosowski.oslark.World;
import com.bosowski.oslark.components.Animator;
import com.bosowski.oslark.gameObjects.Creature;
import com.bosowski.oslark.gameObjects.Player;
import com.bosowski.oslark.gameObjects.Terrain;
import com.bosowski.oslark.main.Assets;
import com.bosowski.oslark.main.GameRenderer;
import com.bosowski.oslark.main.Session;
import com.bosowski.oslarkDomains.enums.Direction;
import com.bosowski.oslarkDomains.enums.State;
import com.google.gson.JsonArray;
import com.google.gson.internal.bind.JsonTreeReader;

import org.json.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class CharacterSelectionScreen extends AbstractGameScreen {

    Stage stage;

    public CharacterSelectionScreen(Game game) {
        super(game);
        Skin fieldSkins = new Skin(Gdx.files.internal("uiskin.json"));
        stage = new Stage();

        String userJson = null;
        try {
            userJson = Session.instance.loadUser();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(userJson);
        JSONObject obj = new JSONObject(userJson);
        System.out.println(obj.get("username"));
        JSONArray characterArray = obj.getJSONArray("characters");
        TextField label = new TextField("Choose your character", fieldSkins);
        label.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()-100);
        stage.addActor(label);

        HashMap<String, Player> characters = new HashMap<>();

        int i = 0;
        for(Object character: characterArray){

            System.out.println("DEBUG -------- START");
            System.out.println(character);
            System.out.println((JSONObject)character);
            System.out.println("DEBUG -------- END");

            Player player = new Player((JSONObject)character);
            TextButton characterButton = new TextButton(player.getName()+"(level "+player.getLevel()+" "+player.getCharacterClass(), fieldSkins);
            characters.put(player.getName(), player);
            characterButton.setPosition(label.getX(), label.getY()-50*i);
            characterButton.addListener(new ClickListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int point, int button){
                    System.out.println("Picked "+player.getName());
                    World.instance.setPlayer(characters.get(player.getName()));
                    String worldJson = null;
                    try {
                        worldJson = Session.instance.loadWorld("characterName="+World.instance.getPlayer().getName());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    JSONObject jsonObj = new JSONObject(worldJson);
                    JSONArray terrain = jsonObj.getJSONArray("terrain");
                    for(Object tile: terrain){
                        Terrain terrainTile = new Terrain((JSONObject)tile);
                        World.instance.instantiate(terrainTile);
                        System.out.println("Loaded terrain: "+terrainTile);
                    }

                    game.setScreen(new GameScreen(game));
//                    for(int posx = 0; posx<10; posx++){
//                        for(int posy = 0; posy<10; posy++){
//                            Terrain terrain = new Terrain("floor", Assets.instance.textures.get("floor1"), Terrain.TerrainType.NORMAL, new Vector2(1,1), false,new Rectangle(), new Vector3(posx, posy, -1));
//                            World.instance.instantiate(terrain);
//                        }
//                    }
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
