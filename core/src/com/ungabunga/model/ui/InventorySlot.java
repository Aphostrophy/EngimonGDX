package com.ungabunga.model.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.utils.Align;

public class InventorySlot extends Stack {
    private Stack defaultBackground;
    private Label numItemsLabel;
    private int numItemsVal = 0;
    private int filterItemType;

    public InventorySlot(Skin skin){
        filterItemType = 0; //filter nothing
        defaultBackground = new Stack();
        Image image = new Image(skin, "optionbox");

        numItemsLabel = new Label(String.valueOf(numItemsVal), skin);
        numItemsLabel.setAlignment(Align.bottomRight);
        numItemsLabel.setColor(1, 0, 0, 1);

        defaultBackground.add(image);

        defaultBackground.setName("background");
        numItemsLabel.setName("numitems");

        this.add(defaultBackground);
        this.add(numItemsLabel);
    }
}
