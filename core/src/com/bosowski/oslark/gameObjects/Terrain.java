package com.bosowski.oslark.gameObjects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;


public class Terrain extends GameObject {

    private TerrainType terrain;

    public Terrain(Terrain terrain){
        super(terrain);
        this.terrain = terrain.terrain;
    }

    public Terrain(String name, TextureRegion texture, TerrainType terrain, Vector2 scale, boolean collides, Rectangle collisionBox, Vector3 position){
        super(name, texture, scale, collides, collisionBox, position);
        this.terrain = terrain;
    }

    public Terrain(String name, Animation animation, TerrainType terrain, Vector2 scale, boolean collides, Rectangle collisionBox, Vector3 position){
        super(name, animation, scale, collides, collisionBox, position);
        this.terrain = terrain;
    }

    public TerrainType getTerrain() {
        return terrain;
    }

    @Override
    public String toString() {
        return "Terrain{" +
                "terrain=" + terrain +
                ", name='" + name + '\'' +
                ", position=" + position +
                ", rotation=" + rotation +
                ", animation=" + animation +
                ", stateTime=" + stateTime +
                ", texture=" + texture +
                ", scale=" + scale +
                ", dimension=" + dimension +
                ", origin=" + origin +
                '}';
    }

    @Override
    public void update(float deltaTime) {}

    public enum TerrainType{
        NORMAL("normal"), OBSTRUCTION("obstruction"), WATER("water"), MUCK("muck"), FIRE("fire");

        private String name;

        TerrainType(String name){
            this.name = name;
        }

        public String getName(){
            return name;
        }

    }
}