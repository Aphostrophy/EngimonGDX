package com.ungabunga.model;

import com.ungabunga.model.entities.MapCell;
import com.ungabunga.model.entities.Player;
import com.ungabunga.model.exceptions.CellOccupiedException;
import com.ungabunga.model.utilities.fileUtil;

import java.io.IOException;

public class GameState {
    public Player player;
    public MapCell[][] map;

    public GameState(String name) throws IOException {
        this.player = new Player(name);
        this.map = fileUtil.readMapFile();
    }

    public void movePlayerUp() throws CellOccupiedException {
        int x = player.getPosition().getFirst();
        int y = player.getPosition().getSecond();
        if(y+1<map.length){
            if(map[y+1][x].occupier==null){
                player.moveUp();
            } else{
                throw new CellOccupiedException("Cell occupied!");
            }
        }
    }

    public void movePlayerDown() throws CellOccupiedException {
        int x = player.getPosition().getFirst();
        int y = player.getPosition().getSecond();
        if(y-1>=0){
            if(map[y-1][x].occupier==null){
                player.moveDown();
            } else{
                throw new CellOccupiedException("Cell occupied!");
            }
        }
    }

    public void movePlayerLeft() throws CellOccupiedException {
        int x = player.getPosition().getFirst();
        int y = player.getPosition().getSecond();
        if(x-1>=0){
            if(map[y][x-1].occupier==null){
                player.moveLeft();
            } else{
                throw new CellOccupiedException("Cell occupied!");
            }
        }
    }

    public void movePlayerRight() throws CellOccupiedException {
        int x = player.getPosition().getFirst();
        int y = player.getPosition().getSecond();
        if(x+1<map[y].length){
            if(map[y][x+1].occupier==null){
                player.moveRight();
            } else{
                throw new CellOccupiedException("Cell occupied!");
            }
        }
    }
}
