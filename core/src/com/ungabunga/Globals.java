package com.ungabunga;

import com.ungabunga.model.entities.MapCell;
import com.ungabunga.model.utilities.fileUtil;

import java.io.IOException;

public class Globals {
    // Ini nanti diisi engimon sama items
    public static MapCell[][] map;

    static {
        try {
            map = fileUtil.readMapFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
