package com.ungabunga.model.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.utils.Align;

public class ParentSlot extends Stack {
    private Stack defaultBackground;

    public ParentSlot(Skin skin){
        defaultBackground = new Stack();
        Image image = new Image(skin, "optionbox");

        defaultBackground.add(image);

        defaultBackground.setName("background");

        this.add(defaultBackground);

    }
}
