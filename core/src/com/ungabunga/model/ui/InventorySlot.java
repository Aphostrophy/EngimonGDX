package com.ungabunga.model.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;

public class InventorySlot extends Stack {
    private Stack defaultBackground;
    private Label numItemsLabel;
    private int numItemsVal;
    private String name;
    private InventoryItem.ItemType itemType;

    public InventorySlot(Skin skin, InventoryItem item, int numItemsVal){
        defaultBackground = new Stack();
        Image image = new Image(skin, "optionbox");

        this.numItemsVal = numItemsVal;

        numItemsLabel = new Label(String.valueOf(numItemsVal), skin);
        numItemsLabel.setAlignment(Align.bottomRight);
        numItemsLabel.setColor(1, 0, 0, 1);
        numItemsLabel.setFontScale(2);
        if(numItemsVal <= 1) {
            numItemsLabel.setVisible(false);
        }

        defaultBackground.add(image);

        defaultBackground.setName("background");
        numItemsLabel.setName("numitems");

        if(item != null) {
            itemType = item.getItemType();
            defaultBackground.add(item);

            this.name = item.getName();
        }

        this.add(defaultBackground);
        this.add(numItemsLabel);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
}
