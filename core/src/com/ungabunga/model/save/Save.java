package com.ungabunga.model.save;

import com.ungabunga.model.GameState;

public class Save {

    int playerPosX;
    int playerPosY;

    public Save(GameState gameState){
        this.playerPosX = gameState.player.getX();
        this.playerPosY = gameState.player.getY();
    }
}
