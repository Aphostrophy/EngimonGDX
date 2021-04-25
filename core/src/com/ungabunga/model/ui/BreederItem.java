package com.ungabunga.model.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.ungabunga.model.entities.Engimon;

public class BreederItem extends Table {
    private Engimon engimon;
    private Image sprite;

    public BreederItem(TextureRegion texture, Engimon engimon){
        this.sprite = new Image(texture);
        this.engimon = engimon;

        this.add(sprite).width(70).height(70);
    }

    public Engimon getEngimon() {
        return this.engimon;
    }

}
