package com.ungabunga.model.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.ungabunga.EngimonGame;

public class MainMenu implements Screen {

    private static final int BUTTON_WIDTH = 150;
    private static final int BUTTON_HEIGTH = 75;

    private EngimonGame game;

    private SpriteBatch batch;

    private Texture playButtonActive;
    private Texture playButtonInactive;
    private Texture exitButtonActive;
    private Texture exitButtonInactive;

    public MainMenu(EngimonGame game) {
        this.game = game;
        this.playButtonActive = new Texture("img/play_button_active.png");
        this.playButtonInactive = new Texture("img/play_button_inactive.png");
        this.exitButtonActive = new Texture("img/exit_button_active.png");
        this.exitButtonInactive = new Texture("img/exit_button_inactive.png");
        this.batch = new SpriteBatch();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        int x = Gdx.graphics.getWidth() / 2 - BUTTON_WIDTH / 2;
        Gdx.gl.glClearColor(0, 0, 0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(exitButtonActive, Gdx.graphics.getWidth()/2 - BUTTON_WIDTH/2, Gdx.graphics.getHeight()/4, BUTTON_WIDTH, BUTTON_HEIGTH);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
