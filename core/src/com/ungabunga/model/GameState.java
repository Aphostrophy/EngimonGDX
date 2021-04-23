package com.ungabunga.model;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.ungabunga.model.entities.MapCell;
import com.ungabunga.model.entities.Player;
import com.ungabunga.model.enums.CellType;
import com.ungabunga.model.exceptions.CellOccupiedException;
import com.ungabunga.model.utilities.AnimationSet;
import com.ungabunga.model.utilities.fileUtil;

public class GameState {
    public Player player;
    public MapCell[][] map;
    public GameState(String name, AnimationSet animations, TiledMap tiledMap) {
        this.player = new Player(name, animations);

        TiledMapTileLayer biomeLayer = (TiledMapTileLayer)tiledMap.getLayers().get(0); // Tile
        TiledMapTileLayer decorationLayer = (TiledMapTileLayer)tiledMap.getLayers().get(1); // Decoration

        this.map = fileUtil.readMapLayer(biomeLayer);

        for(int y=0;y<decorationLayer.getHeight();y++){
            for(int x=0;x<decorationLayer.getWidth();x++){
                if(decorationLayer.getCell(x,y) != null){
                    if(decorationLayer.getCell(x,y).getTile().getProperties().containsKey("Blocked")){
                        this.map[y][x].cellType = CellType.BLOCKED;
                    }
                }
            }
        }

//        for(int i=0;i<map.length;i++){
//            for(int j=0;j<map[i].length;j++){
//                System.out.print(map[i][j].cellType.toString().charAt(0));
//            }
//            System.out.println("");
//        }
    }

    public void movePlayerUp() throws CellOccupiedException {
        int x = player.getPosition().getFirst();
        int y = player.getPosition().getSecond();
        if(y+1<map.length){
            if(map[y+1][x].occupier==null && map[y+1][x].cellType!=CellType.BLOCKED){
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
            if(map[y-1][x].occupier==null && map[y-1][x].cellType!=CellType.BLOCKED){
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
            if(map[y][x-1].occupier==null && map[y][x-1].cellType!=CellType.BLOCKED){
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
            if(map[y][x+1].occupier==null && map[y][x+1].cellType!=CellType.BLOCKED){
                player.moveRight();
            } else{
                throw new CellOccupiedException("Cell occupied!");
            }
        }
    }
}
