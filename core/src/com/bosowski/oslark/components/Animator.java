package com.bosowski.oslark.components;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.bosowski.oslarkDomains.enums.Direction;
import com.bosowski.oslarkDomains.enums.State;

import java.util.HashMap;

public class Animator {

    private HashMap<Direction, HashMap<State, Animation>> animations;

    public Animator(HashMap<Direction, HashMap<State, Animation>> animations){
        this.animations = animations;
    }

    public HashMap<Direction, HashMap<State, Animation>> getAnimations(){
        return animations;
    }


}
