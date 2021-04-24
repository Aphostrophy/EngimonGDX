package com.ungabunga.model.controller;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.ungabunga.model.ui.OptionBox;

public class OptionBoxController extends InputAdapter{
    private  OptionBox box;

    public  OptionBoxController(OptionBox boxs) {
        this.box = boxs;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(keycode == Keys.UP) {
            box.moveUp();
        } else if(keycode == Keys.DOWN) {
            box.moveDown();
        } else if(keycode == Keys.ENTER) {
            box.makeNotVisible();
        }
        return false;
    }

}
