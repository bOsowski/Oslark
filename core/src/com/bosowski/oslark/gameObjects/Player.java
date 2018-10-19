package com.bosowski.oslark.gameObjects;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.bosowski.oslark.main.GameRenderer;

/**
 * Created by bOsowski on 11/02/2018.
 */

public class Player extends Creature{
    OrthographicCamera camera;

//    public Player(int id, String name, Animator animator, Vector2 scale, boolean collides, Rectangle collisionBox){
//        super(id, name, animator, scale, collides, collisionBox);
//    }

    public Player(Creature original){
        super(original);
        camera = GameRenderer.camera;
    }

    public void update(float deltaTime){
        camera.position.set(position);
        camera.update();
    }
}
