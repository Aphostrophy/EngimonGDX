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
import com.ungabunga.model.controller.OptionBoxController;
import com.ungabunga.model.controller.PlayerController;
import com.ungabunga.model.entities.*;
import com.ungabunga.model.enums.IElements;
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
    private Table root;
    private DialogueBox dialogueBox;
    private Table topBar;
    private Table breederWrapper;
    private Table breedButton;
    private Table parentLabel;
    private Table dialogTable;
    private Table backButton;
    private Table title;
    private Table spacer;



    public BreederScreen(EngimonGame app, PlayerController controller) throws IOException {
        this.app = app;
        this.controller = controller;
        batch = new SpriteBatch();

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

        ParentA = new Engimon("X", "X", "X",1, elmt, skills, parents, parents);
        ParentB = new Engimon("X", "X", "X",1, elmt2, skills, parents, parents);

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
            try {
                app.setScreen(new GameScreen(app));
            } catch (IOException e) {
                e.printStackTrace();
            }
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


        Image bg = new Image(new Texture("img/breeder_title.png"));
        title.add(bg).width(500);

        dialogueBox =  new DialogueBox(app.getSkin());
        dialogueBox.animateText("Kamu mendapatkan Engimon baru!");
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



//        uiStage.addActor(dialogTable);
//        uiStage.addActor(inventoryWrapper);
//        uiStage.addActor(parentLabel);

        uiStage.addActor(root);

        BreederEngimonUI parentA = new BreederEngimonUI(app.getSkin());
        BreederEngimonUI parentB = new BreederEngimonUI(app.getSkin());

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
                root.add(dialogTable);
                Engimon child = Breeder.Breed(ParentA, ParentB, "yolo", inventory);
                // cuma buat mastiin childnya kebreed
                System.out.println("Nama: " + child.getName());
                System.out.println("Lvl. " + child.getLevel());
                System.out.println("Species: " + child.getSpecies());
                System.out.println("Parent:");
                System.out.println("- " + child.getParentName().getFirst() + "(" + child.getParentSpecies().getSecond() + ")");
                System.out.println("- " + child.getParentName().getFirst() + "(" + child.getParentSpecies().getSecond() + ")");
                System.out.print("Skills: \n.");

                for (Skill s : child.getSkills()) {
                    System.out.println("- " + s.getSkillName());
                }
                System.out.println("heyyy");
//                System.out.println("Breeding is in progress...");
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
