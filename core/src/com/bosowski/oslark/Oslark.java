package com.bosowski.oslark;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.bosowski.oslark.main.Assets;
import com.bosowski.oslark.screens.LoginScreen;

public class Oslark extends Game {
	
	@Override
	public void create () {
		// Set Libgdx log level
		Gdx.app.setLogLevel(Application.LOG_DEBUG);

		// Load assets
		Assets.instance.init(new AssetManager());

		setScreen(new LoginScreen(this));
	}
}
