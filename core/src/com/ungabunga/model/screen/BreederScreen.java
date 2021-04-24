package com.ungabunga.model.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.ungabunga.EngimonGame;
import com.ungabunga.model.GameState;
import com.ungabunga.model.controller.PlayerController;
import com.ungabunga.model.entities.*;
import com.ungabunga.model.enums.IElements;
import com.ungabunga.model.exceptions.FullInventoryException;
import com.ungabunga.model.ui.*;
import com.ungabunga.model.utilities.Pair;
import org.lwjgl.Sys;
import org.opentest4j.IncompleteExecutionException;

import javax.swing.event.ChangeListener;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class BreederScreen implements Screen {
    private EngimonGame app;

    private final GameScreen gameScreen;

    private PlayerController controller;

    private Sprite ParentABox;
    private Sprite ParentBBox;
    private Sprite BreedButtonInactive;
    private Sprite BreedButtonActive;

    private Engimon ParentA;
    private Engimon ParentB;

    private SpriteBatch batch;
    private Inventory<Engimon> inventory;


    private Stage uiStage;

    private DialogueBox dialogueBox;

    private TextField childName;

    private Table root;
    private Table topBar;
    private Table breederWrapper;
    private Table breedButton;
    private Table nameWrapper;
    private Table parentLabel;
    private Table dialogTable;
    private Table backButton;
    private Table title;
    private Table spacer;



    public BreederScreen(EngimonGame app, PlayerController controller,GameScreen gameScreen) throws IOException {
        this.app = app;
        this.controller = controller;
        batch = new SpriteBatch();

        this.gameScreen = gameScreen;

//        Texture splashTexture = new Texture("img/box.png");
//        this.ParentABox = new Sprite(splashTexture);
//        ParentABox.setSize(250, 250);
//
//        this.ParentBBox = new Sprite(splashTexture);
//        ParentBBox.setSize(250, 250);
//
//        splashTexture = new Texture("img/breed_inactive.png");
//        this.BreedButtonInactive = new Sprite(splashTexture);
//        BreedButtonInactive.setSize(200, 100);
//
//        splashTexture = new Texture("img/breed_active.png");
//        this.BreedButtonActive = new Sprite(splashTexture);
//        BreedButtonActive.setSize(200, 100);

        inventory = new Inventory<Engimon>();
        ArrayList<IElements> elmt = new ArrayList<IElements>();
        elmt.add(IElements.FIRE);
        ArrayList<IElements> elmt2 = new ArrayList<IElements>();
        elmt2.add(IElements.ELECTRIC);
        ArrayList<Skill> skills = new ArrayList<Skill>();
        Pair<String, String> parents = new Pair<String, String>("A", "B");

        Engimon a = new Engimon("Test", "X", "X",100, elmt, skills, parents, parents);
        Engimon b = new Engimon("Hola", "X", "X",100, elmt2, skills, parents, parents);

//        ParentA = new Engimon("Test", "X", "X",100, elmt, skills, parents, parents);
//        ParentB = new Engimon("Hola", "X", "X",100, elmt2, skills, parents, parents);
        try {
            inventory.insertToInventory(a, inventory.getFilledSlot());
            inventory.insertToInventory(b, inventory.getFilledSlot());
        } catch (FullInventoryException e) {
            controller.finishBreeding();
        }

        initUI();
    }

    @Override
    public void dispose() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    public void update(float delta) {

    }

    @Override
    public void render(float delta) {
        controller.update(delta);
        Gdx.gl.glClearColor(0.50f, 0.79f, 0.61f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (!controller.isBreederOpen) {
            app.setScreen(gameScreen);
        }

        uiStage.act(delta);
        uiStage.draw();

    }

    private void initUI() {
        uiStage = new Stage(new ScreenViewport());

        root = new Table();
        root.setSize(uiStage.getWidth(),uiStage.getHeight());

        topBar = new Table();
        breederWrapper = new Table();
        dialogTable = new Table();
        parentLabel = new Table();
        breedButton = new Table();
        backButton = new Table();
        title = new Table();
        nameWrapper = new Table();

        Image bg = new Image(new Texture("img/breeder_title.png"));
        title.add(bg).width(500);

        dialogueBox =  new DialogueBox(app.getSkin());
        dialogueBox.animateText("Pilih dua Engimon untuk dibreed!");
        dialogTable.add(dialogueBox).width(uiStage.getWidth()).height(uiStage.getHeight()/3);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = app.getSkin().getFont("font");
        textButtonStyle.fontColor = new Color(96f/255f, 96f/255f, 96f/255f, 1f);
        TextButton breed = new TextButton("Breed Engimon", textButtonStyle);
        breed.getLabel().setFontScale(3, 3);

        breedButton.setBackground(app.getSkin().getDrawable("optionbox"));
        breedButton.add(breed);

        TextButton back = new TextButton("Back", textButtonStyle);
        back.getLabel().setFontScale(2,2);
        backButton.setBackground(app.getSkin().getDrawable("optionbox"));
        backButton.add(back).expand().align(Align.center).width(100).height(25).space(11f);

        topBar.add(backButton).align(Align.topLeft);
        topBar.add(title);

        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.font = app.getSkin().getFont("font");
        textFieldStyle.fontColor = new Color(96f/255f, 96f/255f, 96f/255f, 1f);

        childName = new TextField(" ", textFieldStyle);
        childName.setPosition(24,73);
        childName.setSize(88, 14);
        nameWrapper.setBackground(app.getSkin().getDrawable("dialoguebox"));
        nameWrapper.add(childName).width(uiStage.getWidth()).height(uiStage.getHeight()/3).space(11f);

//        uiStage.addActor(dialogTable);
//        uiStage.addActor(inventoryWrapper);
//        uiStage.addActor(parentLabel);

        uiStage.addActor(root);

        BreederEngimonUI parentA = new BreederEngimonUI(app.getSkin(), inventory);
        BreederEngimonUI parentB = new BreederEngimonUI(app.getSkin(), inventory);

        Label labelA = new Label("Parent A", app.getSkin());
        Label labelB = new Label("Parent B", app.getSkin());
        parentA.add(labelA);
        parentB.add(labelB);

        breederWrapper.add(parentA).expand().align(Align.topLeft);
        breederWrapper.add(breedButton).expand().align(Align.center).width(250).height(75).space(11f);
        breederWrapper.add(parentB).expand().align(Align.topLeft).space(11f);

        root.add(topBar).top().fillX().row();
        root.add(breederWrapper).top().align(Align.center).row();
        breed.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (parentA.isParentFilled() && parentB.isParentFilled()) {
                    try {
                        app.setScreen(new ChildEngimonScreen(app, controller, parentA.getParentEngimon(), parentB.getParentEngimon(), inventory));
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                } else {
                    root.add(dialogTable);
                }
            }
        });

        back.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
               controller.finishBreeding();
            }
        });

//        root.row();
//        root.add(inventoryUI).expand().align(Align.center).pad(5f);
//        root.add(dialogueBox).expand().align(Align.bottom).pad(8f);

//        TextButton breed = new TextButton("                Breed                ", getApp().getSkin(), "optionbox");
//        ParentASlot = new ParentSlot(getApp().getSkin());
//        ParentBSlot = new ParentSlot(getApp().getSkin());
//        breederWrapper.add(ParentASlot).size(300, 300).expand().align(Align.topLeft);
//        breederWrapper.add(breed).size(300, 300).expand().align(Align.center);
//        breederWrapper.add(ParentBSlot).size(300, 300).expand().align(Align.topLeft).space(8f);

    }
    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void resume() {

    }

    @Override
    public  void show() {
        Gdx.input.setInputProcessor(uiStage);
    }

}
