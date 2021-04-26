package com.ungabunga.model.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.ungabunga.model.entities.Breeder;
import com.ungabunga.model.entities.Engimon;

import java.util.ArrayList;

public class BreederSlot extends Stack {
    private Stack defaultBackground;
    private int idx;
    private boolean selected;

    public BreederSlot(Skin skin, BreederItem item, int idx){
        defaultBackground = new Stack();
        if (selected) {
            Image image = new Image(skin, "optionbox");
            defaultBackground.add(image);
        }
        defaultBackground.setName("background");
        this.add(defaultBackground);
        defaultBackground.add(item);

        this.add(defaultBackground);

        this.idx = idx;

    }

    public BreederSlot(Skin skin) {
        defaultBackground = new Stack();
        this.add(defaultBackground);
    }

    public int getIdx() {return this.idx;}
}
