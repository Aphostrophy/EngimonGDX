package com.ungabunga.model.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.utils.Array;

public class InventoryUI extends Window {

    public final static int numSlots = 50;
    public static final String PLAYER_INVENTORY = "Player_Inventory";
    public static final String STORE_INVENTORY = "Store_Inventory";

    private int lengthSlotRow = 10;
    private Table inventorySlotTable;
    private DragAndDrop dragAndDrop;
    private Array<Actor> inventoryActors;

    private final int slotWidth = 64;
    private final int slotHeight = 64;

    public InventoryUI(Skin skin){
        super("Inventory", new Skin(Gdx.files.internal("skins/statusui.json"), new TextureAtlas("skins/statusui.atlas")), "solidbackground");

        dragAndDrop = new DragAndDrop();
        inventoryActors = new Array<Actor>();

        //create
        inventorySlotTable = new Table();
        inventorySlotTable.setName("Inventory_Slot_Table");

        //layout
        for(int i = 1; i <= numSlots; i++){
            InventorySlot inventorySlot = new InventorySlot();

            inventorySlotTable.add(inventorySlot).size(slotWidth, slotHeight);

            if(i % lengthSlotRow == 0){
                inventorySlotTable.row();
            }
        }
        this.row();
        this.add(inventorySlotTable).colspan(2);
        this.row();
        this.pack();
    }
}
