package com.ungabunga.model.ui;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class InventoryItem extends Image {
    public static enum ItemType {
        ENGIMON,
        SKILLITEM
    }

    private ItemType itemType;
    private String name;

    public InventoryItem(TextureRegion texture, ItemType itemType){
        super(texture);
        this.itemType = itemType;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }
}
