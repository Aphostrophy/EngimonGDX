package com.ungabunga.model.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ungabunga.EngimonGame;
import com.ungabunga.model.GameState;
import com.ungabunga.model.controller.PlayerController;
import com.ungabunga.model.entities.Bag;
import com.ungabunga.model.entities.Player;
import com.ungabunga.model.ui.DialogueBox;
import com.ungabunga.model.ui.InventoryItem;
import com.ungabunga.model.ui.InventoryUI;

import java.io.IOException;


public class InventoryScreen extends AbstractScreen implements Screen {

    private GameScreen gameScreen;

    private PlayerController controller;

    private Stage uiStage;

    private Bag bag;

    private Table root;
    private DialogueBox dialogueBox;
    private Table topBar;
    private Table inventoryWrapper;
    private Table dialogTable;
    private Table backButton;
    private Table bottomBar;
    private Table sortEngimonButton;
    private Table sortSkillItemButton;
    private Table title;

    public InventoryScreen(EngimonGame app, PlayerController controller, Bag bag, GameScreen gameScreen) throws IOException {
        super(app);
        this.controller = controller;
        this.bag = bag;
        this.gameScreen = gameScreen;
        initUI();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(uiStage);
    }

    @Override
    public void render(float delta) {
        controller.update(delta);
        Gdx.gl.glClearColor(0.50f, 0.79f, 0.61f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (!controller.isInventoryOpen) {
            getApp().setScreen(gameScreen);
            this.dispose();
        }

        uiStage.act(delta);
        uiStage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void update(float delta) {
        controller.update(delta);
    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        uiStage.dispose();
    }

    private void initUI() {
        uiStage = new Stage(new ScreenViewport());

        root = new Table();
        root.setSize(uiStage.getWidth(),uiStage.getHeight());

        topBar = new Table();
        bottomBar = new Table();
        inventoryWrapper = new Table();
        dialogTable = new Table();
        backButton = new Table();
        sortEngimonButton = new Table();
        sortSkillItemButton = new Table();
        title = new Table();


        Image bg = new Image(new Texture("img/inventory_title.png"));
        title.add(bg).width(500);

        dialogueBox =  new DialogueBox(getApp().getSkin());
        dialogueBox.animateText("Kamu mendapatkan Engimon baru!");
        dialogTable.add(dialogueBox).width(uiStage.getWidth()).height(uiStage.getHeight()/3);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = getApp().getSkin().getFont("font");
        textButtonStyle.fontColor = new Color(96f/255f, 96f/255f, 96f/255f, 1f);

        TextButton back = new TextButton("Back", textButtonStyle);
        back.getLabel().setFontScale(2,2);
        backButton.setBackground(getApp().getSkin().getDrawable("optionbox"));
        backButton.add(back).expand().align(Align.center).width(100).height(25).space(11f);

        topBar.add(backButton).align(Align.topLeft);
        topBar.add(title);

        uiStage.addActor(root);

        InventoryUI engimonInventory = new InventoryUI(getApp().getSkin(), bag.getEngimonInventory(), InventoryItem.ItemType.ENGIMON, getApp().getResourceProvider(), gameScreen.getGameState());
        InventoryUI skillitemInventory = new InventoryUI(getApp().getSkin(), bag.getSkillItemInventory(), InventoryItem.ItemType.SKILLITEM, getApp().getResourceProvider(), gameScreen.getGameState());

        Label labelA = new Label("Engimon", getApp().getSkin());
        Label labelB = new Label("SkillItem", getApp().getSkin());
        engimonInventory.add(labelA);
        skillitemInventory.add(labelB);

        inventoryWrapper.add(engimonInventory).expand().align(Align.topLeft);
        inventoryWrapper.add(skillitemInventory).expand().align(Align.topLeft).space(11f);

        TextButton engimonSort = new TextButton("Sort Engimon", textButtonStyle);
        engimonSort.getLabel().setFontScale(2,2);
        sortEngimonButton.setBackground(getApp().getSkin().getDrawable("optionbox"));
        sortEngimonButton.add(engimonSort).expand().align(Align.center).width(200).height(25).space(11f);

        TextButton skillitemSort = new TextButton("Sort SkillItem", textButtonStyle);
        skillitemSort.getLabel().setFontScale(2,2);
        sortSkillItemButton.setBackground(getApp().getSkin().getDrawable("optionbox"));
        sortSkillItemButton.add(skillitemSort).expand().align(Align.center).width(200).height(25).space(11f);

        topBar.add(sortEngimonButton).align(Align.center);
        topBar.add(sortSkillItemButton).align(Align.center).space(11f);

        root.add(topBar).top().fillX().row();
        root.add(inventoryWrapper).top().fillX().align(Align.center).row();
        root.add(bottomBar).top().fillX().align(Align.center).row();

        back.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                controller.closeInventory();
            }
        });

        engimonSort.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Sort Engimon");
            }
        });

        skillitemSort.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Sort SkillItem");
            }
        });
    }
}
