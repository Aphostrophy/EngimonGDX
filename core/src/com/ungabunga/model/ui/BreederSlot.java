package com.ungabunga.model.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.utils.Align;
import com.ungabunga.model.entities.Breeder;
import com.ungabunga.model.entities.Engimon;

import java.util.ArrayList;

public class BreederSlot extends Stack {
    private Stack defaultBackground;

    public BreederSlot(Skin skin, BreederItem item){
        defaultBackground = new Stack();
        Image image = new Image(skin, "optionbox");
        defaultBackground.add(image);
        defaultBackground.setName("background");
        defaultBackground.add(item);

        this.add(defaultBackground);

    }

    public BreederSlot(Skin skin) {
        defaultBackground = new Stack();
        Image image = new Image(skin, "optionbox");
        defaultBackground.add(image);
        defaultBackground.setName("background");
        this.add(defaultBackground);
    }
}
