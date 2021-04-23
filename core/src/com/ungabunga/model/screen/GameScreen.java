package com.ungabunga.model.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.ungabunga.EngimonGame;
import com.ungabunga.Settings;
import com.ungabunga.model.GameState;
import com.ungabunga.model.controller.PlayerController;
import com.ungabunga.model.entities.Inventory;
import com.ungabunga.model.screen.components.InventoryUI;
import com.ungabunga.model.utilities.AnimationSet;

import java.io.IOException;

import static com.ungabunga.Settings.ANIM_TIMER;

public class GameScreen extends AbstractScreen {

    private GameState gameState;
    private PlayerController controller;
    private SpriteBatch batch;
    private Stage stage;

    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;

    public GameScreen(EngimonGame app) throws IOException {
        super(app);

        TextureAtlas atlas = app.getAssetManager().get("pic/packed/avatarTextures.atlas", TextureAtlas.class);
        AnimationSet playerAnimations = new AnimationSet(
                new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("brendan_walk_south"), Animation.PlayMode.LOOP_PINGPONG),
                new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("brendan_walk_north"),Animation.PlayMode.LOOP_PINGPONG),
                new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("brendan_walk_west"),Animation.PlayMode.LOOP_PINGPONG),
                new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("brendan_walk_east"),Animation.PlayMode.LOOP_PINGPONG),
                atlas.findRegion("brendan_stand_south"),
                atlas.findRegion("brendan_stand_north"),
                atlas.findRegion("brendan_stand_west"),
                atlas.findRegion("brendan_stand_east")
        );

        batch = new SpriteBatch();

        gameState = new GameState("orz", playerAnimations);

        controller = new PlayerController(gameState);

        camera = new OrthographicCamera();

        Viewport viewport = new ScreenViewport(camera);

        stage = new Stage(viewport);
    }

    @Override
    public  void dispose() {
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
        controller.update(delta);
        gameState.player.update(delta);
        stage.addActor(gameState.inventoryUI);

        renderer.setView(camera);
        renderer.render();

        camera.position.set(gameState.player.getWorldX() * Settings.SCALED_TILE_SIZE,gameState.player.getWorldY() * Settings.SCALED_TILE_SIZE,0);
        camera.update();

        batch.begin();
        batch.setProjectionMatrix(camera.combined);
        if(gameState.isInventoryOpen) {
            stage.draw();
        } else {
            batch.draw(gameState.player.getSprite(),gameState.player.getWorldX()*Settings.SCALED_TILE_SIZE,gameState.player.getWorldY()*Settings.SCALED_TILE_SIZE,Settings.SCALED_TILE_SIZE,Settings.SCALED_TILE_SIZE*1.5f);
        }
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
    }

}
