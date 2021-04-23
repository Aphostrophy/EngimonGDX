package com.ungabunga.model.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ungabunga.EngimonGame;
import com.ungabunga.model.GameState;
import com.ungabunga.model.entities.Breeder;
import com.ungabunga.model.entities.Engimon;
import com.ungabunga.model.entities.Inventory;
import org.opentest4j.IncompleteExecutionException;

import java.io.IOException;

public class BreederScreen extends AbstractScreen {
    private EngimonGame app;

    private Sprite ParentABox;
    private Sprite ParentBBox;
    private Sprite BreedButtonInactive;
    private Sprite BreedButtonActive;

    private Engimon ParentA;
    private Engimon ParentB;

    private SpriteBatch batch;
    private Inventory<Engimon> inventory;

    public BreederScreen(EngimonGame app) throws IOException {
        super(app);
        batch = new SpriteBatch();
        Texture splashTexture = new Texture("img/box.png");
        this.ParentABox = new Sprite(splashTexture);
        ParentABox.setSize(250, 250);

        this.ParentBBox = new Sprite(splashTexture);
        ParentBBox.setSize(250, 250);

        splashTexture = new Texture("img/breed_inactive.png");
        this.BreedButtonInactive = new Sprite(splashTexture);
        BreedButtonInactive.setSize(200, 100);

        splashTexture = new Texture("img/breed_active.png");
        this.BreedButtonActive = new Sprite(splashTexture);
        BreedButtonActive.setSize(200, 100);

        inventory = new Inventory<Engimon>();

        Gdx.input.setInputProcessor(new InputAdapter() {

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                System.out.println(Gdx.input.getX());
                System.out.println(Gdx.input.getY());
                if (Gdx.input.getX() < 105 && Gdx.input.getX() > 25 && Gdx.graphics.getHeight() - Gdx.input.getY() < Gdx.graphics.getHeight() + 80 && Gdx.graphics.getHeight() - Gdx.input.getY() > Gdx.graphics.getHeight() - 85) {
                    System.out.println("helo");
                }

                return super.touchUp(screenX, screenY, pointer, button);
            }

        });

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
        Gdx.gl.glClearColor(0, 0, .25f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        ParentABox.setCenter(Gdx.graphics.getWidth() / 4  - 150, 3*Gdx.graphics.getHeight() / 4);
        ParentABox.draw(batch);
        ParentBBox.setCenter(3 * Gdx.graphics.getWidth() / 4 - 150, 3*Gdx.graphics.getHeight() / 4);
        ParentBBox.draw(batch);

        if (Gdx.input.getX() > Gdx.graphics.getWidth() / 3 - 50 && Gdx.input.getX() < Gdx.graphics.getWidth() / 3 + 155 && Gdx.graphics.getHeight() - Gdx.input.getY() > 5*Gdx.graphics.getHeight() / 8 + 5 && Gdx.graphics.getHeight() - Gdx.input.getY() < 5*Gdx.graphics.getHeight() / 8 + 70) {
            BreedButtonActive.setCenter(Gdx.graphics.getWidth() / 3 + 55, 5*Gdx.graphics.getHeight() / 8 + 40);
            BreedButtonActive.draw(batch);
        } else {
            BreedButtonInactive.setCenter(Gdx.graphics.getWidth() / 3 + 55, 5*Gdx.graphics.getHeight() / 8 + 40);
            BreedButtonInactive.draw(batch);
        }

        batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void resume() {

    }

    @Override
    public  void show() {

    }

    public EngimonGame getApp() {
        return app;
    }

}
