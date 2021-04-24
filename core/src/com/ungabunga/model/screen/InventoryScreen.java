package com.ungabunga.model.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ungabunga.EngimonGame;
import com.ungabunga.model.controller.PlayerController;
import com.ungabunga.model.ui.DialogueBox;
import com.ungabunga.model.ui.InventoryUI;

import java.io.IOException;

public class InventoryScreen implements Screen {
    private EngimonGame app;

    private PlayerController controller;

    private Stage uiStage;

    private Table root;
    private DialogueBox dialogueBox;
    private Table topBar;
    private Table inventoryWrapper;
    private Table dialogTable;
    private Table backButton;
    private Table title;

    private InventoryUI inventoryUI;

    public InventoryScreen(EngimonGame app, PlayerController controller) throws IOException {
        this.app = app;
        this.controller = controller;
        this.inventoryUI = new InventoryUI(app.getSkin());
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
            try {
                app.setScreen(new GameScreen(app));
            } catch (IOException e) {
                e.printStackTrace();
            }
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
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    private void initUI() {
        uiStage = new Stage(new ScreenViewport());

        root = new Table();
        root.setSize(uiStage.getWidth(),uiStage.getHeight());

        topBar = new Table();
        inventoryWrapper = new Table();
        dialogTable = new Table();
        backButton = new Table();
        title = new Table();


        Image bg = new Image(new Texture("img/breeder_title.png"));
        title.add(bg).width(500);

        dialogueBox =  new DialogueBox(app.getSkin());
        dialogueBox.animateText("Kamu mendapatkan Engimon baru!");
        dialogTable.add(dialogueBox).width(uiStage.getWidth()).height(uiStage.getHeight()/3);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = app.getSkin().getFont("font");
        textButtonStyle.fontColor = new Color(96f/255f, 96f/255f, 96f/255f, 1f);

        TextButton back = new TextButton("Back", textButtonStyle);
        back.getLabel().setFontScale(2,2);
        backButton.setBackground(app.getSkin().getDrawable("optionbox"));
        backButton.add(back).expand().align(Align.center).width(100).height(25).space(11f);

        topBar.add(backButton).align(Align.topLeft);
        topBar.add(title);

        uiStage.addActor(root);

        inventoryWrapper.add(inventoryUI).expand().align(Align.center);

        root.add(topBar).top().fillX().row();
        root.add(inventoryWrapper).top().align(Align.center).row();

        back.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                controller.closeInventory();
            }
        });
    }
}
