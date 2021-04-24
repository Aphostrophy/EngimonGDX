package com.ungabunga.model.ui;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class InventoryUI extends Table {

    private final static int ROW = 5;
    private final static int COLUMN = 10;
    private final int slotWidth = 40;
    private final int slotHeight = 40;

    public InventoryUI(Skin skin){
       super(skin);
       this.setBackground("dialoguebox");

       Integer count = 1;
       for(int i = 1; i <= ROW; i++) {
           for(int j = 1; j <= COLUMN; j++) {
               Texture texture = new Texture(Gdx.files.internal("Avatar/brendan_bike_east_0.png"));
               InventoryItem item = new InventoryItem(texture, InventoryItem.ItemType.ENGIMON, count.toString());
               InventorySlot inventorySlot = new InventorySlot(skin, item);
               inventorySlot.setSize(slotWidth, slotHeight);
               inventorySlot.addListener(new ClickListener() {
                   @Override
                   public void clicked(InputEvent event, float x, float y) {
                       super.clicked(event, x, y);
                       InventorySlot slot = (InventorySlot)event.getListenerActor();
                       System.out.println(slot.getName());
                   }
               });
               count++;
               this.add(inventorySlot).size(slotWidth, slotHeight).pad(2.5f);
           }
           this.row();
       }
    }
}
