package com.ungabunga.model.screen;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ungabunga.EngimonGame;

import java.io.IOException;

public class MainMenu implements Screen {

    private static final int BUTTON_WIDTH = 150;
    private static final int BUTTON_HEIGHT = 75;

    private EngimonGame game;

    private SpriteBatch batch;

    private Sprite heading;

    private Splash splash;

    private Texture playButtonActive;
    private Texture playButtonInactive;
    private Texture exitButtonActive;
    private Texture exitButtonInactive;

    public MainMenu(EngimonGame game, Splash splash) {
        this.game = game;
        this.playButtonActive = new Texture("img/play_button_active.png");
        this.playButtonInactive = new Texture("img/play_button_inactive.png");
        this.exitButtonActive = new Texture("img/exit_button_active.png");
        this.exitButtonInactive = new Texture("img/exit_button_inactive.png");
        this.batch = new SpriteBatch();
        this.splash = splash;

        // NANTI GANTI LOGONYA DISINI LAGI
        Texture splashTexture = new Texture("LOGO.png");
        this.heading = new Sprite(splashTexture);

        heading.setSize(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);

        Gdx.input.setInputProcessor(new InputAdapter() {

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                //Exit button
                int x = Gdx.graphics.getWidth() / 2 - BUTTON_WIDTH / 2;
                if (Gdx.input.getX() < x + BUTTON_WIDTH && Gdx.input.getX() > x && Gdx.graphics.getHeight() - Gdx.input.getY() < Gdx.graphics.getHeight() / 4 + BUTTON_HEIGHT && Gdx.graphics.getHeight() - Gdx.input.getY() > Gdx.graphics.getHeight() / 4) {
                    Gdx.app.exit();
                }

//                if (Gdx.input.getX() < x + BUTTON_WIDTH && Gdx.input.getX() > x && Gdx.graphics.getHeight() - Gdx.input.getY() < Gdx.graphics.getHeight() / 2 + BUTTON_HEIGHT && Gdx.graphics.getHeight() - Gdx.input.getY() > Gdx.graphics.getHeight() / 2) {
//                    try {
//                        game.setScreen(new GameScreen(game));
//                        splash.dispose();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }

                //Play game button
                if (Gdx.input.getX() < x + BUTTON_WIDTH && Gdx.input.getX() > x && Gdx.graphics.getHeight() - Gdx.input.getY() < Gdx.graphics.getHeight() / 2 + BUTTON_HEIGHT && Gdx.graphics.getHeight() - Gdx.input.getY() > Gdx.graphics.getHeight() / 2) {
                    try {
                        game.setScreen(new GameScreen(game));
                        splash.dispose();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                return super.touchUp(screenX, screenY, pointer, button);
            }

        });
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        int x = Gdx.graphics.getWidth() / 2 - BUTTON_WIDTH / 2;
        Gdx.gl.glClearColor(0, 0, .25f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        batch.begin();
        heading.setCenter(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight() - 125);
        heading.draw(batch);

        if (Gdx.input.getX() < x + BUTTON_WIDTH && Gdx.input.getX() > x && Gdx.graphics.getHeight() - Gdx.input.getY() < Gdx.graphics.getHeight() / 2 + BUTTON_HEIGHT && Gdx.graphics.getHeight() - Gdx.input.getY() > Gdx.graphics.getHeight() / 2) {
            batch.draw(playButtonActive, Gdx.graphics.getWidth() / 2 - BUTTON_WIDTH / 2, Gdx.graphics.getHeight() / 2, BUTTON_WIDTH, BUTTON_HEIGHT);
        } else {
            batch.draw(playButtonInactive, Gdx.graphics.getWidth() / 2 - BUTTON_WIDTH / 2, Gdx.graphics.getHeight() / 2, BUTTON_WIDTH, BUTTON_HEIGHT);
        }

        if (Gdx.input.getX() < x + BUTTON_WIDTH && Gdx.input.getX() > x && Gdx.graphics.getHeight() - Gdx.input.getY() < Gdx.graphics.getHeight() / 4 + BUTTON_HEIGHT && Gdx.graphics.getHeight() - Gdx.input.getY() > Gdx.graphics.getHeight() / 4) {
            batch.draw(exitButtonActive, Gdx.graphics.getWidth() / 2 - BUTTON_WIDTH / 2, Gdx.graphics.getHeight() / 4, BUTTON_WIDTH, BUTTON_HEIGHT);
        } else {
            batch.draw(exitButtonInactive, Gdx.graphics.getWidth() / 2 - BUTTON_WIDTH / 2, Gdx.graphics.getHeight() / 4, BUTTON_WIDTH, BUTTON_HEIGHT);
        }
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
        Gdx.input.setInputProcessor(null);
        playButtonInactive.dispose();
        playButtonActive.dispose();
        exitButtonActive.dispose();
        exitButtonInactive.dispose();
        batch.dispose();
    }
}
