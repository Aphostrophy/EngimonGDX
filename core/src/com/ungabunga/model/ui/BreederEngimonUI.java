package com.ungabunga.model.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class BreederEngimonUI extends Table {
    private final static int ROW = 6;
    private final static int COLUMN = 4;
    private final int slotWidth = 70;
    private final int slotHeight = 70;

    public BreederEngimonUI(Skin skin){
        super(skin);
        this.setBackground("dialoguebox");

        for(int i = 1; i <= ROW; i++) {
            for(int j = 1; j <= COLUMN; j++) {
                InventorySlot inventorySlot = new InventorySlot(skin);
                this.add(inventorySlot).size(slotWidth, slotHeight).pad(2.5f);
            }
            this.row();
        }
    }
}
