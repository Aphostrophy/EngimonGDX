package com.ungabunga.model.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.ungabunga.model.entities.MapCell;
import com.ungabunga.model.enums.CellType;

import java.io.IOException;
import java.util.ArrayList;

import static com.ungabunga.model.enums.CONSTANTS.*;

public class fileUtil {
    public static MapCell[][] readMapFile() throws IOException {
        FileHandle handle = Gdx.files.internal("resources/peta.txt");
        String text = handle.readString();
        String wordsArray[] = text.split("\\r?\\n");

        ArrayList<ArrayList<MapCell>> gameMap= new ArrayList<ArrayList<MapCell>>();
        int y =0;

        for(String word : wordsArray) {
            ArrayList<MapCell> mapRow = new ArrayList<MapCell>();
            for(int x=0;x<word.length();x++){
                MapCell mapCell = new MapCell(x,y);
                switch(word.charAt(x)){
                    case GRASSLANDSYMBOL:
                        mapCell.cellType = CellType.GRASSLAND;
                        mapRow.add(mapCell);
                        break;
                    case MOUNTAINSSYMBOL:
                        mapCell.cellType = CellType.MOUNTAINS;
                        mapRow.add(mapCell);
                        break;
                    case SEASYMBOL:
                        mapCell.cellType = CellType.SEA;
                        mapRow.add(mapCell);
                        break;
                    case TUNDRASYMBOL:
                        mapCell.cellType = CellType.TUNDRA;
                        mapRow.add(mapCell);
                        break;
                }
            }
            gameMap.add(mapRow);
            y++;
        }

        return gameMap.stream().map(u -> u.toArray(new MapCell[u.size()])).toArray(MapCell[][]::new);
    }

    public static MapCell[][] readMapLayer(TiledMapTileLayer TM){
        ArrayList<ArrayList<MapCell>> gameMap= new ArrayList<ArrayList<MapCell>>();
        System.out.println("Masuk");
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
                else if(TM.getCell(x,y).getTile().getProperties().containsKey("Biome") && TM.getCell(x,y).getTile().getProperties().get("Biome").equals("Mountains")){
                    mapCell.cellType = CellType.MOUNTAINS;
                }
                else if(TM.getCell(x,y).getTile().getProperties().containsKey("Biome") && TM.getCell(x,y).getTile().getProperties().get("Biome").equals("Sea")){
                    mapCell.cellType = CellType.SEA;
                }
                else if(TM.getCell(x,y).getTile().getProperties().containsKey("Biome") && TM.getCell(x,y).getTile().getProperties().get("Biome").equals("Tundra")){
                    mapCell.cellType = CellType.TUNDRA;
                } else{
                    mapCell.cellType = CellType.BLOCKED;
                }
                mapRow.add(mapCell);
            }
            gameMap.add(mapRow);
        }
        System.out.println("Keluar");
        return gameMap.stream().map(u -> u.toArray(new MapCell[u.size()])).toArray(MapCell[][]::new);
    }
}
