package com.ungabunga.model.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.ungabunga.model.GameState;
import com.ungabunga.model.exceptions.NoBreedableEngimon;
import com.ungabunga.model.utilities.ResourceProvider;
import com.ungabunga.model.entities.*;
import com.ungabunga.model.enums.IElements;
import com.ungabunga.model.exceptions.EngimonNotFound;
import com.ungabunga.model.screen.BreederScreen;
import com.ungabunga.model.screen.ChildEngimonScreen;
import com.ungabunga.model.utilities.Pair;
import com.ungabunga.model.utilities.ResourceProvider;
import org.lwjgl.Sys;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;

public class BreederEngimonUI extends Table {
    private final static int ROW = 5;
    private final static int COLUMN = 4;
    private final int slotWidth = 70;
    private final int slotHeight = 70;

    private boolean isParentA;
    private boolean isParentB;

    private Engimon parent;
    private Engimon parentA;
    private Engimon parentB;

    private int parentAIdx;
    private int parentBIdx;

    private boolean isParent;

    public BreederEngimonUI(Skin skin, GameState gameState, ResourceProvider provider){
        super(skin);
        this.setBackground("dialoguebox");


        this.isParent = false;

        this.isParentA = false;
        this.isParentB = false;

        this.parentAIdx = -1;
        this.parentBIdx = -1;

        ArrayList<IElements> elmt = new ArrayList<IElements>();
        elmt.add(IElements.FIRE);
        ArrayList<IElements> elmt2 = new ArrayList<IElements>();
        elmt2.add(IElements.ELECTRIC);
        ArrayList<Skill> skills = new ArrayList<Skill>();
        Pair<String, String> parents = new Pair<String, String>("A", "B");
        this.parent = new Engimon("X", "X", "X",100, elmt, skills, parents, parents);
        this.parentA = new Engimon("X", "X", "X",100, elmt, skills, parents, parents);
        this.parentB = new Engimon("X", "X", "X",100, elmt, skills, parents, parents);

//        for (int i = 0; i < inventory.getFilledSlot(); i++) {
//            temp = inventory.getItemByIndex(i);
//            breedableEngimon.add(temp);
//            System.out.println(i);
//        }

        int k = 0;

        ArrayList<PlayerEngimon> ivt = new ArrayList<>();
        try {
            ivt = gameState.getPlayerInventory().getEngimonInventory().getBreedableEngimonList();
        } catch (NoBreedableEngimon e) {
            e.printStackTrace();
        }

        for (int i = 0; i < ivt.size(); i++) {
            ivt.get(i).displayInfo();
        }



        for(int i = 1; i <= ROW; i++) {
            for(int j = 1; j <= COLUMN; j++) {
                if (k < ivt.size()) {
                    BreederItem item = new BreederItem();

                    PlayerEngimon engimon = ivt.get(k);
                    item = new BreederItem(provider.getSprite(engimon), (Engimon) gameState.getPlayerInventory().getEngimonInventory().getItemByIndex(k));

                    BreederSlot breederSlot = new BreederSlot(skin, item, k);
                    this.add(breederSlot).size(slotWidth, slotHeight).pad(2.5f);
                    breederSlot.addListener(new ClickListener() {
                        public void clicked(InputEvent event, float x, float y) {
                            if (isParentA) {
                                super.clicked(event, x, y);
                                BreederSlot slot = (BreederSlot) event.getListenerActor();
                                setParentA(gameState.getPlayerInventory().getEngimonInventory().getItemByIndex(slot.getIdx()), slot.getIdx());
                            }

                            if (isParentB) {
                                super.clicked(event, x, y);
                                BreederSlot slot = (BreederSlot) event.getListenerActor();
                                setParentB(gameState.getPlayerInventory().getEngimonInventory().getItemByIndex(slot.getIdx()), slot.getIdx());
                            }

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

    public int getParentAIdx() {
        return parentAIdx;
    }

    public int getParentBIdx() {
        return parentBIdx;
    }

    public void setParentA(Engimon engimon, int idx) {
        this.isParentA = true;
        this.parentA = engimon;
        this.parentAIdx = idx;
    }

    public void setParentB(Engimon engimon, int idx){
        this.isParentB = true;
        this.parentB = engimon;
        this.parentBIdx = idx;
    }

    public Engimon getParentA () {
        return this.parentA;
    }

    public Engimon getParentB() {
        return this.parentB;
    }


    public Engimon getParentEngimon() {
        return this.parent;
    }


    public void resetParent() {
        this.isParent = false;
    }

    public void parentA() {
        this.isParentA = true;
        this.isParentB = false;
    }

    public void parentB() {
        this.isParentA = false;
        this.isParentB = true;
    }

    public boolean isParentFilled() {
        return this.parentA.getSpecies() != "X" && this.parentB.getSpecies() != "X";
    }

    public boolean isParentSame() {
        return parentAIdx == parentBIdx;
    }

    public boolean parentAFilled() {
        return this.parentA.getSpecies() != "X";
    }

    public boolean parentBFilled() {
        return this.parentB.getSpecies() != "X";
    }

    public void parentStatus() {
        System.out.println("parent A = " + isParentA);
        System.out.println("parent B = " + isParentB);
    }

    public void parentInfo() {
        System.out.println("parent A = " + parentA.getName() + ", " + parentAIdx);
        System.out.println("parent B = " + parentB.getName() + ", " + parentBIdx);
    }
}
