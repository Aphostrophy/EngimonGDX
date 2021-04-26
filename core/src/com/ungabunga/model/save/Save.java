package com.ungabunga.model.save;

import com.ungabunga.model.GameState;
import com.ungabunga.model.entities.ActiveEngimon;
import com.ungabunga.model.entities.Bag;
import com.ungabunga.model.entities.MapCell;
import com.ungabunga.model.entities.PlayerEngimon;
import com.ungabunga.model.enums.AVATAR_STATE;
import com.ungabunga.model.enums.DIRECTION;
import com.ungabunga.model.utilities.Pair;

public class Save {

    public String playerName;
    public int playerPosX;
    public int playerPosY;

    public Pair<Integer,Integer> engimonPosition;
    public DIRECTION engimonDirection;
    public AVATAR_STATE engimonState;

    public int engimonX;
    public int engimonY;

    public PlayerEngimon engimon;

    public Bag playerInventory;

    public int wildEngimonCount;

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

        this.wildEngimonCount = gameState.getWildEngimonCount();

        this.playerInventory = gameState.getPlayerInventory();

        this.engimon = new PlayerEngimon(gameState.player.getActiveEngimon());
        this.engimonX = gameState.player.getActiveEngimon().getX();
        this.engimonY = gameState.player.getActiveEngimon().getY();
    }
}
