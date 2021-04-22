package com.ungabunga.model.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.ungabunga.EngimonGame;
import com.ungabunga.Settings;
import com.ungabunga.model.GameState;
import com.ungabunga.model.controller.PlayerController;

import java.io.IOException;


public class GameScreen extends AbstractScreen {

    private GameState gameState;
    private PlayerController controller;
    private SpriteBatch batch;

    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;

    public GameScreen(EngimonGame app) throws IOException {
        super(app);

        batch = new SpriteBatch();

        gameState = new GameState("orz");

        controller = new PlayerController(gameState.player);
    }

    @Override
    public  void dispose() {
        gameState.player.avatar.dispose();
        batch.dispose();
        map.dispose();
        renderer.dispose();
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
        renderer.setView(camera);
        renderer.render();

        camera.position.set(gameState.player.getX() * Settings.SCALED_TILE_SIZE,gameState.player.getY() * Settings.SCALED_TILE_SIZE,0);
        camera.update();

        batch.begin();
        batch.setProjectionMatrix(camera.combined);

        batch.draw(gameState.player.avatar,gameState.player.getX()*Settings.SCALED_TILE_SIZE,gameState.player.getY()*Settings.SCALED_TILE_SIZE,Settings.SCALED_TILE_SIZE,Settings.SCALED_TILE_SIZE*1.5f);
        batch.end();
    }

    @Override
    public  void resize(int width, int height) {
        camera.viewportHeight = height;
        camera.viewportWidth = width;
        camera.update();
    }

    @Override
    public  void resume() {

    }

    @Override
    public  void show() {
        map = new TmxMapLoader().load("Maps/Map.tmx");

        renderer = new OrthogonalTiledMapRenderer(map);

        Gdx.input.setInputProcessor(controller);

        camera = new OrthographicCamera();
    }

}
