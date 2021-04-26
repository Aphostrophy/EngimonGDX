package com.ungabunga.model.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.audio.Music;
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
import com.ungabunga.model.controller.DialogueController;
import com.ungabunga.model.controller.PlayerController;
import com.ungabunga.model.dialogue.Dialogue;
import com.ungabunga.model.dialogue.DialogueNode;
import com.ungabunga.model.entities.Battle;
import com.ungabunga.model.entities.Engimon;
import com.ungabunga.model.entities.LivingEngimon;
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

    public DialogueBox dialogueBox;

    public OptionBox optionBox;
    public DialogueController dialogueController;

    private Music themeSong;

    public GameScreen(EngimonGame app) throws IOException {
        super(app);

        gameViewport = new ScreenViewport();

        batch = new SpriteBatch();
        HUDBatch = new SpriteBatch();

        this.themeSong = Gdx.audio.newMusic(Gdx.files.internal("song/themeflute.ogg"));
        themeSong.setLooping(true);
        themeSong.setVolume(.04f);
        themeSong.play();

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
        playerAnimations.addrun(
                new Animation(0.25f/2f, atlas.findRegions("brendan_run_north"), Animation.PlayMode.LOOP_PINGPONG),
                new Animation(0.25f/2f, atlas.findRegions("brendan_run_south"), Animation.PlayMode.LOOP_PINGPONG),
                new Animation(0.25f/2f, atlas.findRegions("brendan_run_west"), Animation.PlayMode.LOOP_PINGPONG),
                new Animation(0.25f/2f, atlas.findRegions("brendan_run_east"), Animation.PlayMode.LOOP_PINGPONG));
        map = new TmxMapLoader().load("Maps/BigMap.tmx");

        gameState = new GameState("orz", playerAnimations,map, app);

        controller = new PlayerController(gameState,this);

        camera = new OrthographicCamera();

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
        dialogueController = new DialogueController(dialogueBox,optionBox,this);

        multiplexer.addProcessor(0, controller);
        multiplexer.addProcessor(1, dialogueController);

        dialogueController.startTutorialDialogue();
    }

    @Override
    public  void dispose() {
        batch.dispose();
        map.dispose();
        renderer.dispose();
        themeSong.dispose();
    }

    @Override
    public  void hide() {

    }

    @Override
    public  void pause() {

    }

    public  void update(float delta) {
        camera.position.set(gameState.player.getWorldX() * Settings.SCALED_TILE_SIZE,gameState.player.getWorldY() * Settings.SCALED_TILE_SIZE,0);
        camera.update();
        uiStage.act(delta);
    }

    @Override
    public  void render(float delta) {
        if(gameState.getPlayerInventory().getEngimonInventory().getFilledSlot()<=0 && gameState.player.getActiveEngimon()==null){
            getApp().setScreen(new GameOverScreen(getApp()));
        }
        controller.update(delta);
        dialogueController.update(delta);

        gameState.update(delta);

        if (controller.isBreederOpen) {
            try {
                BreederScreen breederScreen = new BreederScreen(getApp(), controller, gameState,this);
                getApp().setScreen(breederScreen);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(controller.isInventoryOpen) {
            try {
                getApp().setScreen(new InventoryScreen(getApp(), controller, gameState.getPlayerInventory(),this));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(controller.isDetailOpen) {
            try {
                getApp().setScreen(new DetailEngimonScreen(getApp(), this, gameState.player.getActiveEngimon(), getApp().getResourceProvider(), controller));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        renderer.setView(camera);
        renderer.render();

        camera.position.set(gameState.player.getWorldX() * Settings.SCALED_TILE_SIZE,gameState.player.getWorldY() * Settings.SCALED_TILE_SIZE,0);
        camera.update();
        uiStage.act(delta);
        gameViewport.apply();

        batch.begin();
        batch.setProjectionMatrix(camera.combined);

        for(int y=0;y<gameState.map.length();y++){
            for(int x=0;x<gameState.map.get(y).length();x++){
                if(gameState.map.get(y).get(x).occupier != null && (y!=gameState.player.getY() || x!=gameState.player.getX())) {
                    LivingEngimon occupier = gameState.map.get(y).get(x).occupier;
                    if (gameState.player.getActiveEngimon()!= null){
                        if (gameState.player.getActiveEngimon().getX() == x && gameState.player.getActiveEngimon().getY() == y) {
                            batch.draw(occupier.getSprite(), occupier.getWorldX() * Settings.SCALED_TILE_SIZE, occupier.getWorldY() * Settings.SCALED_TILE_SIZE, Settings.SCALED_TILE_SIZE * 1.0f, Settings.SCALED_TILE_SIZE * 1.0f);
                        } else {
                            batch.draw(occupier.getSpriteAura(), occupier.getWorldX() * Settings.SCALED_TILE_SIZE, occupier.getWorldY() * Settings.SCALED_TILE_SIZE, Settings.SCALED_TILE_SIZE * 1.0f, Settings.SCALED_TILE_SIZE * 1.5f);
                            Battle b = new Battle();
                            b.BattleEngimon(gameState.player.getActiveEngimon(), (Engimon) occupier);
                            if (b.getActiveEngimonPower() > b.getEnemyEngimonPower()) {
                                batch.draw(occupier.getSprite(), occupier.getWorldX() * Settings.SCALED_TILE_SIZE, occupier.getWorldY() * Settings.SCALED_TILE_SIZE, Settings.SCALED_TILE_SIZE * 0.8f, Settings.SCALED_TILE_SIZE * 0.8f);
                            } else {
                                batch.draw(occupier.getSprite(), occupier.getWorldX() * Settings.SCALED_TILE_SIZE, occupier.getWorldY() * Settings.SCALED_TILE_SIZE, Settings.SCALED_TILE_SIZE * 1.2f, Settings.SCALED_TILE_SIZE * 1.2f);
                            }
                        }
                    } else {
                        batch.draw(occupier.getSpriteAura(), occupier.getWorldX() * Settings.SCALED_TILE_SIZE, occupier.getWorldY() * Settings.SCALED_TILE_SIZE, Settings.SCALED_TILE_SIZE * 1.0f, Settings.SCALED_TILE_SIZE * 1.5f);
                        batch.draw(occupier.getSprite(), occupier.getWorldX() * Settings.SCALED_TILE_SIZE, occupier.getWorldY() * Settings.SCALED_TILE_SIZE, Settings.SCALED_TILE_SIZE * 1.0f, Settings.SCALED_TILE_SIZE * 1.0f);
                    }
                }
            }
        }
        batch.draw(gameState.player.getSprite(),gameState.player.getWorldX()*Settings.SCALED_TILE_SIZE,gameState.player.getWorldY()*Settings.SCALED_TILE_SIZE,Settings.SCALED_TILE_SIZE,Settings.SCALED_TILE_SIZE*1.5f);

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
        root = new Table();
        root.setFillParent(true);

        uiStage.addActor(root);

        Table dialogTable2 = new Table();

        uiStage.addActor(root);

        dialogueBox =  new DialogueBox(getApp().getSkin());
        dialogueBox.setVisible(false);

        optionBox = new OptionBox(getApp().getSkin());
        optionBox.setVisible(false);


        dialogTable2.add(optionBox).expand().align(Align.right).space(8f).row();
        dialogTable2.add(dialogueBox).expand().align(Align.bottom).space(8f).row();
        root.add(dialogTable2).expand().align(Align.bottom);
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public GameState getGameState() {
        return gameState;
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
    }

}
