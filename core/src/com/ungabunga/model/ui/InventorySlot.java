package com.ungabunga.model.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.utils.Align;

public class InventorySlot extends Stack {
    private Stack defaultBackground;
    private Label numItemsLabel;
    private int numItemsVal;
    private int idx;
    private int id;
    private String skillItemName;
    private InventoryItem.ItemType itemType;

    public InventorySlot(Skin skin, InventoryItem item, int numItemsVal, int idx, int id, String skillItemName){
        defaultBackground = new Stack();
        Image image = new Image(skin, "optionbox");

        this.idx = idx;
        this.numItemsVal = numItemsVal;
        this.id = id;
        this.skillItemName = skillItemName;

        numItemsLabel = new Label(String.valueOf(numItemsVal), skin);
        numItemsLabel.setAlignment(Align.bottomRight);
        numItemsLabel.setColor(1, 0, 0, 1);
        numItemsLabel.setFontScale(2);

        defaultBackground.add(image);

        defaultBackground.setName("background");
        numItemsLabel.setName("numitems");

        if(item != null) {
            itemType = item.getItemType();
            defaultBackground.add(item);
        }

        this.add(defaultBackground);
        if(numItemsVal>0){
            this.add(numItemsLabel);
        }
        checkVisibilityOfItemCount();
    }

    public void decrementItemCount(int count) {
        numItemsVal -= count;
        numItemsLabel.setText(String.valueOf(numItemsVal));
        if( numItemsVal == 0){
            defaultBackground.getChildren().pop();
        }
        checkVisibilityOfItemCount();
    }

    private void checkVisibilityOfItemCount(){
        if( numItemsVal <= 1 && itemType == InventoryItem.ItemType.SKILLITEM){
            numItemsLabel.setVisible(false);
        } else {
            numItemsLabel.setVisible(true);
        }
    }

    public int getNumItemsVal() {
        return numItemsVal;
    }

    public void setNumItemsVal(int numItemsVal) {
        this.numItemsVal = numItemsVal;
    }

    public InventoryItem.ItemType getItemType() {
        return itemType;
    }

    public int getIdx() {
        return idx;
    }

    public int getId() {
        return id;
    }

    public String getSkillItemName() {
        return skillItemName;
    }
}
