package com.bosowski.oslark.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.bosowski.oslark.World;
import com.bosowski.oslark.enums.Direction;
import com.bosowski.oslark.enums.State;
import com.bosowski.oslark.gameObjects.Terrain;
import com.bosowski.oslark.generation.areas.Dungeon;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import static java.lang.Thread.sleep;

public class GameManager extends InputAdapter {
    public static final String TAG = GameManager.class.getName();
    public World world = World.instance;
    private static Dungeon dungeon;


    public GameManager() {
        init();
    }

    private void init() {
        Gdx.input.setInputProcessor(this);
    }

    public void update(float deltaTime) {
        handleGameInput(deltaTime);
        world.update(deltaTime);
    }

    public void render(SpriteBatch batch) {
        world.render(batch);
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.ESCAPE || keycode == Input.Keys.BACK) {
            Gdx.app.exit();
        }
        return false;
    }

    public void handleGameInput(float deltaTime) {
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            World.instance.getPlayer().move(World.instance.getPlayer().getSpeed(), deltaTime, Direction.UP);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            World.instance.getPlayer().move(World.instance.getPlayer().getSpeed(), deltaTime, Direction.DOWN);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            World.instance.getPlayer().move(World.instance.getPlayer().getSpeed(), deltaTime, Direction.RIGHT);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            World.instance.getPlayer().move(World.instance.getPlayer().getSpeed(), deltaTime, Direction.LEFT);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.O)){
            World.showCollisionBoxes = !World.showCollisionBoxes;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.ENTER)){
            world.getGameObjects().clear();
            world.instantiate(world.getPlayer());
            String worldJson;
            try {
                worldJson = Session.instance.loadWorld("characterName=" + World.instance.getPlayer().getName());
                System.out.println(worldJson);
                JSONObject jsonObj = new JSONObject(worldJson);
                JSONArray terrain = jsonObj.getJSONArray("terrain");
                for (Object tile : terrain) {
                    Terrain terrainTile = new Terrain((JSONObject) tile);
                    World.instance.instantiate(terrainTile);
                    System.out.println("Loaded terrain: " + terrainTile);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            //World.instance.getPlayer().attack();
            world.getGameObjects().clear();
            world.instantiate(world.getPlayer());
            dungeon = new Dungeon( new Rectangle(-5, -5, 10, 10), 2,4, 4, 0);
            dungeon.create();
            try {
                sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else if (!Gdx.input.isKeyPressed(Input.Keys.W) && !Gdx.input.isKeyPressed(Input.Keys.S) && !Gdx.input.isKeyPressed(Input.Keys.D) && !Gdx.input.isKeyPressed(Input.Keys.A)) {
            World.instance.getPlayer().setState(State.IDLE);
        }


        if (Gdx.input.isKeyPressed(Input.Keys.P)) {
            World.instance.getPlayer().receiveDamage(10f);
        }
    }

}
