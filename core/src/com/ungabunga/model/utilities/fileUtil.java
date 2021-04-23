package com.ungabunga.model.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.ungabunga.model.entities.Engimon;
import com.ungabunga.model.entities.MapCell;
import com.ungabunga.model.entities.Skill;
import com.ungabunga.model.enums.CellType;
import com.ungabunga.model.enums.IElements;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Copy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;


public class fileUtil {
    public static MapCell[][] readMapLayer(TiledMapTileLayer TM){
        ArrayList<ArrayList<MapCell>> gameMap= new ArrayList<ArrayList<MapCell>>();

        for(int y=0;y<TM.getHeight();y++){
            ArrayList<MapCell> mapRow = new ArrayList<MapCell>();
            for(int x=0;x<TM.getWidth();x++){
                MapCell mapCell = new MapCell(x,y);
                if(TM.getCell(x,y).getTile().getProperties().containsKey("Blocked")){
                    mapCell.cellType = CellType.BLOCKED;
                }
                else if(TM.getCell(x,y).getTile().getProperties().containsKey("Biome") && TM.getCell(x,y).getTile().getProperties().get("Biome").equals("Grassland")){
                    mapCell.cellType = CellType.GRASSLAND;
                }
                else if(TM.getCell(x,y).getTile().getProperties().containsKey("Biome") && TM.getCell(x,y).getTile().getProperties().get("Biome").equals("Mountain")){
                    mapCell.cellType = CellType.MOUNTAIN;
                }
                else if(TM.getCell(x,y).getTile().getProperties().containsKey("Biome") && TM.getCell(x,y).getTile().getProperties().get("Biome").equals("Sea")){
                    mapCell.cellType = CellType.SEA;
                }
                else if(TM.getCell(x,y).getTile().getProperties().containsKey("Biome") && TM.getCell(x,y).getTile().getProperties().get("Biome").equals("Tundra")){
                    mapCell.cellType = CellType.TUNDRA;
                } else{
                    mapCell.cellType = CellType.INVALID;
                }
                mapRow.add(mapCell);
            }
            gameMap.add(mapRow);
        }

        return gameMap.stream().map(u -> u.toArray(new MapCell[u.size()])).toArray(MapCell[][]::new);
    }

    public static CopyOnWriteArrayList<Engimon> readEngimonCSV(ResourceProvider resourceProvider){
        CopyOnWriteArrayList<Engimon> engimons = new CopyOnWriteArrayList<>();

        FileHandle handle = Gdx.files.internal("resources/engimon.csv");
        String text = handle.readString();
        String linesArray[] = text.split("\\r?\\n");

        for(String line : linesArray){
            System.out.println(line);
            String words[] = line.split(",");

            for(String word : words){
                System.out.println(word);
            }
            engimons.add(new Engimon(words[0],words[3],StringToElements(words[1]), resourceProvider.getSkill(words[2])));
        }

        return engimons;
    }

    public static CopyOnWriteArrayList<Skill> readSkillCSV(){
        CopyOnWriteArrayList<Skill> skills = new CopyOnWriteArrayList<>();

        FileHandle handle = Gdx.files.internal("resources/skill.csv");
        String text = handle.readString();
        String linesArray[] = text.split("\\r?\\n");

        for(String line : linesArray){
            String words[] = line.split(",");
            skills.add(new Skill(words[0],StringToElements(words[1]),Integer.parseInt(words[2]),0));
        }

        return skills;
    }

    private static ArrayList<IElements> StringToElements(String word){
        HashMap<String,IElements> elementsHashMap = new HashMap<>();
        elementsHashMap.put("Fire",IElements.FIRE);
        elementsHashMap.put("Water",IElements.WATER);
        elementsHashMap.put("Electric",IElements.ELECTRIC);
        elementsHashMap.put("Ground",IElements.GROUND);
        elementsHashMap.put("Ice",IElements.ICE);

        String elements[] = word.split("/");
        ArrayList<IElements> iElements = new ArrayList<>();
        for(String element : elements){
            iElements.add(elementsHashMap.get(element));
        }
        return iElements;
    }
}
