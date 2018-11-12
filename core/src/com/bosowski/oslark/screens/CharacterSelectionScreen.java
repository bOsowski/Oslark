package com.bosowski.oslark.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.bosowski.oslark.World;
import com.bosowski.oslark.gameObjects.Player;
import com.bosowski.oslark.gameObjects.Terrain;
import com.bosowski.oslark.generation.areas.Dungeon;
import com.bosowski.oslark.generation.areas.DungeonCell;
import com.bosowski.oslark.generation.areas.DungeonRoom;
import com.bosowski.oslark.generation.areas.Maze;
import com.bosowski.oslark.main.Session;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Vector;

public class CharacterSelectionScreen extends AbstractGameScreen {

    private final JSONArray characterArray;

    public CharacterSelectionScreen(Game game) {
        super(game);
        String userJson = null;
        try {
            userJson = Session.instance.loadUser();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(userJson);
        JSONObject obj = new JSONObject(userJson);
        System.out.println(obj.get("username"));
        characterArray = obj.getJSONArray("characters");
        setUpUI();
    }

    @Override
    protected void setUpUI(){
        Label label = new Label("Choose your character", fieldSkins);
        label.setPosition(Gdx.graphics.getWidth() / 3, Gdx.graphics.getHeight() - 100);
        stage.addActor(label);

        LinkedHashMap<String, Player> characters = new LinkedHashMap<>();

        int i = 0;
        for (Object character : characterArray) {
            Player player = new Player((JSONObject) character);
            TextButton characterButton = new TextButton(player.getName() + "(level " + player.getLevel() + " " + player.getCharacterClass(), fieldSkins);
            characters.put(player.getName(), player);
            characterButton.setPosition(label.getX(), label.getY() - 50 * (i + 1));
            characterButton.addListener(new ClickListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int point, int button) {
                    System.out.println("Picked " + player.getName());
                    World.instance.setPlayer(characters.get(player.getName()));
                    String worldJson = null;
                    try {
                        worldJson = Session.instance.loadWorld("characterName=" + World.instance.getPlayer().getName());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println(worldJson);
                    JSONObject jsonObj = new JSONObject(worldJson);
                    JSONArray terrain = jsonObj.getJSONArray("terrain");
                    for (Object tile : terrain) {
                        Terrain terrainTile = new Terrain((JSONObject) tile);
                        //World.instance.instantiate(terrainTile);
                        System.out.println("Loaded terrain: " + terrainTile);
                    }

                    game.setScreen(new GameScreen(game));
                    return true;
                }
            });
            stage.addActor(characterButton);
            i++;
        }
    }

//    public static ArrayList<GameObject> createCorridor(Vector3 position, int length, int width, boolean shutoffOnLeft, boolean shutOffOnRight, boolean shutOffOnTop) {
//        ArrayList<GameObject> result = new ArrayList<>();
//
//        for (int x = 0; x < length; x++) {
//            for (int y = 0; y < width; y++) {
//                Terrain floor = new Terrain(0, "floor1", new Vector3(position.x + x, position.y - y, position.z - 0.1f), true);
//                result.add(floor);
//            }
//            if (shutOffOnTop) {
//                Terrain wall = new Terrain(0, "wallMid", new Vector3(position.x + x, position.y + 1, position.z - 1f), false);
//                Terrain wallTop = new Terrain(0, "wallMid", new Vector3(position.x + x, position.y + 2, position.z - 1f), false);
//                result.add(wall);
//                result.add(wallTop);
//            }
//        }
//
//        return result;
//    }

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
        super.resize(width, height);
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
