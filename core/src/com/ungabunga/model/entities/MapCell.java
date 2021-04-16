package com.ungabunga.model.entities;
import com.ungabunga.model.enums.CellType;

import javax.lang.model.type.NullType;

public class MapCell {

    public int x;

    public int y;

    public CellType cellType;

    public LivingEngimon occupier;

    public MapCell(int x, int y){
        this.x = x;
        this.y = y;
        this.cellType= null;
        this.occupier = null;
    }
}
