package com.bosowski.oslark.main;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bosowski.oslark.World;
import com.bosowski.oslark.utils.Constants;
import com.bosowski.oslarkDomains.enums.Direction;
import com.bosowski.oslarkDomains.enums.State;

public class GameManager extends InputAdapter{
    public static final String TAG = GameManager.class.getName();
    public World world = World.instance;

    public GameManager() {
        init();
    }

    private void init(){
        Gdx.input.setInputProcessor(this);
    }

    public void update(float deltaTime){
        handleGameInput(deltaTime);
        world.update(deltaTime);
    }

    public void render(SpriteBatch batch){
        world.render(batch);
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.ESCAPE || keycode == Input.Keys.BACK) {
            Gdx.app.exit();
        }
        return false;
    }

    public void handleGameInput(float deltaTime){
        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            World.instance.getPlayer().move(Constants.PLAYER_MOVE_SPEED,deltaTime, Direction.UP);
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.S)){
            World.instance.getPlayer().move(Constants.PLAYER_MOVE_SPEED,deltaTime, Direction.DOWN);
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.D)){
            World.instance.getPlayer().move(Constants.PLAYER_MOVE_SPEED,deltaTime, Direction.RIGHT);
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.A)){
            World.instance.getPlayer().move(Constants.PLAYER_MOVE_SPEED,deltaTime, Direction.LEFT);
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
            World.instance.getPlayer().attack();
        }
        else{
            World.instance.getPlayer().setState(State.IDLE);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.P)){
            World.instance.getPlayer().receiveDamage(10f);
        }
    }


}
