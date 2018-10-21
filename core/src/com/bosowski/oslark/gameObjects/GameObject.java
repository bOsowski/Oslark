package com.bosowski.oslark.gameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.bosowski.oslark.main.Assets;
import com.bosowski.oslarkDomains.enums.State;

public abstract class GameObject{

    //protected int id = -1;                       // id of -1 suggests the object has not been set up properly
    protected String name = "undefined";
    protected Vector3 position =  Vector3.Zero;  // The z component represents the depth.
    protected float rotation = 0;

    protected Animation animation;
    protected float stateTime;
    protected TextureRegion texture = Assets.instance.textures.get("undefined");
    protected Vector2 scale = new Vector2(1,1);
    protected Vector2 dimension = new Vector2(1,1);
    protected Vector2 origin = new Vector2(dimension.x/2, dimension.y/2);
    protected Rectangle collisionBox;
    protected boolean collides = false;


    protected GameObject(GameObject original){
        this.name = original.name;
        this.position = new Vector3(original.position);
        this.rotation = original.rotation;
        this.animation = original.animation;
        this.stateTime = original.stateTime;
        this.texture = original.texture;
        this.scale = original.scale;
        this.dimension = original.dimension;
        this.origin = original.origin;
        this.collisionBox = new Rectangle(original.collisionBox);
        this.collides = original.collides;
    }

    protected GameObject(String name, Vector3 position) {
        this.name = name;
        this.position = position;
    }

    public GameObject(String name, TextureRegion texture, Vector3 position, boolean collides, Rectangle collisionBox) {
        this.name = name;
        this.texture = texture;
        this.position = position;
        this.collides = collides;
        this.collisionBox = new Rectangle(collisionBox);
    }

    public GameObject(String name, Animation animation, Vector2 scale, boolean collides, Rectangle collisionBox, Vector3 position) {
        this.name = name;
        this.animation = animation;
        this.scale = scale;
        this.collides = collides;
        this.collisionBox = new Rectangle(collisionBox);
        this.position = position;
        System.out.println("Assigning animation. Animation state = "+this.animation);
    }

    public GameObject(String name, TextureRegion texture, Vector2 scale, boolean collides, Rectangle collisionBox, Vector3 position) {
        this.name = name;
        this.texture = texture;
        this.scale = scale;
        this.collides = collides;
        this.collisionBox = new Rectangle(collisionBox);
        this.position = position;
    }

    protected GameObject(String name, Vector2 scale, boolean collides, Rectangle collisionBox, Vector3 position) {
        this.name = name;
        this.scale = scale;
        this.collides = collides;
        this.collisionBox = new Rectangle(collisionBox);
        this.position = position;
    }

    protected GameObject(String name, Vector3 position, Vector2 scale, Vector2 dimension, Vector2 origin, boolean collides, Rectangle collisionBox){
        this.name = name;
        this.position = position;
        this.scale = scale;
        this.dimension = dimension;
        this.origin = origin;
        this.collides = collides;
        this.collisionBox = new Rectangle(collisionBox);
    }

    protected GameObject(String name, Animation animation, TextureRegion texture, Vector3 position, Vector2 scale, Vector2 dimension, Vector2 origin, boolean collides, Rectangle collisionBox){
        this.name = name;
        this.animation = animation;
        this.texture = texture;
        this.position = position;
        this.scale = scale;
        this.dimension = dimension;
        this.origin = origin;
        this.collides = collides;
        this.collisionBox = new Rectangle(collisionBox);
    }


    public void render(SpriteBatch batch){
        if(animation != null){
            if(this instanceof Creature && ((Creature)this).getState() == State.DIE){
                animation.setPlayMode(Animation.PlayMode.NORMAL);
            }
            else{
                animation.setPlayMode(Animation.PlayMode.LOOP);
            }
            stateTime += Gdx.graphics.getDeltaTime();
            texture = (TextureRegion) animation.getKeyFrame(stateTime);
        }
        batch.draw(texture.getTexture(), position.x-origin.x, position.y-origin.y, origin.x, origin.y,
                dimension.x, dimension.y, scale.x, scale.y, rotation,
                texture.getRegionX(), texture.getRegionY(), texture.getRegionWidth(), texture.getRegionHeight(),
                false, false);
    }

    public float distance(GameObject other){
        return distance(other.position);
    }

    public float distance(Vector3 otherPosition){
        if(Math.floor(otherPosition.z) == Math.floor(position.z)){
            return Vector2.dst2(otherPosition.x, otherPosition.y, position.x, position.y);
        }
        else{
            return Float.MAX_VALUE;
        }
    }

    public boolean isWithinDistance(GameObject other, float distance){
        if(Math.floor(other.position.z) == Math.floor(this.position.z) &&
                Vector2.dst(other.position.x, other.position.y, this.position.x, this.position.y) <= distance){
            return true;
        }
        return false;
    }

    public boolean isWithinDistance(Vector3 other, float distance){
        if(Math.floor(other.z) == Math.floor(this.position.z) &&
                Vector2.dst(other.x, other.y, this.position.x, this.position.y) <= distance){
            return true;
        }
        return false;
    }

    public boolean overlaps(GameObject other){
        if(Math.floor(other.position.z) == Math.floor(this.position.z) && collisionBox.overlaps(other.collisionBox)){
            return true;
        }
        return false;
    }

    public abstract void update(float deltaTime);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Vector3 getPosition() {
        return position;
    }

    public void setPosition(Vector3 position) {
        this.position = position;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public TextureRegion getTexture() {
        return texture;
    }

    public void setTexture(TextureRegion texture) {
        this.texture = texture;
    }

    public Vector2 getScale() {
        return scale;
    }

    public void setScale(Vector2 scale) {
        this.scale = scale;
    }

    public Vector2 getDimension() {
        return dimension;
    }

    public void setDimension(Vector2 dimension) {
        this.dimension = dimension;
    }

    public Vector2 getOrigin() {
        return origin;
    }

    public void setOrigin(Vector2 origin) {
        this.origin = origin;
    }

    public Animation getAnimation() {
        return animation;
    }

    public void setAnimation(Animation animation) {
        this.animation = animation;
    }

    public float getStateTime() {
        return stateTime;
    }

    public void setStateTime(float stateTime) {
        this.stateTime = stateTime;
    }

    public Rectangle getCollisionBox() {
        return collisionBox;
    }

    public void setCollisionBox(Rectangle collisionBox) {
        this.collisionBox = collisionBox;
    }

    public boolean collides() {
        return collides;
    }

    public void setCollides(boolean collides) {
        this.collides = collides;
    }
}