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
}
