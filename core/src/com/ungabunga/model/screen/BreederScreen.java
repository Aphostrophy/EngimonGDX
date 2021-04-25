package com.ungabunga.model.screen;

import com.badlogic.gdx.*;
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
import com.ungabunga.model.controller.DialogueController;
import com.ungabunga.model.controller.PlayerController;
import com.ungabunga.model.entities.*;
import com.ungabunga.model.enums.IElements;
import com.ungabunga.model.exceptions.FullInventoryException;
import com.ungabunga.model.exceptions.NoParentException;
import com.ungabunga.model.exceptions.SameParentException;
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

    private GameState gameState;
    private final GameScreen gameScreen;

    private InputMultiplexer multiplexer;
    private PlayerController controller;
    private DialogueController dialogueController;

    private Stage uiStage;
    private Stage dialogueStage;

    private DialogueBox dialogueBox;
    private OptionBox optionBox;

    private Table root;
    private Table topBar;
    private Table parentBar;
    private Table breederWrapper;
    private Table breedButton;
    private Table dialogueTable;
    private Table backButton;
    private Table title;
    private Table parentABox;
    private Table parentBBox;
    private Table parentWrapper;
    private Table parentAButton;
    private Table parentBButton;


    private BreederEngimonUI ParentA;
    private BreederEngimonUI ParentB;
    private BreederEngimonUI breedableEngimon;

    private boolean isBreeding;
    private boolean parentA;
    private boolean parentB;
    private boolean parentASprite;
    private boolean parentBSprite;




    public BreederScreen(EngimonGame app, PlayerController controller, GameState gameState, GameScreen gameScreen) throws IOException {
        this.app = app;
        this.controller = controller;
        this.gameState = gameState;

        this.gameScreen = gameScreen;

        this.isBreeding = false;

        this.parentA = false;
        this.parentB = false;

        this.parentASprite = false;
        this.parentBSprite = false;

        initUI();

        multiplexer = new InputMultiplexer();
        dialogueController = new DialogueController(dialogueBox,optionBox,gameScreen);

        multiplexer.addProcessor(0, controller);
        multiplexer.addProcessor(1, dialogueController);
        multiplexer.addProcessor(2, uiStage);
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
        uiStage.act(delta);
    }

    @Override
    public void render(float delta) {
        controller.update(delta);
        dialogueController.update(delta);

        Gdx.gl.glClearColor(0.50f, 0.79f, 0.61f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (!controller.isBreederOpen) {
            app.setScreen(gameScreen);
        }

        if (parentA) {
            parentAButton.setColor(1, 1, 0, 1);
        } else {
            parentAButton.setColor(1, 1, 1, 1);
        }

        if (parentB) {
            parentBButton.setColor(1, 1, 0, 1);
        } else {
            parentBButton.setColor(1, 1, 1, 1);
        }

        if (breedableEngimon.parentAFilled()) {
            parentABox.clearChildren();
            Image sprite = new Image(app.getResourceProvider().getSprite((PlayerEngimon) gameState.getPlayerInventory().getEngimonInventory().getItemByIndex(breedableEngimon.getParentAIdx())));
            parentABox.add(sprite).width(150).height(150);

        }

        if (breedableEngimon.parentBFilled()) {
            parentBBox.clearChildren();
            Image sprite = new Image(app.getResourceProvider().getSprite((PlayerEngimon) gameState.getPlayerInventory().getEngimonInventory().getItemByIndex(breedableEngimon.getParentBIdx())));
            parentBBox.add(sprite).width(150).height(150);;
        }

        if (isBreeding) {
            try {
                breedableEngimon.parentStatus();
                breedableEngimon.parentInfo();
                System.out.println(breedableEngimon.isParentFilled());
                if (breedableEngimon.isParentFilled()) {
                    if (!breedableEngimon.isParentSame()) {
                        if (gameState.getPlayerInventory().getCurrBagCapacity() < 2) {
                            ChildEngimonScreen childEngimonScreen = new ChildEngimonScreen(app, controller, breedableEngimon.getParentA(), breedableEngimon.getParentB(), gameScreen, gameState);
                            app.setScreen(childEngimonScreen);
                            stopBreeding();
                        } else {
                            throw new FullInventoryException("Inventory is full!");
                        }
                    } else {
                        throw new SameParentException("Please choose two different Engimons!");
                    }
                } else {
                    throw new NoParentException("Please choose two Engimons to breed!");
                }
            } catch (Exception e) {
                System.out.println("Exception catched");
                dialogueController.startExceptionDialogue(e);
                ParentA.resetParent();
                ParentB.resetParent();
                stopBreeding();
            }
        }

        uiStage.act(delta);
        uiStage.draw();

        dialogueStage.act(delta);
        dialogueStage.draw();


    }

    private void initUI() {
        uiStage = new Stage(new ScreenViewport());
        dialogueStage = new Stage(new ScreenViewport());
        dialogueStage.getViewport().update(Gdx.graphics.getWidth()/2,Gdx.graphics.getWidth()/2);;

        root = new Table();
        root.setSize(uiStage.getWidth(),uiStage.getHeight());

        topBar = new Table();
        parentBar = new Table();
        breederWrapper = new Table();
        dialogueTable = new Table();

        breedButton = new Table();
        backButton = new Table();
        parentAButton = new Table();
        parentBButton = new Table();
        title = new Table();

        parentABox = new Table();
        parentBBox = new Table();
        parentWrapper = new Table();

        dialogueBox =  new DialogueBox(app.getSkin());
        dialogueBox.setVisible(false);

        optionBox = new OptionBox(app.getSkin());
        optionBox.setVisible(false);

        dialogueTable.add(dialogueBox).bottom().fillX();
        dialogueTable.add(optionBox).bottom().fillX();
        dialogueTable.setSize(uiStage.getWidth(),uiStage.getHeight());
        dialogueStage.addActor(dialogueTable);


        Image bg = new Image(new Texture("img/breeder_title.png"));
        title.add(bg).width(500);


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

        TextButton parentA = new TextButton("Parent A", textButtonStyle);
        parentA.getLabel().setFontScale(2, 2);
        TextButton parentB = new TextButton("Parent B", textButtonStyle);
        parentB.getLabel().setFontScale(2, 2);

        parentAButton.setBackground(app.getSkin().getDrawable("optionbox"));
        parentAButton.add(parentA).expand().align(Align.center).width(100).height(25).space(11f);
        parentBButton.add(parentB).expand().align(Align.center).width(100).height(25).space(11f);
        parentBButton.setBackground(app.getSkin().getDrawable("optionbox"));
        parentBar.add(parentAButton);
        parentBar.add(parentBButton);

        topBar.add(backButton).align(Align.topLeft);
        topBar.add(title);

        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.font = app.getSkin().getFont("font");
        textFieldStyle.fontColor = new Color(96f/255f, 96f/255f, 96f/255f, 1f);

        uiStage.addActor(root);

        breedableEngimon = new BreederEngimonUI(app.getSkin(), gameState, app.getResourceProvider());

        ParentA = new BreederEngimonUI(app.getSkin(), gameState, app.getResourceProvider());
        ParentB = new BreederEngimonUI(app.getSkin(), gameState, app.getResourceProvider());

        Label labelA = new Label("Parent A", app.getSkin());
        Label labelB = new Label("Parent B", app.getSkin());
        Label labelC = new Label("Breedable Engimons", app.getSkin());
        ParentA.add(labelA);
        ParentB.add(labelB);
        breedableEngimon.add(labelC);

        parentABox.setBackground(app.getSkin().getDrawable("optionbox"));
        parentBBox.setBackground(app.getSkin().getDrawable("optionbox"));
        parentWrapper.add(parentABox).width(200).height(200).row();
        parentWrapper.add(parentBBox).width(200).height(200).row();

        breederWrapper.add(breedableEngimon).align(Align.topLeft);
//        breederWrapper.add(ParentA).expand().align(Align.topLeft);
        breederWrapper.add(breedButton).expand().align(Align.center).width(250).height(75).space(11f);
//        breederWrapper.add(ParentB).expand().align(Align.topLeft).space(11f);

        root.add(topBar).top().fillX().row();
        root.add(parentBar).top().fillX().row();
        root.add(breederWrapper).top().align(Align.center);
        root.add(parentWrapper);
        breed.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                startBreeding();
            }
        });

        parentAButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                breedableEngimon.parentA();
                selectA();
            }
        });

        parentBButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                breedableEngimon.parentB();
                selectB();
            }
        });

        back.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
               controller.finishBreeding();
            }
        });

    }
    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void resume() {

    }

    public void startBreeding() {
        isBreeding = true;
    }

    public void stopBreeding() {
        isBreeding = false;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void selectA() {
        this.parentA = true;
        this.parentB = false;
    }


    public void selectB() {
        this.parentB = true;
        this.parentA = false;
    }
    @Override
    public  void show() {
        Gdx.input.setInputProcessor(multiplexer);
    }

}
