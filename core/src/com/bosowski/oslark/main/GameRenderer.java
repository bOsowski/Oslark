package com.bosowski.oslark.main;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;
import com.bosowski.oslark.utils.Constants;

/**
 * Created by bOsowski on 27/01/2018.
 */

public class GameRenderer implements Disposable{
    private GameManager gameManager;
    private SpriteBatch batch;
    public static OrthographicCamera camera;
    //private OrthographicCamera GUIcamera;

    public GameRenderer(GameManager gameManager){
        this.gameManager = gameManager;
        init();
    }

    private void init(){
        batch = new SpriteBatch();
        camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
        camera.position.set(Vector3.Zero);
        camera.update();
        //GUIcamera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
        //GUIcamera.position.set(0,0,0);
        //GUIcamera.update();
    }

    public void render(){
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        gameManager.render(batch);
        batch.end();
    }



    @Override
    public void dispose() {
        batch.dispose();
    }
}
