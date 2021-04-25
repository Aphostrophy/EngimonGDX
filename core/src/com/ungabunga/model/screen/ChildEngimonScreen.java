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
import com.ungabunga.model.ui.*;
import com.ungabunga.model.utilities.Pair;
import org.lwjgl.Sys;
import org.opentest4j.IncompleteExecutionException;
import org.w3c.dom.Text;

import javax.swing.event.ChangeListener;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ChildEngimonScreen implements Screen {
    private EngimonGame app;

    private PlayerController controller;
    private GameState gameState;
    private final GameScreen gameScreen;

    private Stage uiStage;
    private Bag bag;

    private Engimon ParentA;
    private Engimon ParentB;
    private Engimon child;

    private Table root;
    private Table topBar;
    private Table childBox;
    private Table childWrapper;
    private Table nameWrapper;

    private Table backButton;
    private Table title;
    private Table enterButton;

    private TextField childName;


    public ChildEngimonScreen(EngimonGame app, PlayerController controller, Engimon ParentA, Engimon ParentB, GameScreen gameScreen, GameState gameState) throws IOException {
        this.app = app;
        this.controller = controller;
        this.gameState = gameState;
        this.gameScreen = gameScreen;

        this.ParentA = ParentA;
        this.ParentB = ParentB;

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

        backButton = new Table();

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = app.getSkin().getFont("font");
        textButtonStyle.fontColor = new Color(96f/255f, 96f/255f, 96f/255f, 1f);

        enterButton = new Table();

        TextButton enter = new TextButton("Enter", textButtonStyle);
        enter.getLabel().setFontScale(2,2);
        enterButton.setBackground(app.getSkin().getDrawable("optionbox"));
        enterButton.add(enter).expand().align(Align.center).width(100).height(25).space(11f);

        title = new Table();
        Image bg = new Image(new Texture("img/new_engimon_title.png"));
        title.add(bg).width(500);

        topBar = new Table();
        topBar.add(backButton).align(Align.topLeft);
        topBar.add(title);

        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.font = app.getSkin().getFont("font");
        textFieldStyle.fontColor = new Color(96f/255f, 96f/255f, 96f/255f, 1f);

        childName = new TextField("", textFieldStyle);
        childName.setPosition(24,73);
        childName.setSize(88, 14);

        nameWrapper = new Table();
        nameWrapper.setBackground(app.getSkin().getDrawable("dialoguebox"));
        nameWrapper.add(childName).width(uiStage.getWidth()/2).height(200).space(11f);

        root.add(topBar).top().fillX().row();
        root.add(nameWrapper);
        root.add(enter);


        enter.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {

                Engimon child = Breeder.Breed(ParentA, ParentB, childName.getText(), gameState);
                controller.finishBreeding();
            }
        });
        uiStage.addActor(root);

    }

    public GameState getNewGameState() {
        return gameState;
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
