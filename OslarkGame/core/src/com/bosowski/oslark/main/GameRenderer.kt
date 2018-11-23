//package com.bosowski.oslark.main
//
//import com.badlogic.gdx.Screen
//import com.badlogic.gdx.graphics.OrthographicCamera
//import com.badlogic.gdx.graphics.g2d.SpriteBatch
//import com.badlogic.gdx.math.Vector3
//import com.badlogic.gdx.utils.Disposable
//import com.bosowski.oslark.utils.Constants
//
///**
// * Created by bOsowski on 27/01/2018.
// */
//
//class GameRenderer
////private OrthographicCamera GUIcamera;
//
//(private val gameManager: GameManager) : Disposable {
//  private var batch: SpriteBatch? = null
//
//  init {
//    init()
//  }
//
//  private fun init() {
//    batch = SpriteBatch()
//    camera.position.set(Vector3.Zero)
//    camera.update()
//    //GUIcamera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
//    //GUIcamera.position.set(0,0,0);
//    //GUIcamera.update();
//  }
//
//  fun render() {
//    batch!!.projectionMatrix = camera.combined
//    batch!!.begin()
//    gameManager.render(batch!!)
//    batch!!.end()
//  }
//
//
//  override fun dispose() {
//    batch!!.dispose()
//  }
//
//  companion object {
//    var camera: OrthographicCamera  = OrthographicCamera(Constants.VIEWPORT_WIDTH * 2, Constants.VIEWPORT_HEIGHT * 2)
//    var currentScreen: Screen? = null
//  }
//}
