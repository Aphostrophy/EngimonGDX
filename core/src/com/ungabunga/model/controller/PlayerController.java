package com.ungabunga.model.controller;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.ungabunga.model.entities.Player;

public class PlayerController extends InputAdapter{
    private Player player;
    public PlayerController(Player p) {
        this.player = p;
    }

    @Override
    public boolean keyDown(int keycode) {

        if(keycode == Keys.W) {
            player.moveUp();
        }
        if(keycode == Keys.S) {
            player.moveDown();
        }
        if(keycode == Keys.A) {
            player.moveLeft();
        }
        if(keycode == Keys.D) {
            player.moveRight();
        }
        return false;
    }
}
