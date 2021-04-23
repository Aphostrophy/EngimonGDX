package com.ungabunga;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.ungabunga.model.screen.Splash;

public class EngimonGame extends Game{
	private Splash splashScreen;

	private AssetManager assetManager;

	public void create() {
		assetManager = new AssetManager();
		assetManager.load("pic/packed/avatarTextures.atlas", TextureAtlas.class);
		assetManager.finishLoading();

		splashScreen = new Splash(this);
		this.setScreen(splashScreen);
	}

	public void render() {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		super.render();
	}

	public AssetManager getAssetManager(){
		return assetManager;
	}
}
