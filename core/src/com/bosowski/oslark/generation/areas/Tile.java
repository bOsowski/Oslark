package com.bosowski.oslark.generation.areas;

import com.badlogic.gdx.math.Vector3;
import com.bosowski.oslark.gameObjects.Terrain;

import org.lwjgl.util.vector.Vector2f;

public class Tile {
    public Vector2f position;
    public Terrain.TerrainType type;

    public Tile(Vector2f position, Terrain.TerrainType type){
        this.position = position;
        this.type = type;
    }
}
