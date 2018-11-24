package com.bosowski.oslark.desktop

import com.badlogic.gdx.tools.texturepacker.TexturePacker

internal object AtlasBuilder {

  @JvmStatic
  fun main(args: Array<String>) {
    // Adjust this to control the debug outline.
    val drawDebugOutline = false

    val settings = TexturePacker.Settings()
    settings.useIndexes = true
    settings.maxHeight = 1024
    settings.maxWidth = 1024
    settings.debug = drawDebugOutline
    settings.duplicatePadding = true
    TexturePacker.process(settings, "sprites_RAW", "atlas", "game.atlas")
    println("\n*SUCCESS*")
  }
}


