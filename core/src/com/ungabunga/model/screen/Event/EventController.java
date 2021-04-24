//package com.ungabunga.model.screen.Event;
//
//import com.badlogic.gdx.Input.Keys;
//import com.badlogic.gdx.InputAdapter;
//import com.ungabunga.model.ui.DialogueBox;
//import com.ungabunga.model.ui.OptionBox;
//
//public class EventController extends InputAdapter {
//    private DialogueBox dialogue;
//    private OptionBox optionBox;
//    private STATE state;
//    public EventController() {
//        this.dialogue = dialogue;
//        this.optionBox = optionBox;
//    }
//
//    public enum STATE {
//        NEXT,
//        SELECT_ACTION,
//        DEACTIVATED,
//        ;
//    }
//
//    @Override
//    public boolean keyDown(int keycode) {
//        if (optionBox.isVisible()) {
//            if (keycode == Keys.UP) {
//                optionBox.moveUp();
//            } else if (keycode == Keys.DOWN) {
//                optionBox.moveDown();
//            } else if (keycode == Keys.ENTER) {
//                if (optionBox.getSelected() == 0) {
//                    // fungsi
//                    optionBox.setVisible(false);
//                } else if (optionBox.getSelected() == 1) {
//                    // fungsi
//                    optionBox.setVisible(false);
//                }
//            }
//        }
//        return false;
//    }
//
//    public void update(float delta) {
//        if (isDisplayingNextDialogue() && dialogue.isFinished() && !optionBox.isVisible()) {
//            optionBox.clearChoice();
//            optionBox.addOption("YES");
//            optionBox.addOption("NO");
//            optionBox.setVisible(true);
//        }
//    }
//    public boolean isDisplayingNextDialogue() {
//        return this.state == STATE.NEXT;
//    }
//}
