package com.ungabunga.model.controller;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.ungabunga.model.GameState;

public class PlayerController extends InputAdapter{
    private GameState gameState;
    public PlayerController(GameState gameState) {
        this.gameState = gameState;
    }

    @Override
    public boolean keyDown(int keycode) {

        if(keycode == Keys.W) {
            try{
                gameState.movePlayerUp();
            } catch(Exception e){
                System.out.println(e);
            }
        }
        if(keycode == Keys.S) {
            try{
                gameState.movePlayerDown();
            } catch(Exception e){
                System.out.println(e);
            }
        }
        if(keycode == Keys.A) {
            try{
                gameState.movePlayerLeft();
            } catch(Exception e){
                System.out.println(e);
            }
        }
        if(keycode == Keys.D) {
            try{
                gameState.movePlayerRight();
            } catch(Exception e){
                System.out.println(e);
            }
        }
        return false;
    }
}
