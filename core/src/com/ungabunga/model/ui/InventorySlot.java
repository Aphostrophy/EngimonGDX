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
    private int numItemsVal = 0;
    private InventoryItem.ItemType itemType;

    public InventorySlot(Skin skin, InventoryItem item){
        defaultBackground = new Stack();
        Image image = new Image(skin, "optionbox");

        numItemsLabel = new Label(String.valueOf(numItemsVal), skin);
        numItemsLabel.setAlignment(Align.bottomRight);
        numItemsLabel.setColor(1, 0, 0, 1);

        defaultBackground.add(image);

        defaultBackground.setName("background");
        numItemsLabel.setName("numitems");

        itemType = item.getItemType();
        item.setSize(0, 0);
        defaultBackground.add(item);

        this.add(defaultBackground);
        this.add(numItemsLabel);
    }
}
