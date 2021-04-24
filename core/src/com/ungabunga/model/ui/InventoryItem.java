package com.ungabunga.model.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class InventoryItem extends Image {
    public static enum ItemType {
        ENGIMON,
        SKILLITEM
    }

    private ItemType itemType;
    private String name;

    public InventoryItem(Texture texture, ItemType itemType, String name){
        super(texture);
        this.itemType = itemType;
        this.name =  name;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    @Override
    public String getName() {
        return name;
    }
}
