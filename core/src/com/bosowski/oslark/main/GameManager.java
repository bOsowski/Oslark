package com.bosowski.oslark.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bosowski.oslark.World;
import com.bosowski.oslark.enums.Direction;
import com.bosowski.oslark.enums.State;
import com.bosowski.oslark.generation.areas.Dungeon;

public class GameManager extends InputAdapter {
    public static final String TAG = GameManager.class.getName();
    public World world = World.instance;
    private static Dungeon dungeon = new Dungeon();


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
        } else if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            //World.instance.getPlayer().attack();
            dungeon.clear();
            dungeon = new Dungeon();
            dungeon.create();
        } else if (!Gdx.input.isKeyPressed(Input.Keys.W) && !Gdx.input.isKeyPressed(Input.Keys.S) && !Gdx.input.isKeyPressed(Input.Keys.D) && !Gdx.input.isKeyPressed(Input.Keys.A)) {
            World.instance.getPlayer().setState(State.IDLE);
        }


        if (Gdx.input.isKeyPressed(Input.Keys.P)) {
            World.instance.getPlayer().receiveDamage(10f);
        }
    }

}
