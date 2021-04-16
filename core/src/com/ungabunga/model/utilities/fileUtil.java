package com.ungabunga.model.utilities;

import com.ungabunga.model.entities.MapCell;
import com.ungabunga.model.enums.CellType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import static com.ungabunga.model.enums.CONSTANTS.*;

public class fileUtil {
    public static MapCell[][] readMapFile() throws IOException {
        URL url = fileUtil.class.getResource("resources/peta.txt");
        BufferedReader br = new BufferedReader(new FileReader(url.getPath()));

        ArrayList<ArrayList<MapCell>> gameMap= new ArrayList<ArrayList<MapCell>>();

        String st;
        int y = 0;
        while ((st = br.readLine()) != null){
            ArrayList<MapCell> mapRow = new ArrayList<MapCell>();
            for(int x=0;x<st.length();x++){
                MapCell mapCell = new MapCell(x,y);
                switch(st.charAt(x)){
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
