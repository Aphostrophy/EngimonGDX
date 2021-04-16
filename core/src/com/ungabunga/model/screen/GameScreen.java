package com.ungabunga.model.screen;
import com.ungabunga.EngimonGame;
import com.ungabunga.Settings;
import com.ungabunga.model.entities.Players;
import com.ungabunga.model.controller.PlayerController;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
public class GameScreen extends AbstractScreen {

    private Players player;
    private PlayerController controller;
    private SpriteBatch batch;
    private Texture standSouth;

    public GameScreen(EngimonGame app) {
        super(app);

        standSouth = new Texture("pic/brendan_stand_south.png");

        batch = new SpriteBatch();

        player = new Players(0, 0);

        controller = new PlayerController(player);
    }

    @Override
    public  void dispose() {

    }

    @Override
    public  void hide() {

    }

    @Override
    public  void pause() {

    }

    public  void update(float delta) {

    }

    @Override
    public  void render(float delta) {
        batch.begin();
        batch.draw(standSouth,player.getX()*Settings.SCALED_TILE_SIZE,player.getY()*Settings.SCALED_TILE_SIZE,Settings.SCALED_TILE_SIZE,Settings.SCALED_TILE_SIZE*1.5f);
        batch.end();
    }

    @Override
    public  void resize(int width, int height) {

    }

    @Override
    public  void resume() {

    }

    @Override
    public  void show() {
        Gdx.input.setInputProcessor(controller);
    }

}
