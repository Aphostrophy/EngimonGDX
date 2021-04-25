package com.ungabunga.model.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ungabunga.model.GameState;
import com.ungabunga.model.dialogue.Dialogue;
import com.ungabunga.model.dialogue.DialogueNode;
import com.ungabunga.model.entities.*;
import com.ungabunga.model.enums.AVATAR_MODE;
import com.ungabunga.model.enums.AVATAR_STATE;
import com.ungabunga.model.enums.DIRECTION;
import com.ungabunga.model.save.Save;
import com.ungabunga.model.screen.BreederScreen;
import com.ungabunga.model.screen.GameScreen;
import com.ungabunga.model.ui.DialogueBox;
import com.ungabunga.model.utilities.Pair;

import java.io.IOException;
import java.util.ArrayList;

public class PlayerController extends InputAdapter{
    private GameScreen gameScreen;
    private GameState gameState;
    private DIRECTION direction;
    private AVATAR_STATE state;
    public boolean isInventoryOpen;
    public boolean isBreederOpen;

    public PlayerController(GameState gameState, GameScreen gameScreen) {
        this.gameState = gameState;
        this.isInventoryOpen = false;
        this.isBreederOpen = false;
        this.gameScreen = gameScreen;
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.I) {
            isInventoryOpen = !isInventoryOpen;
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
        if(keycode == Keys.SHIFT_LEFT  && gameState.player.currMode == AVATAR_MODE.WALKING) {
            gameState.player.nextMode = AVATAR_MODE.RUNNING;
        }
        if(keycode == Keys.R){
            gameState.removePlayerEngimon();
        }
        if (keycode == Keys.B) {
           battleHandler();
        }
        if(keycode == Keys.X){
            instantKill();
        }
        if(keycode == Keys.H){
            gameScreen.dialogueController.startTutorialDialogue();
        }
        if(keycode == Keys.M){
            gameState.player.setPosition(gameState.map.length()/2,gameState.map.get(0).length()/2);
            gameState.removePlayerEngimon();
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

    public void closeInventory() {
        isInventoryOpen = !isInventoryOpen;
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
        if (keycode == Keys.SHIFT_LEFT && gameState.player.currMode == AVATAR_MODE.RUNNING) {
            gameState.player.nextMode = AVATAR_MODE.WALKING;
        }
        return false;
    }

    public void update(float delta){
        if(direction == DIRECTION.UP && state!=AVATAR_STATE.STANDING){
            try{
                gameState.movePlayerUp();
            } catch(Exception e){
                gameScreen.dialogueController.startExceptionDialogue(e);
            }
        }
        else if(direction == DIRECTION.DOWN && state!=AVATAR_STATE.STANDING){
            try{
                gameState.movePlayerDown();
            } catch(Exception e){
                gameScreen.dialogueController.startExceptionDialogue(e);
            }
        }
        else if(direction == DIRECTION.LEFT && state!=AVATAR_STATE.STANDING){
            try{
                gameState.movePlayerLeft();
            } catch(Exception e){
                gameScreen.dialogueController.startExceptionDialogue(e);
            }
        }
        else if(direction == DIRECTION.RIGHT && state!=AVATAR_STATE.STANDING){
            try{
                gameState.movePlayerRight();
            } catch(Exception e){
                gameScreen.dialogueController.startExceptionDialogue(e);
            }
        }
    }

    public void battleHandler(){
        if(this.gameState.player.getActiveEngimon()!=null){
            Pair<Integer,Integer> dir = new Pair<>(0,0);
            DIRECTION d = this.gameState.player.getDirection();
            if(d == DIRECTION.UP) {
                dir = new Pair<>(0,1);
            } else if(d == DIRECTION.DOWN) {
                dir = new Pair<>(0,-1);
            } else if(d == DIRECTION.RIGHT) {
                dir = new Pair<>(1,0);
            } else if(d == DIRECTION.LEFT) {
                dir = new Pair<>(-1,0);
            }
            System.out.println("Jessonn");

            if((this.gameState.player.getY() + dir.getSecond())
                    != this.gameState.player.getActiveEngimon().getY()
                    || (this.gameState.player.getX() + dir.getFirst())
                    != this.gameState.player.getActiveEngimon().getX() ) {
                if(gameState.map.get(this.gameState.player.getY() + dir.getSecond()).get(this.gameState.player.getX() + dir.getFirst()).occupier != null){
                    WildEngimon occupier = (WildEngimon) gameState.map.get(this.gameState.player.getY() + dir.getSecond()).get(this.gameState.player.getX() + dir.getFirst()).occupier;
                    if (occupier != null) {
                        Battle B = new Battle();
                        Engimon PlayerEngimons =(Engimon)this.gameState.player.getActiveEngimon();
                        Engimon EnemyEngimons = (Engimon)occupier;
                        B.BattleEngimon(PlayerEngimons, EnemyEngimons);
                        String AllBattleDialogue = B.showTotalPower();
                        if(B.BattleStatusIsWin()) {
                            AllBattleDialogue += "Engimon anda jago juga !\n";
                        } else {
                            AllBattleDialogue += "Engimon anda cupu kali !\n";
                        }
                        ArrayList<String> Dialog = new ArrayList<String>();
                        Dialog.add("=====DETAIL MY ENGIMON=====\n" + PlayerEngimons.displayInfoToString());
                        Dialog.add("=====DETAIL ENEMY ENGIMON=====\n" + EnemyEngimons.displayInfoToString());
                        Dialog.add(AllBattleDialogue);
                        System.out.println(AllBattleDialogue);
                        gameScreen.dialogueController.startBattleDialogue(Dialog);
                        System.out.println(occupier.getName());
                    }
                    System.out.println("HAHA");
                }
            }
            else {
                System.out.println("JESSONNNNNNNNNNNNNNNNNNNNNNNNNNN");
            }
        }
    }

    public void instantKill(){
        if(this.gameState.player.getActiveEngimon()!=null){
            Pair<Integer,Integer> dir = new Pair<>(0,0);
            DIRECTION d = this.gameState.player.getDirection();
            if(d == DIRECTION.UP) {
                dir = new Pair<>(0,1);
                System.out.println("atas");
            } else if(d == DIRECTION.DOWN) {
                dir = new Pair<>(0,-1);
                System.out.println("bawah");
            } else if(d == DIRECTION.RIGHT) {
                dir = new Pair<>(1,0);
                System.out.println("kanan");
            } else if(d == DIRECTION.LEFT) {
                dir = new Pair<>(-1,0);
                System.out.println("kiri");
            }
            System.out.println("X DIPENCET");
            if((this.gameState.player.getY() + dir.getSecond()) != this.gameState.player.getActiveEngimon().getY() || (this.gameState.player.getX() + dir.getFirst()) != this.gameState.player.getActiveEngimon().getX() ) {
                System.out.println("Ya itu engimon musuh");
                if(gameState.map.get(this.gameState.player.getY() + dir.getSecond()).get(this.gameState.player.getX() + dir.getFirst()).occupier != null){
                    WildEngimon occupier = (WildEngimon) gameState.map.get(this.gameState.player.getY() + dir.getSecond()).get(this.gameState.player.getX() + dir.getFirst()).occupier;
                    occupier.reduceLives();
                    System.out.println("mati lo anjeng");
                }
            }   else {
                System.out.println("Itu engimon anda sendiri");
            }
        }
    }
}
