package com.ungabunga.model.controller;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input.Keys;
import com.ungabunga.model.entities.Players;
public class PlayerController extends InputAdapter{
    private Players player;
    public PlayerController(Players p) {
        this.player = p;
    }

    @Override
    public boolean keyDown(int keycode) {

        if(keycode == Keys.W) {
            player.move(0,1);
        }
        if(keycode == Keys.S) {
            player.move(0,-1);
        }
        if(keycode == Keys.A) {
            player.move(-1,0);
        }
        if(keycode == Keys.D) {
            player.move(1,0);
        }
        return false;
    }
}
