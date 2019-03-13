package com.bosowski.oslark.utils

object Constants {

  //Game World dimensions
  val WORLD_HEIGH = 10f
  val WORLD_WIDTH = 10f

  //Viewport dimenstions
  val VIEWPORT_HEIGHT = 26f
  val VIEWPORT_WIDTH = 46f

  val VIEWPORT_HEIGHT_TEXT = 1600f
  val VIEWPORT_WIDTH_TEXT = 1600f

  val GAME_TO_UI_SCALE_X = VIEWPORT_WIDTH_TEXT/4/VIEWPORT_WIDTH
  val GAME_TO_UI_SCALE_Y = VIEWPORT_HEIGHT_TEXT/4/VIEWPORT_HEIGHT

  //Images atlas
  val TEXTURE_ATLAS = "atlas/game.atlas"
  val GAME_OBJECT_DATA = "gameObjects.xml"
  val SAVE_DATA = "save.json"


  val PLAYER_MOVE_SPEED = 1.5f

  val FRAME_DURATION = 0.20f

  val FIRE_DAMAGE_DISTANCE = 2f
  val FIRE_DPS = 25f

  val NPC_REACTION_DISTANCE = 5f
  val NPC_DISTANCE_FROM_INITIAL_POSITION = 3f
}