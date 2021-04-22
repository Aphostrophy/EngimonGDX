package com.ungabunga;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.ungabunga.model.screen.GameScreen;
import com.ungabunga.model.screen.Splash;

import java.io.IOException;

public class EngimonGame extends Game{
	private GameScreen gameScreen;
	private Splash splashScreen;

	public void create() {
		try {
			gameScreen = new GameScreen(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
		splashScreen = new Splash(this);
		this.setScreen(splashScreen);
//		this.setScreen(gameScreen);
	}

	public void render() {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		super.render();
	}
}
