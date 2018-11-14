package com.bosowski.oslark.gameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.bosowski.oslark.main.Assets;

import org.json.JSONObject;


public class Terrain extends GameObject {

    private TerrainType terrain = TerrainType.NORMAL;

    public Terrain(Terrain terrain){
        super(terrain);
        this.terrain = terrain.terrain;
    }

    public Terrain(int id, String name, Vector3 position, boolean collides){
        super(id,name,position, collides);
        setUp();
    }

    public Terrain(String name, Vector3 position, boolean collides){
        super(name, position, collides);
        setUp();
    }

    public Terrain(JSONObject jsonObject){
        super(jsonObject.getJSONObject("super"));
        this.terrain = TerrainType.valueOf(jsonObject.getString("terrainType"));
//        setUp();
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
                "id=" + id +
                ", name='" + name + '\'' +
                ", position=" + position +
                ", rotation=" + rotation +
                ", animation=" + animation +
                ", stateTime=" + stateTime +
                ", texture=" + texture +
                ", scale=" + scale +
                ", dimension=" + dimension +
                ", origin=" + origin +
                ", collisionBox=" + collisionBox +
                ", collides=" + collides +
                '}';
    }

    protected void setUp(){
        if(Assets.instance.animations.containsKey(name)){
            this.animation = Assets.instance.animations.get(name);
        }
        else if(Assets.instance.textures.containsKey(name)){
            this.texture = Assets.instance.textures.get(name);
        }
        else{
            this.texture = Assets.instance.textures.get("undefined");
            Gdx.app.error(TAG, "Unable to load any textures for object '"+name+"' ("+id+")");
        }
        this.collisionBox = new Rectangle(this.position.x-origin.x, this.position.y, 1, 1);
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