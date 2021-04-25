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
import com.ungabunga.model.controller.DialogueController;
import com.ungabunga.model.controller.PlayerController;
import com.ungabunga.model.entities.Bag;
import com.ungabunga.model.entities.Player;
import com.ungabunga.model.ui.DialogueBox;
import com.ungabunga.model.ui.InventoryItem;
import com.ungabunga.model.ui.InventoryUI;
import com.ungabunga.model.ui.OptionBox;

import java.io.IOException;


public class InventoryScreen extends AbstractScreen implements Screen {

    private GameScreen gameScreen;

    private PlayerController controller;

    public DialogueController dialogueController;

    private Stage uiStage;

    private Bag bag;

    private Table root;
    private DialogueBox dialogueBox;
    private Table topBar;
    private Table inventoryWrapper;
    private Table dialogTable;
    private Table backButton;
    private Table bottomBar;
    private Table deleteButton;
    private Table detailButton;
    private Table plusButton;
    private Table minusButton;
    private Table amountUI;
    private Table title;

    private InventoryUI engimonInventory;
    private InventoryUI skillitemInventory;

    private String message;
    private Integer amount = 0;
    private boolean isDelete;
    private boolean isDetail;

    public InventoryScreen(EngimonGame app, PlayerController controller, Bag bag, GameScreen gameScreen) throws IOException {
        super(app);
        this.controller = controller;
        this.bag = bag;
        this.gameScreen = gameScreen;
        this.isDelete = false;
        this.isDetail = false;

        OptionBox optionBox = new OptionBox(getApp().getSkin());
        optionBox.setVisible(false);

        initUI();
        this.dialogueController = new DialogueController(dialogueBox, optionBox);
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

        dialogueController.update(delta);

        if (!controller.isInventoryOpen) {
            getApp().setScreen(gameScreen);
            this.dispose();
        }

        if(isDelete) {
            deleteButton.setColor(1, 0, 0, 1);
            amountUI.setVisible(true);
            plusButton.setVisible(true);
            minusButton.setVisible(true);
        } else {
            deleteButton.setColor(1, 1, 1, 1);;
            amountUI.setVisible(false);
            plusButton.setVisible(false);
            minusButton.setVisible(false);
        }

        if(isDetail) {
            detailButton.setColor(1, 1, 0, 1);
        } else {
            detailButton.setColor(1, 1, 1, 1);
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
        deleteButton = new Table();
        detailButton = new Table();
        plusButton = new Table();
        amountUI = new Table();
        minusButton = new Table();
        title = new Table();

        Image bg = new Image(new Texture("img/inventory_title.png"));
        title.add(bg).width(500);

        dialogueBox =  new DialogueBox(getApp().getSkin());
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

        engimonInventory = new InventoryUI(getApp().getSkin(), bag.getEngimonInventory(), InventoryItem.ItemType.ENGIMON, getApp().getResourceProvider(), gameScreen.getGameState(),this);
        skillitemInventory = new InventoryUI(getApp().getSkin(), bag.getSkillItemInventory(), InventoryItem.ItemType.SKILLITEM, getApp().getResourceProvider(), gameScreen.getGameState(), this);

        Label labelA = new Label("Engimon", getApp().getSkin());
        Label labelB = new Label("SkillItem", getApp().getSkin());
        engimonInventory.add(labelA);
        skillitemInventory.add(labelB);

        inventoryWrapper.add(engimonInventory).expand().align(Align.topLeft);
        inventoryWrapper.add(skillitemInventory).expand().align(Align.topLeft).space(11f);

        TextButton delete = new TextButton("Delete", textButtonStyle);
        delete.getLabel().setFontScale(2,2);
        deleteButton.setBackground(getApp().getSkin().getDrawable("optionbox"));
        deleteButton.add(delete).expand().align(Align.center).width(200).height(25).space(11f);

        TextButton detail = new TextButton("Detail", textButtonStyle);
        detail.getLabel().setFontScale(2,2);
        detailButton.setBackground(getApp().getSkin().getDrawable("optionbox"));
        detailButton.add(detail).expand().align(Align.center).width(200).height(25).space(11f);

        TextButton plus = new TextButton("+", textButtonStyle);
        plus.getLabel().setFontScale(2,2);
        plusButton.setBackground(getApp().getSkin().getDrawable("optionbox"));
        plusButton.add(plus).expand().align(Align.center).width(25).height(25).space(11f);

        TextButton amountUIButton = new TextButton(this.amount.toString(), textButtonStyle);
        amountUIButton.getLabel().setFontScale(2,2);
        amountUI.setBackground(getApp().getSkin().getDrawable("dialoguebox"));
        amountUI.add(amountUIButton).expand().align(Align.center).width(25).height(25).space(11f);

        TextButton minus = new TextButton("-", textButtonStyle);
        minus.getLabel().setFontScale(2,2);
        minusButton.setBackground(getApp().getSkin().getDrawable("optionbox"));
        minusButton.add(minus).expand().align(Align.center).width(25).height(25).space(11f);

        topBar.add(deleteButton).align(Align.center);
        topBar.add(detailButton).align(Align.center).space(11f);
        topBar.add(minusButton).align(Align.center).space(11f);
        topBar.add(amountUI).align(Align.center).space(11f);
        topBar.add(plusButton).align(Align.center).space(11f);

        root.add(topBar).top().fillX().row();
        root.add(inventoryWrapper).top().fillX().align(Align.center).row();
        root.add(bottomBar).top().fillX().align(Align.center).row();
        root.add(dialogueBox).top().fillX().align(Align.bottom).row();

        back.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                controller.closeInventory();
            }
        });

        delete.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                isDelete = !isDelete;
                engimonInventory.setDelete(isDelete);
                skillitemInventory.setDelete(isDelete);
            }
        });

        detail.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                isDetail = !isDetail;
                engimonInventory.setDetail(isDetail);
                skillitemInventory.setDetail(isDetail);
            }
        });

        plus.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                amount++;
                amountUIButton.setText(amount.toString());
                skillitemInventory.setAmount(amount);
            }
        });

        minus.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if(amount > 0) {
                    amount--;
                    amountUIButton.setText(amount.toString());
                    skillitemInventory.setAmount(amount);
                }
            }
        });
    }
}
