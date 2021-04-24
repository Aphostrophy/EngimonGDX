package com.ungabunga.model.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.ungabunga.model.entities.Breeder;
import com.ungabunga.model.entities.Engimon;
import com.ungabunga.model.entities.Inventory;
import com.ungabunga.model.exceptions.EngimonNotFound;
import com.ungabunga.model.screen.ChildEngimonScreen;
import org.lwjgl.Sys;

import java.io.IOException;
import java.util.ArrayList;

public class BreederEngimonUI extends Table {
    private final static int ROW = 5;
    private final static int COLUMN = 4;
    private final int slotWidth = 70;
    private final int slotHeight = 70;

    private Engimon parent;
    private Engimon temp;
    private ArrayList<Engimon> breedableEngimon;

    public BreederEngimonUI(Skin skin, Inventory<Engimon> inventory){
        super(skin);
        this.setBackground("dialoguebox");
        Texture texture = new Texture(Gdx.files.internal("Avatar/brendan_bike_east_0.png"));

        this.breedableEngimon = new ArrayList<>();

        for (int i = 0; i < inventory.getFilledSlot(); i++) {
            temp = inventory.getItemByIndex(i);
            breedableEngimon.add(temp);
            System.out.println(i);
        }

        int k = 0;

        for(int i = 1; i <= ROW; i++) {
            for(int j = 1; j <= COLUMN; j++) {
                if (k < inventory.getFilledSlot()) {
                    BreederSlot breederSlot = new BreederSlot(skin, new BreederItem(texture, breedableEngimon.get(k)));
                    this.add(breederSlot).size(slotWidth, slotHeight).pad(2.5f);
                    breederSlot.addListener(new ClickListener() {
                        public void clicked(InputEvent event, float x, float y) {
                            System.out.println("Click!!");
                        }
                    });
                    k++;
                } else {
                    BreederSlot breederSlot = new BreederSlot(skin);
                    this.add(breederSlot).size(slotWidth, slotHeight).pad(2.5f);
                }
            }
            this.row();
        }
    }
}
