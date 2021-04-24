package com.ungabunga.model.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.ungabunga.model.GameState;
import com.ungabunga.model.entities.PlayerEngimon;
import com.ungabunga.model.enums.AVATAR_STATE;
import com.ungabunga.model.enums.DIRECTION;
import com.ungabunga.model.save.Save;
import com.ungabunga.model.screen.BreederScreen;
import java.io.IOException;

public class PlayerController extends InputAdapter{
    private GameState gameState;
    private DIRECTION direction;
    private AVATAR_STATE state;
    public boolean isInventoryOpen;
    public boolean isBreederOpen;

    public PlayerController(GameState gameState) {
        this.gameState = gameState;
        this.isInventoryOpen = false;
        this.isBreederOpen = false;
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.I) {
            isInventoryOpen = !isInventoryOpen;
        }
        if(keycode == Input.Keys.B) {
            isBreederOpen = !isBreederOpen;
        }
        if(isInventoryOpen) {
            return false;
        }
        if (isBreederOpen) {
            return false;
        }
        if(keycode == Keys.W) {
            direction = DIRECTION.UP;
            state = AVATAR_STATE.WALKING;
        }
        if(keycode == Keys.S) {
            direction = DIRECTION.DOWN;
            state = AVATAR_STATE.WALKING;
        }
        if(keycode == Keys.A) {
            direction = DIRECTION.LEFT;
            state = AVATAR_STATE.WALKING;
        }
        if(keycode == Keys.D) {
            direction = DIRECTION.RIGHT;
            state = AVATAR_STATE.WALKING;
        }
        if(keycode == Keys.R){
            gameState.removePlayerEngimon();
        }
        if(keycode == Keys.P){
            if(gameState.player.getActiveEngimon()==null){
                try{
                    System.out.println("Active Engimon spawned");
                    gameState.spawnActiveEngimon(new PlayerEngimon(gameState.app.getResourceProvider().getEngimon("Blastoise")));
                } catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
        if(keycode == Keys.F5){
            Json json = new Json();
            json.setOutputType(JsonWriter.OutputType.json);
            FileHandle file = Gdx.files.local("mysave.json");
            file.writeString(json.toJson(new Save(gameState)), false);
        }
        if(keycode == Keys.F6){
            FileHandle file = Gdx.files.local("mysave.json");
            String mysave = file.readString();
            Json json = new Json();
            Save save = json.fromJson(Save.class, mysave);
            gameState.loadSave(save);
        }
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (Gdx.input.getX() < 220 && Gdx.input.getX() > 130 && Gdx.graphics.getHeight() - Gdx.input.getY() < Gdx.graphics.getHeight() + 80 && Gdx.graphics.getHeight() - Gdx.input.getY() > Gdx.graphics.getHeight() - 85) {
            isInventoryOpen = !isInventoryOpen;
        }

        if (Gdx.input.getX() < 105 && Gdx.input.getX() > 25 && Gdx.graphics.getHeight() - Gdx.input.getY() < Gdx.graphics.getHeight() + 80 && Gdx.graphics.getHeight() - Gdx.input.getY() > Gdx.graphics.getHeight() - 85) {
            isBreederOpen = !isBreederOpen;

        }
        return super.touchUp(screenX, screenY, pointer, button);
    }

    public void finishBreeding() {
        isBreederOpen = !isBreederOpen;
    }

    @Override
    public boolean keyUp(int keycode){
        if(keycode == Keys.W) {
            state = AVATAR_STATE.STANDING;
        }
        if(keycode == Keys.S) {
            state = AVATAR_STATE.STANDING;
        }
        if(keycode == Keys.A) {
            state = AVATAR_STATE.STANDING;
        }
        if(keycode == Keys.D) {
            state = AVATAR_STATE.STANDING;
        }
        return false;
    }

    public void update(float delta){
        if(direction == DIRECTION.UP && state!=AVATAR_STATE.STANDING){
            try{
                gameState.movePlayerUp();
            } catch(Exception e){
                System.out.println(e);
            }
        }
        else if(direction == DIRECTION.DOWN && state!=AVATAR_STATE.STANDING){
            try{
                gameState.movePlayerDown();
            } catch(Exception e){
                System.out.println(e);
            }
        }
        else if(direction == DIRECTION.LEFT && state!=AVATAR_STATE.STANDING){
            try{
                gameState.movePlayerLeft();
            } catch(Exception e){
                System.out.println(e);
            }
        }
        else if(direction == DIRECTION.RIGHT && state!=AVATAR_STATE.STANDING){
            try{
                gameState.movePlayerRight();
            } catch(Exception e){
                System.out.println(e);
            }
        }
    }
}
