package com.bosowski.oslark.gameObjects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.bosowski.oslark.World;
import com.bosowski.oslark.components.Animator;
import com.bosowski.oslark.utils.Constants;
import com.bosowski.oslarkDomains.enums.Direction;
import com.bosowski.oslarkDomains.enums.State;

import java.util.ArrayList;

public abstract class Creature extends GameObject{

    protected float totalHitPoints;
    protected float hitPoints;
    protected boolean hasAttacked = false;
    protected float damage = 0;
    protected Animator animator;
    protected State state = State.MOVE;
    protected Direction direction = Direction.DOWN;
    protected float speed = 0;
    protected int level = 1;


    public Creature(String name, Animator animator, Vector2 scale, boolean collides, Rectangle collisionBox, float totalHitPoints, float hitPoints, float damage, State state, Direction direction, float speed, int level, Vector3 position){
        super(name, scale, collides, collisionBox, position);
        this.totalHitPoints = totalHitPoints;
        this.hitPoints = hitPoints;
        this.animator = animator;
        this.state = state;
        this.direction = direction;
        this.damage = damage;
        this.speed = speed;
        this.level = level;
    }

    public Creature(Creature original){
        super(original);
        this.totalHitPoints = original.totalHitPoints;
        this.hitPoints = original.hitPoints;
        this.animator = original.animator;
        this.state = original.state;
        this.direction = original.direction;
        this.hasAttacked = original.hasAttacked;
        this.damage = original.damage;
        this.speed = original.speed;
    }

    public abstract void update(float deltaTime);

    public void attack(){
        animation.setFrameDuration(Constants.FRAME_DURATION);
        setState(State.ATTACK);
        if(state == State.ATTACK && animation.getKeyFrameIndex(stateTime) == animation.getKeyFrames().length-1 && !hasAttacked){
            Rectangle attackArea = new Rectangle(position.x+direction.value.x, position.y+direction.value.y, 1, 1);
            System.out.println(name+" is attacking.");
            for(GameObject gameObject: World.instance.getGameObjects()){
                if(gameObject != this && gameObject instanceof Creature && gameObject.collides && Math.floor(position.z) == Math.floor(gameObject.position.z) && gameObject.collisionBox.overlaps(attackArea)){
                    ((Creature)gameObject).receiveDamage(damage);
                    System.out.println("Attacked ."+gameObject.name);
                }
            }
            hasAttacked = true;
        }
        else if(animation.getKeyFrameIndex(stateTime) != animation.getKeyFrames().length-1 && hasAttacked){
            hasAttacked = false;
        }
    }

    public boolean move(float amount, float deltaTime, Direction direction){
        if(state == State.DIE){
            return false;
        }
        if(checkCollisions(deltaTime).contains(Terrain.TerrainType.MUCK)){
            amount = amount/2;
            stateTime -= deltaTime/2;
        }

        Vector3 futurePos = new Vector3(position);
        futurePos.mulAdd(direction.value, deltaTime*amount);
        setDirection(direction);
        if(!World.instance.willCollide(this, futurePos)) {
            position = futurePos;
            setState(State.MOVE);
            return true;
        }
        return false;
    }

    public void receiveDamage(float damage){
        System.out.println(name+" received "+damage+" damage.");
        setHitPoints(hitPoints - damage);
    }



    public float getTotalHitPoints() {
        return totalHitPoints;
    }

    public void setTotalHitPoints(float totalHitPoints) {
        this.totalHitPoints = totalHitPoints;
    }

    public float getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(float hitPoints) {
        if(hitPoints > totalHitPoints){
            this.hitPoints = totalHitPoints;
        }
        else if(hitPoints <= 0){
            this.hitPoints = 0;
            setState(State.DIE);
            position.z = (float)Math.floor(position.z)+Float.MIN_VALUE;
            collides = false;
            System.out.println(name+" died.");
        }
        else{
            this.hitPoints = hitPoints;
        }
    }

    public ArrayList<Terrain.TerrainType> checkCollisions(float deltaTime){
        ArrayList<Terrain.TerrainType> terrainCollisions = new ArrayList<>();
        for(GameObject gameObject: World.instance.getGameObjects()){
            if(gameObject instanceof Terrain && collisionBox.overlaps(gameObject.collisionBox)){
                switch (((Terrain)gameObject).getTerrain()){
                    case MUCK:
                        //System.out.println("On muck");
                        terrainCollisions.add(Terrain.TerrainType.MUCK);
                        break;
                    case NORMAL:
                        //System.out.println("On normal");
                        terrainCollisions.add(Terrain.TerrainType.NORMAL);
                        break;
                    case OBSTRUCTION:
                        //System.out.println("On obstruction");
                        terrainCollisions.add(Terrain.TerrainType.OBSTRUCTION);
                        break;
                    case FIRE:
                        //System.out.println("On fire");
                        terrainCollisions.add(Terrain.TerrainType.FIRE);
                        break;
                    case WATER:
                        //System.out.println("On water");
                        terrainCollisions.add(Terrain.TerrainType.WATER);
                        break;
                    default:
                        //System.out.println("On default");
                        break;
                }
            }
        }
        return terrainCollisions;
    }

    public void reactOnEnvironment(float deltaTime) {
        for (GameObject gameObject : World.instance.getGameObjects()) {
            if(gameObject instanceof Terrain){
                Terrain terrain = (Terrain)gameObject;
                if(terrain.getTerrain() == Terrain.TerrainType.FIRE){
                    float distance = distance(terrain);
                    if(distance <= Constants.FIRE_DAMAGE_DISTANCE){
                        float damage = Constants.FIRE_DPS/(distance+1);
                        System.out.println("Damage = "+damage);
                        setHitPoints(getHitPoints()-(deltaTime*damage));
                    }
                }
            }
        }
    }


    public Animator getAnimator() {
        return animator;
    }

    public void setAnimator(Animator animator) {
        this.animator = animator;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        if(this.state != state && this.state != State.DIE){
            stateTime = 0;
            this.state = state;
            animation = animator.getAnimations().get(direction).get(state);
        }
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
        animation = animator.getAnimations().get(direction).get(state);
    }


}