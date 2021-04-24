package com.ungabunga.model.save;

import com.ungabunga.model.GameState;
import com.ungabunga.model.entities.MapCell;

public class Save {

    public String playerName;
    public int playerPosX;
    public int playerPosY;
    public MapCell[][] map;

    public Save(){

    }

    public Save(GameState gameState){
        this.playerName = gameState.player.name;
        this.playerPosX = gameState.player.getX();
        this.playerPosY = gameState.player.getY();

        MapCell[][] map = new MapCell[gameState.map.get(0).length()][gameState.map.length()];

        for(int y=0;y<gameState.map.length();y++){
            for(int x=0;x<gameState.map.get(0).length();x++){
                map[y][x] = gameState.map.get(y).get(x);
            }
        }
        this.map = map;
    }
}
