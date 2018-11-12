package com.bosowski.oslark.generation.areas;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.bosowski.oslark.World;
import com.bosowski.oslark.enums.Direction;
import com.bosowski.oslark.gameObjects.Terrain;

import java.util.HashMap;

public class DungeonCell extends Terrain {

    HashMap<Direction, Terrain> walls = new HashMap<>();

    public DungeonCell(String name, Vector3 position, boolean collides) {
        super(name, position, collides);
        walls.put(Direction.UP, new Terrain("wallUp", new Vector3(position.x, position.y+1f, 0), true));
        walls.get(Direction.UP).getCollisionBox().y =  walls.get(Direction.UP).getCollisionBox().y - walls.get(Direction.UP).getOrigin().y;

        walls.put(Direction.DOWN, new Terrain("wallDown", new Vector3(position.x, position.y, 0), true));
        walls.get(Direction.DOWN).getCollisionBox().y =  walls.get(Direction.DOWN).getCollisionBox().y - walls.get(Direction.DOWN).getOrigin().y;
        walls.get(Direction.DOWN).getCollisionBox().height =  0.1f;

        walls.put(Direction.LEFT, new Terrain("wallLeft", new Vector3(position.x, position.y, 0), true));
        walls.get(Direction.LEFT).getCollisionBox().y =  walls.get(Direction.LEFT).getCollisionBox().y - walls.get(Direction.LEFT).getOrigin().y;
        walls.get(Direction.LEFT).setDimension(new Vector2(0.2f,2f));
        walls.get(Direction.LEFT).getCollisionBox().width =  walls.get(Direction.LEFT).getDimension().x;
        walls.get(Direction.LEFT).setOrigin(new Vector2(0.5f,0.5f));

        walls.put(Direction.RIGHT, new Terrain("wallRight", new Vector3(position.x, position.y, 0), true));
        walls.get(Direction.RIGHT).setDimension(new Vector2(-0.2f,2f));
        walls.get(Direction.RIGHT).getCollisionBox().x =  walls.get(Direction.RIGHT).getPosition().x + walls.get(Direction.RIGHT).getOrigin().x;
        walls.get(Direction.RIGHT).getCollisionBox().y =  walls.get(Direction.RIGHT).getPosition().y - walls.get(Direction.RIGHT).getOrigin().y;
        walls.get(Direction.RIGHT).getCollisionBox().width =  walls.get(Direction.RIGHT).getDimension().x;
        walls.get(Direction.RIGHT).setOrigin(new Vector2(-0.5f,0.5f));
    }

    public void instantiate(){
        World.instance.instantiate(this);
        for(Terrain wall: walls.values()){
            World.instance.instantiate(wall);
        }
    }
}
