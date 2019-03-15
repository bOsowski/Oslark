package com.bosowski.oslark

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.assets.AssetErrorListener
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.utils.Disposable
import com.bosowski.oslark.enums.State
import com.bosowski.oslark.utils.Constants

import java.util.HashMap

/**
 * Singleton class responsible for asset management.
 */
object Assets
  : Disposable, AssetErrorListener {
  private val TAG = Assets::javaClass.name
  private var assetManager: AssetManager? = null
  val textures = HashMap<String, TextureRegion>()
  val animations = HashMap<String, Animation<TextureRegion>>()
  val stateAnimations = HashMap<String, HashMap<State, Animation<TextureRegion>>>()
  lateinit var font: BitmapFont


  fun init(assetManager: AssetManager) {
    Assets.assetManager = assetManager
    assetManager.setErrorListener(this)

    //load texture from game sprites
    assetManager.load(Constants.TEXTURE_ATLAS, TextureAtlas::class.java)
    assetManager.finishLoading()

    for (name in assetManager.assetNames) {
      //Gdx.app.debug(TAG, "asset '$name' loaded.")
    }

    //create atlas for game sprites
    val atlas = assetManager.get<TextureAtlas>(Constants.TEXTURE_ATLAS)

    for (region in atlas.regions) {
      //insert the loaded texture into the map.
      if (region.index == -1) {
        textures[region.name] = region
        //Gdx.app.debug(TAG, "texture: '" + region.name + "' loaded.")
      } else if (!animations.containsKey(region.name)) {
        val animation = atlas.findRegions(region.name)
        animations[region.name] = Animation<TextureRegion>(Constants.FRAME_DURATION, animation, Animation.PlayMode.LOOP)
        //Gdx.app.debug(TAG, "animation: '" + region.name + ", amount of animations: " + animation.size + "' loaded.")
      }

    }

    for (animationName in animations.keys) {
      //println(animationName)
      if (animationName.contains("_")) {
        if (animationName.contains("male") || animationName.contains("female")) {
          val split = animationName.split("_".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
          if (!stateAnimations.containsKey(split[0] + split[1])) {
            stateAnimations[split[0] + split[1]] = HashMap<State, Animation<TextureRegion>>()
          }
          if (!stateAnimations[split[0] + split[1]]!!.containsKey(State.getState(split[2]))) {
            stateAnimations[split[0] + split[1]]!![State.getState(split[2])] = animations[animationName]!!
          }
          stateAnimations[split[0] + split[1]]!![State.getState(split[2])] = animations[animationName]!!
          //println(split[0] + split[1])

        } else {
          val split = animationName.split("_".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
          if (!stateAnimations.containsKey(split[0])) {
            stateAnimations[split[0]] = HashMap()
          }
          if (!stateAnimations[split[0]]!!.containsKey(State.getState(split[1]))) {
            stateAnimations[split[0]]!![State.getState(split[1])] = animations[animationName]!!
          }

          stateAnimations[split[0]]!![State.getState(split[1])] = animations[animationName]!!
        }
      }
    }

    val generator = FreeTypeFontGenerator(Gdx.files.internal("OpenSans-Regular.ttf"))
    val parameter = FreeTypeFontGenerator.FreeTypeFontParameter()
    parameter.borderWidth = 1f
    parameter.borderColor = Color.BLACK
    parameter.size = 16
    font = generator.generateFont(parameter)
    generator.dispose()
  }

  override fun error(asset: AssetDescriptor<*>, throwable: Throwable) {
    Gdx.app.error(Assets::javaClass.name, "Couldn't load asset '$asset'", throwable)
  }

  override fun dispose() {
    assetManager!!.dispose()
  }
}