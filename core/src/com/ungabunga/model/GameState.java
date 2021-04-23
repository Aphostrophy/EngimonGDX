package com.ungabunga.model;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.ungabunga.model.entities.MapCell;
import com.ungabunga.model.entities.Player;
import com.ungabunga.model.exceptions.CellOccupiedException;
import com.ungabunga.model.utilities.AnimationSet;
import com.ungabunga.model.utilities.fileUtil;

import java.io.IOException;

public class GameState {
    public Player player;
    public MapCell[][] map;
    public GameState(String name, AnimationSet animations, TiledMapTileLayer TM) throws IOException {
        this.player = new Player(name, animations);
        this.map = fileUtil.readMapLayer(TM);
        for(int i=0;i<map.length;i++){
            for(int j=0;j<map[i].length;j++){
                System.out.println(map[i][j].cellType);
            }
        }
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
