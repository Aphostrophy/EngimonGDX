package com.ungabunga.model;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.ungabunga.EngimonGame;
import com.ungabunga.model.controller.DialogueController;
import com.ungabunga.model.dialogue.Dialogue;
import com.ungabunga.model.dialogue.DialogueNode;
import com.ungabunga.model.entities.*;
import com.ungabunga.model.enums.CellType;
import com.ungabunga.model.enums.IElements;
import com.ungabunga.model.exceptions.CellOccupiedException;
import com.ungabunga.model.exceptions.FullInventoryException;
import com.ungabunga.model.save.Save;
import com.ungabunga.model.ui.DialogueBox;
import com.ungabunga.model.utilities.AnimationSet;
import com.ungabunga.model.utilities.Pair;
import com.ungabunga.model.utilities.fileUtil;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicReferenceArray;

public class GameState {
    public EngimonGame app;

    public Player player;
    public AtomicReferenceArray<AtomicReferenceArray<MapCell>> map;

    private Bag playerInventory;
    public DialogueBox dialogueBox;
    public boolean Occupied = false;
    private float timeDelta;
    private String stringException;
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

        ArrayList<IElements> elmt = new ArrayList<IElements>();
        elmt.add(IElements.FIRE);
        ArrayList<IElements> elmt2 = new ArrayList<IElements>();
        elmt2.add(IElements.ELECTRIC);
        ArrayList<Skill> skills = new ArrayList<Skill>();
        Pair<String, String> parents = new Pair<String, String>("A", "B");

        Engimon a = new Engimon("Test", "Pikachu", "X",100, elmt, skills, parents, parents);
        Engimon b = new Engimon("Hola", "Raichu", "X",100, elmt2, skills, parents, parents);

        SkillItem hehe = new SkillItem("Buffer", 15);
        this.playerInventory = new Bag();

        try {
            this.playerInventory.insertToBag(new PlayerEngimon(a));
            this.playerInventory.insertToBag(new PlayerEngimon(b));
            this.playerInventory.insertToBag(hehe);
            this.playerInventory.insertToBag(hehe);
        } catch (FullInventoryException e) {
            e.printStackTrace();
        }

        this.map = atomicMap;

        this.player = new Player(name, animations, map.length()/2, map.get(0).length()/2);

        this.wildEngimonCount = 0;

        this.dialogueBox = new DialogueBox(app.getSkin());

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
        player.update(delta);
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
        if(player.getActiveEngimon()!=null){
            removePlayerEngimon();
        }

        if(map.get(player.getY()-1).get(player.getX()).occupier==null && map.get(player.getY()-1).get(player.getX()).cellType != CellType.BLOCKED){
            ActiveEngimon activeEngimon = new ActiveEngimon(playerEngimon, player,player.getX(), player.getY()-1);
            player.setActiveEngimon(activeEngimon);
            map.get(player.getY()-1).get(player.getX()).occupier = activeEngimon;
        }
        else if(map.get(player.getY()+1).get(player.getX()).occupier==null && map.get(player.getY()+1).get(player.getX()).cellType != CellType.BLOCKED){
            ActiveEngimon activeEngimon = new ActiveEngimon(playerEngimon, player, player.getX(), player.getY()+1);
            player.setActiveEngimon(activeEngimon);
            map.get(player.getY()+1).get(player.getX()).occupier = activeEngimon;
        }
        else if(map.get(player.getY()).get(player.getX()-1).occupier==null && map.get(player.getY()).get(player.getX()-1).cellType != CellType.BLOCKED){
            ActiveEngimon activeEngimon = new ActiveEngimon(playerEngimon, player, player.getX()-1, player.getY());
            player.setActiveEngimon(activeEngimon);
            map.get(player.getY()).get(player.getX()-1).occupier = activeEngimon;
        }
        else if(map.get(player.getY()).get(player.getX()+1).occupier==null && map.get(player.getY()).get(player.getX()+1).cellType != CellType.BLOCKED){
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
