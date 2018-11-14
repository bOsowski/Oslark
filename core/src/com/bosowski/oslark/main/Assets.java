package com.bosowski.oslark.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.bosowski.oslark.enums.State;
import com.bosowski.oslark.utils.Constants;

import java.util.HashMap;

/**
 * Singleton class responsible for asset management.
 */
public class Assets implements Disposable, AssetErrorListener{

    public static final Assets instance = new Assets();
    public static final String TAG = Assets.class.getName();
    private AssetManager assetManager;
    public final HashMap<String, TextureRegion> textures = new HashMap<>();
    public final HashMap<String, Animation> animations = new HashMap<>();
    public final HashMap<String, HashMap<State, Animation>> stateAnimations = new HashMap<>();



    private Assets(){
        //empty private constructor. -> prevents instantiating the class from outside.
    }

    public void init(AssetManager assetManager){
        this.assetManager = assetManager;
        assetManager.setErrorListener(this);

        //load texture from game sprites
        assetManager.load(Constants.TEXTURE_ATLAS, TextureAtlas.class);
        assetManager.finishLoading();

        for(String name: assetManager.getAssetNames()){
            Gdx.app.debug(TAG, "asset '" + name+"' loaded.");
        }

        //create atlas for game sprites
        TextureAtlas atlas = assetManager.get(Constants.TEXTURE_ATLAS);

        for(TextureAtlas.AtlasRegion region: atlas.getRegions()){
            //insert the loaded texture into the map.
            if(region.index == -1){
                textures.put(region.name, region);
                Gdx.app.debug(TAG, "texture: '" + region.name + "' loaded.");
            }
            else if(!animations.containsKey(region.name)){
                Array<TextureAtlas.AtlasRegion> animation = atlas.findRegions(region.name);
                animations.put(region.name, new Animation<>(Constants.FRAME_DURATION, animation, Animation.PlayMode.LOOP));
                Gdx.app.debug(TAG, "animation: '" + region.name + ", amount of animations: "+ animation.size +"' loaded.");
            }

        }

        for(String animationName: animations.keySet()){
            System.out.println(animationName);
            if(animationName.contains("_")){
                if(animationName.contains("male") || animationName.contains("female")){
                    String[] split = animationName.split("_");
                    if(!stateAnimations.containsKey(split[0]+split[1])){
                        stateAnimations.put(split[0]+split[1], new HashMap<>());
                    }
                    if(!stateAnimations.get(split[0]+split[1]).containsKey(State.getState(split[2]))){
                        stateAnimations.get(split[0]+split[1]).put(State.getState(split[2]), animations.get(animationName));
                    }
                    stateAnimations.get(split[0]+split[1]).put(State.getState(split[2]), animations.get(animationName));
                    System.out.println(split[0]+split[1]);

                }
                else{
                    String[] split = animationName.split("_");
                    if(!stateAnimations.containsKey(split[0])){
                        stateAnimations.put(split[0], new HashMap<>());
                    }
                    if(!stateAnimations.get(split[0]).containsKey(State.getState(split[1]))){
                        stateAnimations.get(split[0]).put(State.getState(split[1]), animations.get(animationName));
                    }

                    stateAnimations.get(split[0]).put(State.getState(split[1]), animations.get(animationName));
                }

            }
        }
    }

    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        Gdx.app.error(TAG, "Couldn't load asset '" + asset + "'", throwable);
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }


}