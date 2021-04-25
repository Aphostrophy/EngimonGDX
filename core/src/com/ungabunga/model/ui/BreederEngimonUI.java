package com.ungabunga.model.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.ungabunga.model.GameState;
import com.ungabunga.model.utilities.ResourceProvider;
import com.ungabunga.model.entities.*;
import com.ungabunga.model.enums.IElements;
import com.ungabunga.model.exceptions.EngimonNotFound;
import com.ungabunga.model.screen.BreederScreen;
import com.ungabunga.model.screen.ChildEngimonScreen;
import com.ungabunga.model.utilities.Pair;
import com.ungabunga.model.utilities.ResourceProvider;
import org.lwjgl.Sys;

import java.io.IOException;
import java.util.ArrayList;

public class BreederEngimonUI extends Table {
    private final static int ROW = 5;
    private final static int COLUMN = 4;
    private final int slotWidth = 70;
    private final int slotHeight = 70;

    private Engimon parent;
    private int parentIdx;

    private boolean isParent;
    private ArrayList<Engimon> breedableEngimon;

    public BreederEngimonUI(Skin skin, GameState gameState, ResourceProvider provider){
        super(skin);
        this.setBackground("dialoguebox");
        Texture texture = new Texture(Gdx.files.internal("Avatar/brendan_bike_east_0.png"));

        this.breedableEngimon = new ArrayList<>();
        this.isParent = false;

        ArrayList<IElements> elmt = new ArrayList<IElements>();
        elmt.add(IElements.FIRE);
        ArrayList<IElements> elmt2 = new ArrayList<IElements>();
        elmt2.add(IElements.ELECTRIC);
        ArrayList<Skill> skills = new ArrayList<Skill>();
        Pair<String, String> parents = new Pair<String, String>("A", "B");
        this.parent = new Engimon("X", "X", "X",100, elmt, skills, parents, parents);

//        for (int i = 0; i < inventory.getFilledSlot(); i++) {
//            temp = inventory.getItemByIndex(i);
//            breedableEngimon.add(temp);
//            System.out.println(i);
//        }

        int k = 0;

        for(int i = 1; i <= ROW; i++) {
            for(int j = 1; j <= COLUMN; j++) {
                if (k < gameState.getPlayerInventory().getEngimonInventory().getFilledSlot()) {
                    BreederItem item = new BreederItem(provider.getSprite((PlayerEngimon) gameState.getPlayerInventory().getEngimonInventory().getItemByIndex(k)), (Engimon) gameState.getPlayerInventory().getEngimonInventory().getItemByIndex(k));
                    BreederSlot breederSlot = new BreederSlot(skin, item, k);
                    this.add(breederSlot).size(slotWidth, slotHeight).pad(2.5f);
                    breederSlot.addListener(new ClickListener() {
                        public void clicked(InputEvent event, float x, float y) {
                            super.clicked(event, x, y);
                            BreederSlot slot = (BreederSlot) event.getListenerActor();
                            setParent((Engimon) gameState.getPlayerInventory().getEngimonInventory().getItemByIndex(slot.getIdx()), slot.getIdx());
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

    public void setParent(Engimon engimon, int idx) {
        this.parent = engimon;
        this.isParent = true;
        this.parentIdx = idx;
    }

    public void printParent() {
        System.out.println(parent.getName());
    }

    public Engimon getParentEngimon() {
        return this.parent;
    }

    public boolean isParentFilled() {
        return this.isParent;
    }

    public void resetParent() {
        this.isParent = false;
    }

    public int getParentIdx() {
        return this.parentIdx;
    }
}
