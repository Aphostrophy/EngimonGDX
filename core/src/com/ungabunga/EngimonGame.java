package com.ungabunga;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.ungabunga.model.screen.GameScreen;
public class EngimonGame extends Game{
	private GameScreen screen;

	public void create() {
		screen = new GameScreen(this);

		this.setScreen(screen);
	}

	public void render() {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		super.render();
	}
}
