package com.ungabunga.model.controller;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.ungabunga.model.ui.DialogueBox;

public class DialogueBoxController extends InputAdapter{

        private DialogueBox box;

        public DialogueBoxController(DialogueBox boxs) {
            this.box = boxs;
        }

        @Override
        public boolean keyUp(int keycode) {
            if(keycode == Input.Keys.ENTER) {
                box.setVisible(false);
            }
            return false;
        }
}
