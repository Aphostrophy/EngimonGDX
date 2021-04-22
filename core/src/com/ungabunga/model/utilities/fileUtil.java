package com.ungabunga.model.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
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
}
