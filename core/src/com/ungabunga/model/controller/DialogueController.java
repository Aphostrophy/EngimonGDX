package com.ungabunga.model.controller;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.ungabunga.model.GameState;
import com.ungabunga.model.dialogue.Dialogue;
import com.ungabunga.model.dialogue.DialogueNode;
import com.ungabunga.model.dialogue.DialogueNode.NODE_TYPE;
import com.ungabunga.model.dialogue.DialogueTraverser;
import com.ungabunga.model.entities.*;
import com.ungabunga.model.exceptions.FullInventoryException;
import com.ungabunga.model.screen.GameScreen;
import com.ungabunga.model.ui.DialogueBox;
import com.ungabunga.model.ui.OptionBox;

import java.util.ArrayList;
import java.util.List;

public class DialogueController extends InputAdapter {
    private DialogueTraverser traverser;
    private DialogueBox Dbox;
    private OptionBox Obox;

    private GameScreen gameScreen;

    private boolean startCountdown;
    private DIALOG_STATE dialogState;

    private float dialogueBoxTimer;
    private float TIMER_TIMEOUT = 2f;
    private float BATTLE_TIMEOUT = 4f;

    private List<SkillItem> skillItemList;
    private WildEngimon wildEngimon;

    public enum DIALOG_STATE{
        BATTLE,
        CHOOSESKILL,
        ELSE,
    }

    public DialogueController(DialogueBox Dbox, OptionBox Obox, GameScreen gameScreen) {
        this.Dbox = Dbox;
        this.Obox = Obox;

        this.gameScreen = gameScreen;
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
            else if(traverser.getType() == NODE_TYPE.MULT && dialogState == DIALOG_STATE.BATTLE) {
                progress(Obox.getSelected());
                if(Obox.getSelected() == 0) {
                    Battle B = new Battle();
                    Engimon PlayerEngimons =this.gameScreen.getGameState().player.getActiveEngimon();
                    Engimon EnemyEngimons = this.wildEngimon;
                    B.BattleEngimon(PlayerEngimons, EnemyEngimons);
                    String AllBattleDialogue = B.showTotalPower();
                    if(B.BattleStatusIsWin()) {
                        AllBattleDialogue += "Engimon anda jago juga !";
                        if (PlayerEngimons.getLevel() < 5)
                        {
                            PlayerEngimons.addExp(5 / PlayerEngimons.getLevel() * 10);
                        }
                        else if (PlayerEngimons.getLevel() <= 10)
                        {
                            PlayerEngimons.addExp(5 / PlayerEngimons.getLevel() * 15);
                        }
                        else if (PlayerEngimons.getLevel() <= 20)
                        {
                            PlayerEngimons.addExp(5 / PlayerEngimons.getLevel() * 20);
                        }
                        else
                        {
                            PlayerEngimons.addExp(5 / PlayerEngimons.getLevel() * 30);
                        }
                        gameScreen.getGameState().getPlayerInventory().showInventory();
                        try {
                            gameScreen.getGameState().getPlayerInventory().insertToBag(new PlayerEngimon(EnemyEngimons));
                            gameScreen.getGameState().getPlayerInventory().insertToBag(new SkillItem(EnemyEngimons.getSkills().get(0).getSkillName(),EnemyEngimons.getSkills().get(0).getBasePower()));
                        } catch (FullInventoryException e) {
                            e.printStackTrace();
                            gameScreen.dialogueController.startExceptionDialogue(e);
                        }
                        gameScreen.getGameState().getPlayerInventory().showInventory();
                    } else {
                        AllBattleDialogue += "Engimon anda cupu kali !";

                    }
                    ArrayList<String> Dialog = new ArrayList<String>();
                    Dialog.add("=====DETAIL MY ENGIMON=====\n" + PlayerEngimons.displayInfoToString());
                    Dialog.add("=====DETAIL ENEMY ENGIMON=====\n" + EnemyEngimons.displayInfoToString());
                    Dialog.add(AllBattleDialogue);
                    System.out.println(AllBattleDialogue);
                    gameScreen.dialogueController.startBattleDialogue2(Dialog);
                    System.out.println(wildEngimon.getName());
                }
            }
            else if(traverser.getType() == NODE_TYPE.MULT && dialogState == DIALOG_STATE.CHOOSESKILL){
                progress(Obox.getSelected());
                System.out.println(skillItemList.get(Obox.getSelected()).getName());
            }
            else if(traverser.getType() == NODE_TYPE.MULT && dialogState == DIALOG_STATE.ELSE) {
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
                    if(dialogState == DIALOG_STATE.BATTLE) {
                        time = BATTLE_TIMEOUT;
                    } else {
                        time = TIMER_TIMEOUT;
                    }
                    if(dialogueBoxTimer> time){
                        dialogueBoxTimer = 0f;
                        startCountdown = false;
                        Dbox.setVisible(false);
                        dialogState = DIALOG_STATE.ELSE;
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
        dialogState = DIALOG_STATE.ELSE;
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
        System.out.println(e.getMessage());
        dialogState = DIALOG_STATE.ELSE;
        Dialogue dialogue = new Dialogue();
        DialogueNode a = new DialogueNode(e.getMessage(), 0);
        System.out.println(e.getMessage());
        dialogue.addNode(a);
        Obox.setVisible(false);
        startDialogue(dialogue);
    }

    public void startBattleDialogue(WildEngimon wildEngimon) {
        this.dialogState = DIALOG_STATE.BATTLE;
        this.wildEngimon = wildEngimon;
        Dialogue dialogue = new Dialogue();
        DialogueNode awal = new DialogueNode("Hellow nub", 0);
        DialogueNode a = new DialogueNode("Anda mau gelud?", 1);
        DialogueNode b = new DialogueNode("Mari kita coba!", 2);
        DialogueNode c = new DialogueNode("Okay!", 3);
        awal.makeLinear(a.getId());
        a.addChoice("Proceed",2);
        a.addChoice("Abort",3);
        dialogue.addNode(awal);
        dialogue.addNode(a);
        dialogue.addNode(b);
        dialogue.addNode(c);
        startDialogue(dialogue);
    }

    public void startDialogue(String message) {
        dialogState = DIALOG_STATE.ELSE;
        Dialogue dialogue = new Dialogue();
        DialogueNode a = new DialogueNode(message, 0);

        dialogue.addNode(a);
        Obox.setVisible(false);
        startDialogue(dialogue);
    }

    public void startInventoryDialogue(String Dialog) {
        dialogState = DIALOG_STATE.ELSE;
        Dialogue dialogue = new Dialogue();
        DialogueNode a = new DialogueNode(Dialog, 0);

        dialogue.addNode(a);
        Obox.setVisible(false);
        startDialogue(dialogue);
    }

    // I.S skillItemList 4
    public void startSkillChoiceDialogue(List<SkillItem> skillItemList){
        dialogState = DIALOG_STATE.CHOOSESKILL;
        this.skillItemList = skillItemList;
        Dialogue dialogue = new Dialogue();
        DialogueNode skillDialog = new DialogueNode("You already have 4 skills, choose one to unlearn",0);
        DialogueNode endingDialogue = new DialogueNode("Wise choice, enjoy your new skill",1);
        DialogueNode cancelDialogue = new DialogueNode("Cancelled learn skill",2);
        skillDialog.addChoice(skillItemList.get(0).getName(),1);
        skillDialog.addChoice(skillItemList.get(1).getName(),1);
        skillDialog.addChoice(skillItemList.get(2).getName(),1);
        skillDialog.addChoice(skillItemList.get(3).getName(),1);
        skillDialog.addChoice("Cancel",2);

        dialogue.addNode(skillDialog);
        dialogue.addNode(endingDialogue);
        dialogue.addNode(cancelDialogue);

        startDialogue(dialogue);
    }

    public void startBattleDialogue2(ArrayList<String> Dialog) {
        Dialogue dialogue = new Dialogue();
        dialogue = dialogue.generateDialogue(Dialog);
        Obox.setVisible(false);
        startDialogue(dialogue);
    }
}
