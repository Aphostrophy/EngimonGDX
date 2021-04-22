package com.ungabunga.model.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.ungabunga.EngimonGame;
import com.ungabunga.Settings;
import com.ungabunga.model.controller.PlayerController;
import com.ungabunga.model.entities.Player;
public class GameScreen extends AbstractScreen {

    private Player player;
    private PlayerController controller;
    private SpriteBatch batch;

    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;

    public GameScreen(EngimonGame app) {
        super(app);

        batch = new SpriteBatch();

        player = new Player("orz");

        controller = new PlayerController(player);
    }

    @Override
    public  void dispose() {
        player.avatar.dispose();
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

        camera.position.set(player.getX() * Settings.SCALED_TILE_SIZE,player.getY() * Settings.SCALED_TILE_SIZE,0);
        camera.update();

        batch.begin();
        batch.setProjectionMatrix(camera.combined);

        batch.draw(player.avatar,player.getX()*Settings.SCALED_TILE_SIZE,player.getY()*Settings.SCALED_TILE_SIZE,Settings.SCALED_TILE_SIZE,Settings.SCALED_TILE_SIZE*1.5f);
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
