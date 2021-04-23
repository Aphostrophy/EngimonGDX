package com.ungabunga.model.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.ungabunga.EngimonGame;
import com.ungabunga.Settings;
import com.ungabunga.model.GameState;
import com.ungabunga.model.controller.OptionBoxController;
import com.ungabunga.model.controller.PlayerController;
import com.ungabunga.model.ui.DialogueBox;
import com.ungabunga.model.ui.OptionBox;
import com.ungabunga.model.utilities.AnimationSet;

import java.io.IOException;

import static com.ungabunga.Settings.ANIM_TIMER;

public class GameScreen extends AbstractScreen {

    private GameState gameState;

    private InputMultiplexer multiplexer;
    private PlayerController controller;
    private SpriteBatch batch;
    private OptionBoxController optionBoxController;
    private Stage stage;
    private SpriteBatch HUDBatch;
    private Sprite BreederMenuInactive;
    private Sprite BreederMenuActive;
    private Sprite InventoryInactive;
    private Sprite InventoryActive;

    private TiledMap map;

    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;

    private Viewport gameViewport;
    private int uiScale = 2;

    private Stage uiStage;
    private Table root;
    private DialogueBox dialogueBox;
    private OptionBox optionBox;
    public GameScreen(EngimonGame app) throws IOException {
        super(app);

        gameViewport = new ScreenViewport();

        batch = new SpriteBatch();
        HUDBatch = new SpriteBatch();

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



        map = new TmxMapLoader().load("Maps/Map.tmx");

        gameState = new GameState("orz", playerAnimations,map);

        controller = new PlayerController(gameState);

        camera = new OrthographicCamera();

        stage = new Stage(gameViewport);

        // nanti diganti yg bagusan dikit icon breedernya
        Texture splashTexture = new Texture("img/breeder_icon_inactive.png");
        this.BreederMenuInactive = new Sprite(splashTexture);
        BreederMenuInactive.setSize(80, 80);

        splashTexture = new Texture("img/breeder_icon_active.png");
        this.BreederMenuActive = new Sprite(splashTexture);
        BreederMenuActive.setSize(80, 80);

        splashTexture = new Texture("img/inventory_inactive.png");
        this.InventoryInactive = new Sprite(splashTexture);
        InventoryInactive.setSize(70, 70);

        splashTexture = new Texture("img/inventory_active.png");
        this.InventoryActive = new Sprite(splashTexture);
        InventoryActive.setSize(70, 70);

        initUI();
        multiplexer = new InputMultiplexer();
        controller = new PlayerController(gameState);
        optionBoxController = new OptionBoxController(optionBox);
        multiplexer.addProcessor(0,controller);
        multiplexer.addProcessor(1,optionBoxController);

    }

    @Override
    public  void dispose() {
//        batch.dispose();
//        map.dispose();
//        renderer.dispose();
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
        uiStage.act(delta);
        gameViewport.apply();

        batch.begin();
        batch.setProjectionMatrix(camera.combined);
        if(gameState.isInventoryOpen) {
            stage.draw();
        } else {
            batch.draw(gameState.player.getSprite(),gameState.player.getWorldX()*Settings.SCALED_TILE_SIZE,gameState.player.getWorldY()*Settings.SCALED_TILE_SIZE,Settings.SCALED_TILE_SIZE,Settings.SCALED_TILE_SIZE*1.5f);
        }
        batch.end();

        uiStage.draw();


        HUDBatch.begin();
        if (Gdx.input.getX() < 105 && Gdx.input.getX() > 25 && Gdx.graphics.getHeight() - Gdx.input.getY() < Gdx.graphics.getHeight() + 80 && Gdx.graphics.getHeight() - Gdx.input.getY() > Gdx.graphics.getHeight() - 85) {
            BreederMenuActive.setCenter(65, Gdx.graphics.getHeight() - 55);
            BreederMenuActive.draw(HUDBatch);
        } else {
            BreederMenuInactive.setCenter(65, Gdx.graphics.getHeight() - 55);
            BreederMenuInactive.draw(HUDBatch);
        }

        if (Gdx.input.getX() < 220 && Gdx.input.getX() > 130 && Gdx.graphics.getHeight() - Gdx.input.getY() < Gdx.graphics.getHeight() + 80 && Gdx.graphics.getHeight() - Gdx.input.getY() > Gdx.graphics.getHeight() - 85) {
            InventoryActive.setCenter(160, Gdx.graphics.getHeight() - 55);
            InventoryActive.draw(HUDBatch);
        } else {
            InventoryInactive.setCenter(160, Gdx.graphics.getHeight() - 55);
            InventoryInactive.draw(HUDBatch);
        }
        HUDBatch.end();

    }
    private void initUI() {
        uiStage = new Stage(new ScreenViewport());
        uiStage.getViewport().update(Gdx.graphics.getWidth()/uiScale,Gdx.graphics.getWidth()/uiScale);
//        uiStage.setDebugAll(true);
        root = new Table();
        root.setFillParent(true);
        uiStage.addActor(root);
        Table dialogTable = new Table();
        dialogueBox =  new DialogueBox(getApp().getSkin());
        dialogueBox.animateText("Hellow BGST!\n KEREN GA DIALOGUE BOXNYA HEHEHEHEHEEHEHEHE");

        optionBox = new OptionBox(getApp().getSkin());
        optionBox.addOption("Option 1");
        optionBox.addOption("Option 2");
        optionBox.addOption("Option 3");

        dialogTable.add(optionBox).expand().align(Align.right).space(8f).row();
        dialogTable.add(dialogueBox).expand().align(Align.bottom).space(8f).row();
        root.add(dialogTable).expand().align(Align.bottom);
//        root.add(dialogueBox).expand().align(Align.bottom).pad(8f);

    }
    @Override
    public  void resize(int width, int height) {
        camera.viewportHeight = height;
        camera.viewportWidth = width;
        uiStage.getViewport().update(width/uiScale, height/uiScale,true);
        gameViewport.update(width, height);
        camera.update();
    }

    @Override
    public  void resume() {

    }

    @Override
    public  void show() {
        renderer = new OrthogonalTiledMapRenderer(map);

        Gdx.input.setInputProcessor(multiplexer);

        camera = new OrthographicCamera();
    }

}
