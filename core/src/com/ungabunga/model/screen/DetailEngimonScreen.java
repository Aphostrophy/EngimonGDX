package com.ungabunga.model.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ungabunga.EngimonGame;
import com.ungabunga.model.controller.PlayerController;
import com.ungabunga.model.entities.ActiveEngimon;
import com.ungabunga.model.entities.PlayerEngimon;
import com.ungabunga.model.utilities.ResourceProvider;

import java.io.IOException;

public class DetailEngimonScreen extends AbstractScreen implements Screen {
    private ResourceProvider provider;

    private Stage uiStage;

    private ActiveEngimon activeEngimon;

    private PlayerController controller;

    private GameScreen gameScreen;

    private Table root;
    private Table topBar;
    private Stack engimonPic;
    private Stack skillPic;
    private Table name;
    private Table details;
    private Table skills;
    private Table detailsWrapper;
    private Table backButton;
    private Table title;

    public DetailEngimonScreen(EngimonGame app, GameScreen gameScreen,ActiveEngimon activeEngimon, ResourceProvider provider, PlayerController controller) throws IOException {
        super(app);

        this.activeEngimon = activeEngimon;

        this.provider = provider;

        this.controller = controller;

        this.gameScreen = gameScreen;

        initUI();
    }

    public void initUI() {
        uiStage = new Stage(new ScreenViewport());

        root = new Table();
        root.setSize(uiStage.getWidth(),uiStage.getHeight());

        Image image = new Image(getApp().getSkin(), "optionbox");

        topBar = new Table();
        engimonPic = new Stack();
        skillPic = new Stack();
        name = new Table();
        details = new Table();
        skills = new Table();
        detailsWrapper = new Table();
        backButton = new Table();
        title = new Table();

        // add background
        engimonPic.add(image);
        skillPic.add(image);

//        name.setBackground("dialoguebox");
//        details.setBackground("dialoguebox");
//        skills.setBackground("dialoguebox");
//        detailsWrapper.setBackground("dialoguebox");

        Image engimonImage = new Image(provider.getSprite((PlayerEngimon) activeEngimon));
        engimonPic.add(engimonImage);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = getApp().getSkin().getFont("font");
        textButtonStyle.fontColor = new Color(96f/255f, 96f/255f, 96f/255f, 1f);

        Image bg = new Image(new Texture("img/inventory_title.png"));
        title.add(bg).width(500);

        TextButton nameText = new TextButton(this.activeEngimon.getName(), textButtonStyle);
        nameText.getLabel().setFontScale(1, 1);
        name.setBackground(getApp().getSkin().getDrawable("dialoguebox"));
        name.add(nameText).expand().align(Align.center).width(400).height(25).space(11f);

        TextButton detailsText = new TextButton(this.activeEngimon.displayInfoToString(), textButtonStyle);
        detailsText.getLabel().setFontScale(1, 1);
        details.setBackground(getApp().getSkin().getDrawable("dialoguebox"));
        details.add(detailsText).expand().align(Align.center).width(400).height(200).space(11f);

        skills.setBackground(getApp().getSkin().getDrawable("dialoguebox"));

        for(int i = 0; i < activeEngimon.getSkills().size(); i++) {
            Table skillWrapper = new Table();

            Label masteryLevelLabel = new Label(String.valueOf(activeEngimon.getSkills().get(i).getMasteryLevel()), getApp().getSkin());
            masteryLevelLabel.setAlignment(Align.bottomRight);
            masteryLevelLabel.setColor(1, 0, 0, 1);
            masteryLevelLabel.setFontScale(2);

            Image skillImage = new Image(provider.getSprite(activeEngimon.getSkills().get(i)));

            skillPic.add(skillImage);
            skillPic.add(masteryLevelLabel);

            TextButton skillDetailsText = new TextButton(activeEngimon.getSkills().get(i).displaySkillInfoDetailString(), textButtonStyle);
            skillDetailsText.getLabel().setFontScale(1, 1);

            skillWrapper.add(skillPic).size(72, 72);
            skillWrapper.add(skillDetailsText).expand().align(Align.center).width(100).height(50).space(11f);

            skills.add(skillWrapper).expand().width(100).height(50).pad(5f);
        }

        TextButton back = new TextButton("Back", textButtonStyle);
        back.getLabel().setFontScale(2,2);
        backButton.setBackground(getApp().getSkin().getDrawable("optionbox"));
        backButton.add(back).expand().align(Align.center).width(100).height(25).space(11f);

        topBar.add(backButton).align(Align.topLeft);
        topBar.add(title).align(Align.center);

        detailsWrapper.add(name).top().align(Align.center).row();
        detailsWrapper.add(details).top().align(Align.center).row();
        detailsWrapper.add(skills).width(400).height(200).top().align(Align.center).row();

        root.add(topBar).top().fillX().row();
        root.add(engimonPic).left();
        root.add(detailsWrapper).right();
        root.row();

        uiStage.setDebugAll(true);

        uiStage.addActor(root);

        back.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                controller.closeDetail();
            }
        });
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

        if (!controller.isDetailOpen) {
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
}
