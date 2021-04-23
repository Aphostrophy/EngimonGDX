package com.ungabunga.model.screen.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.utils.Align;

public class InventorySlot extends Stack {
    private Stack _defaultBackground;
    private Image _customBackgroundDecal;
    private Label _numItemsLabel;
    private int _numItemsVal = 0;
    private int _filterItemType;

    public InventorySlot(){
        _filterItemType = 0; //filter nothing
        _defaultBackground = new Stack();
        _customBackgroundDecal = new Image();
        Image image = new Image(new NinePatch(new TextureAtlas("skins/statusui.atlas").createPatch("dialog")));

        _numItemsLabel = new Label(String.valueOf(_numItemsVal), new Skin(Gdx.files.internal("skins/statusui.json"), new TextureAtlas("skins/statusui.atlas")), "inventory-item-count");
        _numItemsLabel.setAlignment(Align.bottomRight);
        _numItemsLabel.setVisible(false);

        _defaultBackground.add(image);

        _defaultBackground.setName("background");
        _numItemsLabel.setName("numitems");

        this.add(_defaultBackground);
        this.add(_numItemsLabel);
    }
}
