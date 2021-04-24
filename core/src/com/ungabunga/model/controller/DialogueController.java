package com.ungabunga.model.controller;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.ungabunga.model.dialogue.Dialogue;
import com.ungabunga.model.dialogue.DialogueNode;
import com.ungabunga.model.dialogue.DialogueNode.NODE_TYPE;
import com.ungabunga.model.dialogue.DialogueTraverser;
import com.ungabunga.model.ui.DialogueBox;
import com.ungabunga.model.ui.OptionBox;

public class DialogueController extends InputAdapter {
    private DialogueTraverser traverser;
    private DialogueBox Dbox;
    private OptionBox Obox;

    public DialogueController(DialogueBox Dbox, OptionBox Obox) {
        this.Dbox = Dbox;
        this.Obox = Obox;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (Dbox.isVisible()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(Obox.isVisible()) {
            if(keycode == Keys.UP) {
                Obox.moveUp();
                return true;
            } else if(keycode == Keys.DOWN) {
                Obox.moveDown();
                return true;
            }
        }
        if(traverser != null && keycode == Keys.ENTER && Dbox.isFinished()) {
            if(traverser.getType() == NODE_TYPE.END) {
                traverser = null;
                Dbox.setVisible(false);
            }
            else if(traverser.getType() == NODE_TYPE.LINEAR) {
                progress(0);
            }
            else if(traverser.getType() == NODE_TYPE.MULT) {
                progress(Obox.getSelected());
            }
            return true;
        }
        if(Dbox.isVisible()) {
            return true;
        }
        return false;
    }
    public void update(float delta) {
        if(Dbox.isFinished() && traverser != null) {
            if(traverser.getType() == NODE_TYPE.MULT) {
                Obox.setVisible(true);
            }
        }
    }
    public void startDialogue(Dialogue D) {
        traverser = new DialogueTraverser(D);
        Dbox.setVisible(true);
        Dbox.animateText(traverser.getText());
        if(traverser.getType() == NODE_TYPE.MULT) {
            Obox.clear();
            for(String str : D.getNode(0).getLabels()) {
                Obox.addOption(str);
            }
        }
    }
    private void progress(int idx) {
        Obox.setVisible(false);
        DialogueNode next = traverser.getNext(idx);
        Dbox.animateText(next.getText());
        if (next.getType() == NODE_TYPE.MULT) {
            Obox.clearChoice();
            for(String str : next.getLabels()) {
                Obox.addOption(str);
            }
        }
    }

    public void startExceptionDialogue(Exception e){
        Dialogue dialogue = new Dialogue();
        DialogueNode a = new DialogueNode(e.getMessage(), 0);

        dialogue.addNode(a);
        startDialogue(dialogue);
    }
}
