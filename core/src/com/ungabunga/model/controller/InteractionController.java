package com.ungabunga.model.controller;

import com.badlogic.gdx.Game;
import com.ungabunga.model.GameState;
import com.ungabunga.model.entities.*;
import com.ungabunga.model.enums.DIRECTION;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.ungabunga.model.enums.CellType;
import com.ungabunga.model.utilities.Pair;

import java.util.concurrent.atomic.AtomicReferenceArray;

public class InteractionController extends InputAdapter {

    private DialogueController dialogueController;
    public GameState gameState;

    public InteractionController(DialogueController dialogueController, GameState gameState) {
        this.dialogueController = dialogueController;
        this.gameState = gameState;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Keys.T) {
            Pair<Integer,Integer> dir = new Pair<>(0,0);
            DIRECTION d = this.gameState.player.getDirection();
            if(d == DIRECTION.UP) {
                dir = new Pair<>(0,1);
            } else if(d == DIRECTION.DOWN) {
                dir = new Pair<>(0,-1);
            } else if(d == DIRECTION.RIGHT) {
                dir = new Pair<>(1,0);
            }
            else if(d == DIRECTION.LEFT) {
                dir = new Pair<>(-1,0);
            }
            if((this.gameState.player.getY() + dir.getSecond()) != this.gameState.player.getActiveEngimon().getY() && (this.gameState.player.getX() + dir.getFirst()) != this.gameState.player.getActiveEngimon().getX() ) {
                WildEngimon occupied = (WildEngimon)gameState.map.get(this.gameState.player.getY() + dir.getSecond()).get(this.gameState.player.getX() + dir.getFirst()).occupier;
                if (occupied != null) {
                    System.out.println(occupied.getName());
                }

            } else {
                System.out.println("JESSONNNNNNNNNNNNNNNNNNNNNNNNNNN");
            }

            return false;
        }
        return false;
    }

}
