package com.ungabunga;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.ungabunga.model.screen.Splash;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.ungabunga.model.utilities.SkinGenerator;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.ungabunga.model.utilities.ResourceProvider;

import java.util.ArrayList;

public class EngimonGame extends Game{
	private Splash splashScreen;
	private Skin skin;

	private ResourceProvider resourceProvider;

	public void create() {
		resourceProvider = new ResourceProvider();

		skin = SkinGenerator.generateSkin(resourceProvider.getAssetManager());
		splashScreen = new Splash(this);
		this.setScreen(splashScreen);
	}

	public void render() {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		super.render();
	}

	public AssetManager getAssetManager(){
		return resourceProvider.getAssetManager();
	}
	public ResourceProvider getResourceProvider(){
		return this.resourceProvider;
	}
	public Skin getSkin() {
		return skin;
	}
}
