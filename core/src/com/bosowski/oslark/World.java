package com.bosowski.oslark;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.bosowski.oslark.enums.State;
import com.bosowski.oslark.gameObjects.Creature;
import com.bosowski.oslark.gameObjects.GameObject;
import com.bosowski.oslark.gameObjects.Player;
import com.bosowski.oslark.gameObjects.Terrain;
import com.bosowski.oslark.generation.areas.DungeonCell;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by bOsowski on 27/01/2018.
 * <p>
 * World is a singleton.
 * It is responsible for game objects.
 */

public class World {
    private ArrayList<GameObject> gameObjects = new ArrayList<>();
    private final HashMap<Integer, GameObject> gameObjectsById = new HashMap<>();
    Player player;

    public static final World instance = new World();

    private World() {
        //private empty constructor. -> allows to only instantiate the class from within itself.
    }

    public void update(float deltaTime) {
        sortWorld();
        for (GameObject gameObject : gameObjects) {
            if (gameObject instanceof Creature && ((Creature) gameObject).getState() == State.DIE) {
                continue;
            }
            if (gameObject instanceof Creature) {
                gameObject.getCollisionBox().setPosition(gameObject.getPosition().x - gameObject.getOrigin().x / 2, gameObject.getPosition().y - gameObject.getOrigin().y);
                if (gameObject.collides()) {
                    ((Creature) gameObject).reactOnEnvironment(deltaTime);
                }
            }
            gameObject.update(deltaTime);
        }
    }

    private ShapeRenderer sr = new ShapeRenderer();
    static private boolean projectionMatrixSet = false;

    public void render(SpriteBatch batch) {
        for (GameObject gameObject : gameObjects) {
            gameObject.render(batch);
            //showCollision(gameObject, batch);
        }
    }

    private void showCollision(GameObject gameObject, SpriteBatch batch) {
        batch.end();
        if (gameObject.getCollisionBox() != null && !(gameObject instanceof DungeonCell)) {
            if (!projectionMatrixSet) {
                sr.setProjectionMatrix(batch.getProjectionMatrix());
            }
            sr.begin(ShapeRenderer.ShapeType.Filled);
            sr.setColor(Color.RED);
            sr.rect(gameObject.getCollisionBox().x, gameObject.getCollisionBox().y, gameObject.getCollisionBox().width, gameObject.getCollisionBox().height);
            sr.end();
        }
        batch.begin();
    }

    public void instantiate(GameObject gameObject) {
        gameObjects.add(gameObject);

        if (gameObject.getClass() == Player.class) {
            player = (Player) gameObject;
        }
    }

    public void destroy(GameObject gameObject) {
        gameObjects.remove(gameObject);
    }

    private void sortWorld() {
        gameObjects.sort((a, b) -> {
            if (a.getPosition().z > b.getPosition().z) {
                return 1;
            } else if (a.getPosition().z < b.getPosition().z) {
                return -1;
            } else if (a.getPosition().y < b.getPosition().y) {
                return 1;
            } else if (a.getPosition().y > b.getPosition().y) {
                return -1;
            } else {
                return 0;
            }
        });
    }

    public ArrayList<GameObject> getGameObjects() {
        return gameObjects;
    }

    public boolean willCollide(GameObject subject, Vector3 futurePos) {
        for (GameObject other : gameObjects) {
            if (subject != other && subject.collides() && other.collides()) {
                Rectangle subjectFutureRect = new Rectangle(futurePos.x - subject.getOrigin().x / 2, futurePos.y - subject.getOrigin().y, subject.getCollisionBox().width, subject.getCollisionBox().height);
                if (subjectFutureRect.overlaps(other.getCollisionBox())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param bounds
     * @return
     * @deprecated
     */
    public boolean isOnTerrain(Rectangle bounds) {
        for (GameObject terrain : gameObjects) {
            if (terrain instanceof DungeonCell) {
                if (terrain.getCollisionBox().contains(bounds)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void setGameObjects(ArrayList<GameObject> gameObjects) {
        this.gameObjects = gameObjects;
    }

    public HashMap<Integer, GameObject> getGameObjectsById() {
        return gameObjectsById;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
        gameObjects.add(player);
    }

    public static World getInstance() {
        return instance;
    }
}
