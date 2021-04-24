package com.ungabunga.model.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.ungabunga.model.entities.Engimon;

public class BreederItem extends Image {
    private Engimon engimon;

    public BreederItem(Texture texture, Engimon engimon){
        super(texture);
        this.engimon = engimon;
    }

    public Engimon getEngimon() {
        return this.engimon;
    }

}
