package com.ungabunga.model;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.ungabunga.EngimonGame;
import com.ungabunga.model.entities.*;
import com.ungabunga.model.enums.CellType;
import com.ungabunga.model.exceptions.CellOccupiedException;
import com.ungabunga.model.save.Save;
import com.ungabunga.model.utilities.AnimationSet;
import com.ungabunga.model.utilities.fileUtil;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicReferenceArray;

public class GameState {
    public EngimonGame app;

    public Player player;
    public AtomicReferenceArray<AtomicReferenceArray<MapCell>> map;

    private Bag playerInventory;

    private float timeDelta;

    private float SPAWN_INTERVAL = 5.0f;

    private int wildEngimonCount;

    public GameState(String name, AnimationSet animations, TiledMap tiledMap, EngimonGame app) {
        TiledMapTileLayer biomeLayer = (TiledMapTileLayer)tiledMap.getLayers().get(0); // Tile
        TiledMapTileLayer decorationLayer = (TiledMapTileLayer)tiledMap.getLayers().get(1); // Decoration

        MapCell nonConcurrentMap[][] = fileUtil.readMapLayer(biomeLayer);

        AtomicReferenceArray<AtomicReferenceArray<MapCell>> atomicMap = new AtomicReferenceArray<>(nonConcurrentMap.length);

        for(int y=0;y<nonConcurrentMap.length;y++){
            AtomicReferenceArray<MapCell> atomicRow = new AtomicReferenceArray<MapCell>(nonConcurrentMap[y]);
            atomicMap.set(y,atomicRow);
        }

        this.playerInventory = new Bag();

        this.map = atomicMap;

        this.player = new Player(name, animations, map.length()/2, map.get(0).length()/2);

        this.wildEngimonCount = 0;

        for(int y=0;y<decorationLayer.getHeight();y++){
            for(int x=0;x<decorationLayer.getWidth();x++){
                if(decorationLayer.getCell(x,y) != null){
                    if(decorationLayer.getCell(x,y).getTile().getProperties().containsKey("Blocked")){
                        this.map.get(y).get(x).cellType = CellType.BLOCKED;
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
            int spawnX = ThreadLocalRandom.current().nextInt(0,map.length());
            int spawnY = ThreadLocalRandom.current().nextInt(0,map.length());
            if(map.get(spawnY).get(spawnX).cellType == CellType.BLOCKED || map.get(spawnY).get(spawnX).occupier!=null){
                return;
            }
            Engimon wildEngimon = app.getResourceProvider().randomizeEngimon(map.get(spawnY).get(spawnX).cellType);
            map.get(spawnY).get(spawnX).occupier = new WildEngimon(wildEngimon);

            wildEngimonCount++;
            timeDelta = 0;

        }
    }

    public void loadSave(Save save){
        player.loadSave(save);
        for(int y=0;y<save.map.length;y++){
            for(int x=0;x<save.map[0].length;x++){
                this.map.get(y).set(x, save.map[y][x]);
            }
        }
        this.playerInventory = save.playerInventory;
        this.wildEngimonCount = save.wildEngimonCount;
    }

    public void movePlayerUp() throws CellOccupiedException {
        int x = player.getPosition().getFirst();
        int y = player.getPosition().getSecond();
        if(y+1<map.length()){
            if(map.get(y+1).get(x).occupier==null && map.get(y+1).get(x).cellType!=CellType.BLOCKED){
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
            if(map.get(y-1).get(x).occupier==null &&map.get(y-1).get(x).cellType!=CellType.BLOCKED){
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
            if(map.get(y).get(x-1).occupier==null && map.get(y).get(x-1).cellType!=CellType.BLOCKED){
                player.moveLeft();
            } else{
                throw new CellOccupiedException("Cell occupied!");
            }
        }
    }

    public void movePlayerRight() throws CellOccupiedException {
        int x = player.getPosition().getFirst();
        int y = player.getPosition().getSecond();
        if(x+1<map.get(y).length()){
            if(map.get(y).get(x+1).occupier==null && map.get(y).get(x+1).cellType!=CellType.BLOCKED){
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
        if(map.get(player.getY()-1).get(player.getX()).occupier==null){
            ActiveEngimon activeEngimon = new ActiveEngimon(playerEngimon, player,player.getX(), player.getY()-1);
            player.setActiveEngimon(activeEngimon);
            map.get(player.getY()-1).get(player.getX()).occupier = activeEngimon;
        }
        else if(map.get(player.getY()+1).get(player.getX()).occupier==null){
            ActiveEngimon activeEngimon = new ActiveEngimon(playerEngimon, player, player.getX(), player.getY()+1);
            player.setActiveEngimon(activeEngimon);
            map.get(player.getY()+1).get(player.getX()).occupier = activeEngimon;
        }
        else if(map.get(player.getY()).get(player.getX()-1).occupier==null){
            ActiveEngimon activeEngimon = new ActiveEngimon(playerEngimon, player, player.getX()-1, player.getY());
            player.setActiveEngimon(activeEngimon);
            map.get(player.getY()).get(player.getX()-1).occupier = activeEngimon;
        }
        else if(map.get(player.getY()).get(player.getX()+1).occupier==null){
            ActiveEngimon activeEngimon = new ActiveEngimon(playerEngimon, player, player.getX()+1,player.getY());
            player.setActiveEngimon(activeEngimon);
            map.get(player.getY()).get(player.getX()+1).occupier = activeEngimon;
        } else{
            throw new CellOccupiedException("No place to spawn player engimon");
        }
    }

    public void removePlayerEngimon(){
        if(player.getActiveEngimon()!=null){
            map.get(player.getActiveEngimon().getY()).get(player.getActiveEngimon().getX()).occupier = null;
            player.removeActiveEngimon();
        }
    }

    public Bag getPlayerInventory(){
        return this.playerInventory;
    }
}
