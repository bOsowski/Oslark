package com.bosowski.oslark.components;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.bosowski.oslark.enums.State;

import java.util.HashMap;

public class Animator {

    private HashMap<State, Animation> animations;

    public Animator(HashMap<State, Animation> animations){
        this.animations = animations;
    }

    public HashMap<State, Animation> getAnimations(){
        return animations;
    }


}