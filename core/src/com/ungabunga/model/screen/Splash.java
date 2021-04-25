package com.ungabunga.model.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ungabunga.EngimonGame;

import java.io.IOException;

public class Splash implements Screen {
    private SpriteBatch batch;
    private Sprite splash;
    private BitmapFont font;
    private GlyphLayout text;
    private float y;
    private float alpha = 0;

    private EngimonGame game;

    private Music openingTheme;

    public Splash(EngimonGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();

        // NANTI LOGO GAMENYA DIGANTIIII!!!!!
        Texture splashTexture = new Texture("Logo.png");
        splash = new Sprite(splashTexture);
        font = new BitmapFont(Gdx.files.internal("font/white.fnt"));
        text = new GlyphLayout(font, "PRESS SPACE TO CONTINUE!!!");
        splash.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.openingTheme = Gdx.audio.newMusic(Gdx.files.internal("song/OpeningTheme.ogg"));

        Splash splash = this;

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keyCode) {
                if (keyCode == Input.Keys.SPACE) {
                    game.setScreen(new MainMenu(game, splash));
                }
                return true;
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Pixmap pm = new Pixmap(Gdx.files.internal("img/cursor_32.png"));
        Gdx.graphics.setCursor(Gdx.graphics.newCursor(pm, 0, 0));
        pm.dispose();

        openingTheme.setLooping(true);
        openingTheme.setVolume(.025f);
        openingTheme.play();

        batch.begin();
        splash.setCenter(Gdx.graphics.getWidth()/2, y);
        if(alpha < 1) {
            splash.draw(batch, alpha);
        } else {
            splash.draw(batch);
            font.draw(batch, "PRESS SPACE TO CONTINUE!!!", Gdx.graphics.getWidth() / 2 - text.width/2, Gdx.graphics.getHeight() / 5);
        }

        if(alpha < 1) {
            alpha += 0.00125;
        }
        if(y < Gdx.graphics.getHeight()/2) {
            y += 1;
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
        batch.dispose();
        splash.getTexture().dispose();
        font.dispose();
        openingTheme.dispose();
    }
}
