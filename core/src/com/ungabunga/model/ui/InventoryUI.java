package com.ungabunga.model.ui;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.ungabunga.EngimonGame;
import com.ungabunga.model.entities.Bag;
import com.ungabunga.model.entities.Inventory;
import com.ungabunga.model.entities.PlayerEngimon;
import com.ungabunga.model.utilities.ResourceProvider;

public class InventoryUI extends Table {

    private final static int ROW = 5;
    private final static int COLUMN = 7;
    private final int slotWidth = 72;
    private final int slotHeight = 72;

    public InventoryUI(Skin skin, Inventory inventory, InventoryItem.ItemType itemType, ResourceProvider provider){
       super(skin);
       this.setBackground("dialoguebox");

       Integer count = 1;
       int idx = 0;
       for(int i = 1; i <= ROW; i++) {
           for(int j = 1; j <= COLUMN; j++) {
               if(idx < inventory.getFilledSlot()) {
                   InventoryItem item = new InventoryItem(provider.getSprite((PlayerEngimon) inventory.getItemByIndex(idx)), itemType, count.toString());
                   InventorySlot inventorySlot = new InventorySlot(skin, item, 1);
                   inventorySlot.addListener(new ClickListener() {
                       @Override
                       public void clicked(InputEvent event, float x, float y) {
                           super.clicked(event, x, y);
                           InventorySlot slot = (InventorySlot) event.getListenerActor();
                           System.out.println(slot.getName());
                       }
                   });
                   this.add(inventorySlot).size(slotWidth, slotHeight).pad(5f);
                   idx++;
               }
               count++;
           }
           this.row();
       }
    }
}
