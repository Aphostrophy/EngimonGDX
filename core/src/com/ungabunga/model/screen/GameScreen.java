package com.ungabunga.model.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.ungabunga.EngimonGame;
import com.ungabunga.Settings;
import com.ungabunga.model.controller.PlayerController;
import com.ungabunga.model.entities.Players;
public class GameScreen extends AbstractScreen {

    private Players player;
    private PlayerController controller;
    private SpriteBatch batch;
    private Texture standSouth;

    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;

    public GameScreen(EngimonGame app) {
        super(app);

        standSouth = new Texture("pic/brendan_stand_south.png");

        batch = new SpriteBatch();

        player = new Players(0, 0);

        controller = new PlayerController(player);
    }

    @Override
    public  void dispose() {
        standSouth.dispose();
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
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.setView(camera);
        renderer.render();

        batch.begin();
        batch.draw(standSouth,player.getX()*Settings.SCALED_TILE_SIZE,player.getY()*Settings.SCALED_TILE_SIZE,Settings.SCALED_TILE_SIZE,Settings.SCALED_TILE_SIZE*1.5f);
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
