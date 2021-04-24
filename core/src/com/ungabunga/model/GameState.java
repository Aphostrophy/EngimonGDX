package com.ungabunga.model;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.ungabunga.EngimonGame;
import com.ungabunga.model.entities.*;
import com.ungabunga.model.enums.CellType;
import com.ungabunga.model.exceptions.CellOccupiedException;
import com.ungabunga.model.utilities.AnimationSet;
import com.ungabunga.model.utilities.fileUtil;

import java.util.concurrent.ThreadLocalRandom;

public class GameState {
    private EngimonGame app;

    public Player player;
    public MapCell[][] map;

    private Inventory<PlayerEngimon> playerEngimonInventory;
    private Inventory<SkillItem> playerSkillItemInventory;

    private float timeDelta;

    private float SPAWN_INTERVAL = 5.0f;

    private int wildEngimonCount;

    public GameState(String name, AnimationSet animations, TiledMap tiledMap, EngimonGame app) {
        TiledMapTileLayer biomeLayer = (TiledMapTileLayer)tiledMap.getLayers().get(0); // Tile
        TiledMapTileLayer decorationLayer = (TiledMapTileLayer)tiledMap.getLayers().get(1); // Decoration

        this.map = fileUtil.readMapLayer(biomeLayer);

        this.player = new Player(name, animations, map.length/2, map[0].length/2);

        this.wildEngimonCount = 0;

        for(int y=0;y<decorationLayer.getHeight();y++){
            for(int x=0;x<decorationLayer.getWidth();x++){
                if(decorationLayer.getCell(x,y) != null){
                    if(decorationLayer.getCell(x,y).getTile().getProperties().containsKey("Blocked")){
                        this.map[y][x].cellType = CellType.BLOCKED;
                    }
                }
            }
        }

        this.app = app;
        this.timeDelta = 0;
    }

    public void update(float delta){
        timeDelta += delta;
        if(timeDelta > SPAWN_INTERVAL && wildEngimonCount <=15){
            int spawnX = ThreadLocalRandom.current().nextInt(0,map.length);
            int spawnY = ThreadLocalRandom.current().nextInt(0,map.length);
            if(map[spawnY][spawnX].cellType == CellType.BLOCKED || map[spawnY][spawnX].occupier!=null){
                return;
            }
            Engimon wildEngimon = app.getResourceProvider().randomizeEngimon(map[spawnY][spawnX].cellType);
            map[spawnY][spawnX].occupier = new WildEngimon(wildEngimon);

            wildEngimonCount++;
            timeDelta = 0;

            // Nyoba doang nanti hapus di bawah ini
            if(player.getActiveEngimon()==null){
                try{
                    System.out.println("Active Engimon spawned");
                    spawnActiveEngimon(new PlayerEngimon(this.app.getResourceProvider().getEngimon("Blastoise")));
                } catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
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

    public int getWildEngimonCount(){
        return this.wildEngimonCount;
    }

    public void setWildEngimonCount(int count){
        this.wildEngimonCount=count;
    }

    public void spawnActiveEngimon(PlayerEngimon playerEngimon) throws CellOccupiedException{
        if(map[player.getY()-1][player.getX()].occupier==null){
            ActiveEngimon activeEngimon = new ActiveEngimon(playerEngimon, player);
            player.setActiveEngimon(activeEngimon);
            map[player.getY()-1][player.getX()].occupier = activeEngimon;
        }
        else if(map[player.getY()+1][player.getX()].occupier==null){
            ActiveEngimon activeEngimon = new ActiveEngimon(playerEngimon, player);
            player.setActiveEngimon(activeEngimon);
            map[player.getY()+1][player.getX()].occupier = activeEngimon;
        }
        else if(map[player.getY()][player.getX()-1].occupier==null){
            ActiveEngimon activeEngimon = new ActiveEngimon(playerEngimon, player);
            player.setActiveEngimon(activeEngimon);
            map[player.getY()][player.getX()-1].occupier = activeEngimon;
        }
        else if(map[player.getY()][player.getX()+1].occupier==null){
            ActiveEngimon activeEngimon = new ActiveEngimon(playerEngimon, player);
            player.setActiveEngimon(activeEngimon);
            map[player.getY()][player.getX()+1].occupier = activeEngimon;
        } else{
            throw new CellOccupiedException("No place to spawn player engimon");
        }
    }

//    public void removePlayerEngimon
}
