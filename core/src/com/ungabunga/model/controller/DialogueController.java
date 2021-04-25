package com.ungabunga.model.controller;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.ungabunga.model.dialogue.Dialogue;
import com.ungabunga.model.dialogue.DialogueNode;
import com.ungabunga.model.dialogue.DialogueNode.NODE_TYPE;
import com.ungabunga.model.dialogue.DialogueTraverser;
import com.ungabunga.model.ui.DialogueBox;
import com.ungabunga.model.ui.OptionBox;

import java.util.ArrayList;

public class DialogueController extends InputAdapter {
    private DialogueTraverser traverser;
    private DialogueBox Dbox;
    private OptionBox Obox;

    private boolean startCountdown;
    private boolean isBattle = false;

    private float dialogueBoxTimer;
    private float TIMER_TIMEOUT = 2f;
    private float BATTLE_TIMEOUT = 4f;

    public DialogueController(DialogueBox Dbox, OptionBox Obox) {
        this.Dbox = Dbox;
        this.Obox = Obox;

        this.startCountdown = false;
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
        if(traverser != null && keycode == Keys.ENTER) {
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
        if(traverser != null) {
            if(traverser.getType() == NODE_TYPE.MULT) {
                Obox.setVisible(true);
            }
            if(traverser.getType()==NODE_TYPE.END && Dbox.isFinished()){
                if(!startCountdown){
                    startCountdown = true;
                } else{
                    dialogueBoxTimer+= delta;
                    float time;
                    if(isBattle) {
                        time = BATTLE_TIMEOUT;
                    } else {
                        time = TIMER_TIMEOUT;
                    }
                    if(dialogueBoxTimer> time){
                        dialogueBoxTimer = 0f;
                        startCountdown = false;
                        Dbox.setVisible(false);
                        isBattle = false;
                    }
                }
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

    public void startTutorialDialogue(){
        Dialogue dialogue = new Dialogue();

        DialogueNode a = new DialogueNode("Welcome to Engimon, Curse of The Marcello Pokemon God" +
                "\nPress Enter to close this message",0);
        DialogueNode b = new DialogueNode("Use UP and DOWN arrow to select choices, would you like to skip the tutorial?", 1);
        DialogueNode c = new DialogueNode("To walk, use W A S D." +
                "\nPress H to restart the tutorial. " +
                "\nPress B for Battle", 2);
        DialogueNode d = new DialogueNode("Press F5 to save the game"+
                "\nYou can breed and open the inventory by"+
                "\nclicking the icons at the top left of the screen",3);
        DialogueNode e = new DialogueNode("Enjoy the game!", 4);

        a.makeLinear(b.getId());
        c.makeLinear(d.getId());
        b.addChoice("Yes",4);
        b.addChoice("No",2);

        dialogue.addNode(a);
        dialogue.addNode(b);
        dialogue.addNode(c);
        dialogue.addNode(d);
        dialogue.addNode(e);

        startDialogue(dialogue);
    }

    public void startExceptionDialogue(Exception e){
        isBattle = false;
        Dialogue dialogue = new Dialogue();
        DialogueNode a = new DialogueNode(e.getMessage(), 0);

        dialogue.addNode(a);
        Obox.setVisible(false);
        startDialogue(dialogue);
    }

    public void startBattleDialogue(ArrayList<String> Dialog) {
        isBattle = true;
        Dialogue dialogue = new Dialogue();
        dialogue = dialogue.generateDialogue(Dialog);
        Obox.setVisible(false);
        startDialogue(dialogue);
    }

    public void startDialogue(String message) {
        isBattle = false;
        Dialogue dialogue = new Dialogue();
        DialogueNode a = new DialogueNode(message, 0);

        dialogue.addNode(a);
        Obox.setVisible(false);
        startDialogue(dialogue);
    }

    public void startInventoryDialogue(String Dialog) {
        isBattle = false;
        Dialogue dialogue = new Dialogue();
        DialogueNode a = new DialogueNode(Dialog, 0);

        dialogue.addNode(a);
        Obox.setVisible(false);
        startDialogue(dialogue);
    }
}
