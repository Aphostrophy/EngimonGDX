package com.ungabunga.model.ui;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;

public class OptionBox extends Table {

    private int selectIdx = 0;
    private List<Image> arrows = new ArrayList<Image>();
    private List<Label> options = new ArrayList<Label>();

    private Table uiContainer;

    public OptionBox(Skin skin) {
        super(skin);
        this.setBackground("optionbox");
        uiContainer = new Table();
        this.add(uiContainer).pad(5f);
    }
    public void addOption(String opt) {
        Label optionLabel = new Label(opt, this.getSkin());
        options.add(optionLabel);
        Image arrow  = new Image(this.getSkin(), "arrow");
        arrows.add(arrow);
        arrow.setVisible(false);

        uiContainer.add(arrow).expand().align(Align.left);
        uiContainer.add(optionLabel).expand().align(Align.left).space(5f);
        uiContainer.row();

        calcArrowVisibility();
    }

    public void moveUp() {
        this.selectIdx--;
        if(this.selectIdx < 0) {
            this.selectIdx = 0;
        }
        calcArrowVisibility();
    }

    public void moveDown() {
        this.selectIdx++;
        if(this.selectIdx >= arrows.size()) {
            this.selectIdx = arrows.size() - 1;
        }
        calcArrowVisibility();
    }

    public int getSelected() {
        return selectIdx;
    }

    public void clearChoice() {
        this.uiContainer.clearChildren();
        this.arrows.clear();
        this.options.clear();
        this.selectIdx = 0;
    }

    private void calcArrowVisibility() {
        for(int i =0; i<arrows.size(); i++) {
            if(i == this.selectIdx) {
                this.arrows.get(i).setVisible(true);
            }
            else {
                this.arrows.get(i).setVisible(false);
            }
        }
    }
}
