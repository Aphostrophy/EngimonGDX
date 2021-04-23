package com.ungabunga.model.controller;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.ungabunga.model.GameState;
import com.ungabunga.model.enums.AVATAR_STATE;
import com.ungabunga.model.enums.DIRECTIONS;

public class PlayerController extends InputAdapter{
    private GameState gameState;
    private DIRECTIONS direction;
    private AVATAR_STATE state;

    public PlayerController(GameState gameState) {
        this.gameState = gameState;
    }

    @Override
    public boolean keyDown(int keycode) {

        if(keycode == Keys.W) {
            System.out.println("WOI W");
            direction = DIRECTIONS.UP;
            state = AVATAR_STATE.WALKING;
        }
        if(keycode == Keys.S) {
            direction = DIRECTIONS.DOWN;
            state = AVATAR_STATE.WALKING;
        }
        if(keycode == Keys.A) {
            direction = DIRECTIONS.LEFT;
            state = AVATAR_STATE.WALKING;
        }
        if(keycode == Keys.D) {
            direction = DIRECTIONS.RIGHT;
            state = AVATAR_STATE.WALKING;
        }
        return false;
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
        if(direction == DIRECTIONS.UP && state!=AVATAR_STATE.STANDING){
            try{
                gameState.movePlayerUp();
            } catch(Exception e){
                System.out.println(e);
            }
        }
        else if(direction == DIRECTIONS.DOWN && state!=AVATAR_STATE.STANDING){
            try{
                gameState.movePlayerDown();
            } catch(Exception e){
                System.out.println(e);
            }
        }
        else if(direction == DIRECTIONS.LEFT && state!=AVATAR_STATE.STANDING){
            try{
                gameState.movePlayerLeft();
            } catch(Exception e){
                System.out.println(e);
            }
        }
        else if(direction == DIRECTIONS.RIGHT && state!=AVATAR_STATE.STANDING){
            try{
                gameState.movePlayerRight();
            } catch(Exception e){
                System.out.println(e);
            }
        }
    }
}
