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
	private AssetManager assetManager;

	private ResourceProvider resourceProvider;

	public void create() {
		assetManager = new AssetManager();
		assetManager.load("pic/packed/avatarTextures.atlas", TextureAtlas.class);
		assetManager.load("pic/packed/uipack.atlas", TextureAtlas.class);
		assetManager.load("pic/font/small_letters_font.fnt", BitmapFont.class);

		assetManager.load("pic/engimon_packed/arcanine.atlas", TextureAtlas.class);
		assetManager.load("pic/engimon_packed/blastoise.atlas", TextureAtlas.class);
		assetManager.load("pic/engimon_packed/cubone.atlas", TextureAtlas.class);
		assetManager.load("pic/engimon_packed/diglett.atlas", TextureAtlas.class);
		assetManager.load("pic/engimon_packed/glaceon.atlas", TextureAtlas.class);
		assetManager.load("pic/engimon_packed/glalie.atlas", TextureAtlas.class);
		assetManager.load("pic/engimon_packed/magmar.atlas", TextureAtlas.class);
		assetManager.load("pic/engimon_packed/mareep.atlas", TextureAtlas.class);
		assetManager.load("pic/engimon_packed/ninetales.atlas", TextureAtlas.class);
		assetManager.load("pic/engimon_packed/psyduck.atlas", TextureAtlas.class);
		assetManager.load("pic/engimon_packed/raichu.atlas", TextureAtlas.class);
		assetManager.load("pic/engimon_packed/shark.atlas", TextureAtlas.class);
		assetManager.load("pic/engimon_packed/tongkol.atlas", TextureAtlas.class);
		assetManager.load("pic/engimon_packed/voltorb.atlas", TextureAtlas.class);
		assetManager.load("pic/engimon_packed/wartotle.atlas", TextureAtlas.class);

		assetManager.finishLoading();
		skin = SkinGenerator.generateSkin(assetManager);
		resourceProvider = new ResourceProvider();
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
	public ResourceProvider getResourceProvider(){
		return this.resourceProvider;
	}
	public Skin getSkin() {
		return skin;
	}
}
