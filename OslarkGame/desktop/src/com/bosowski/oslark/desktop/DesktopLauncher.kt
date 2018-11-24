package com.bosowski.oslark.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.bosowski.oslark.Oslark

object DesktopLauncher {
  @JvmStatic
  fun main(arg: Array<String>) {
    val config = LwjglApplicationConfiguration()
    LwjglApplication(Oslark(), config)
  }
}
