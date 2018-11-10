package com.bosowski.oslark.gameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.bosowski.oslark.components.Animator;
import com.bosowski.oslark.enums.Direction;
import com.bosowski.oslark.enums.State;
import com.bosowski.oslark.generation.areas.Passage;
import com.bosowski.oslark.generation.areas.TileArea;
import com.bosowski.oslark.main.Assets;
import com.bosowski.oslark.main.GameRenderer;

import org.json.JSONObject;

/**
 * Created by bOsowski on 11/02/2018.
 */

public class Player extends Creature{

    Gender gender;
    CharacterClass characterClass;

    public Player(JSONObject jsonObject){
        super(jsonObject.getJSONObject("super"));
        this.gender = Gender.valueOf(jsonObject.getString("gender"));
        this.characterClass = CharacterClass.valueOf(jsonObject.getString("characterClass"));
        Gdx.app.error(TAG, "Trying to load animator "+ "'"+ name+gender.name +"'" +"for '"+name+"' ("+id+")");
        this.animator = new Animator(Assets.instance.stateAnimations.get(characterClass.name+gender.name));
        setState(state);
    }

    public Player(String name, Animator animator, Vector2 scale, boolean collides, Rectangle collisionBox, float totalHitPoints, float hitPoints, float damage, State state, Direction direction, float speed, int level, Vector3 position, Gender gender, CharacterClass characterClass) {
        super(name, animator, scale, collides, collisionBox, totalHitPoints, hitPoints, damage, state, direction, speed, level, position);
        this.gender = gender;
        this.characterClass = characterClass;
    }

    public Player(Creature original){
        super(original);
    }

    public void update(float deltaTime){
        GameRenderer.camera.position.set(position);
        GameRenderer.camera.update();
    }

    public enum Gender{
        MALE("male"), FEMALE("female");

        public final String name;

        Gender(String name){
            this.name = name;
        }
    }

    public enum CharacterClass {
        KNIGHT("knight"), WIZARD("wizard"), ELF("elf");

        public final String name;

        CharacterClass(String name){
            this.name = name;
        }
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public CharacterClass getCharacterClass() {
        return characterClass;
    }

    public void setCharacterClass(CharacterClass characterClass) {
        this.characterClass = characterClass;
    }
}
