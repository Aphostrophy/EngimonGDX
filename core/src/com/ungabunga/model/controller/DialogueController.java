package com.ungabunga.model.controller;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.ungabunga.model.GameState;
import com.ungabunga.model.dialogue.Dialogue;
import com.ungabunga.model.dialogue.DialogueNode;
import com.ungabunga.model.dialogue.DialogueNode.NODE_TYPE;
import com.ungabunga.model.dialogue.DialogueTraverser;
import com.ungabunga.model.entities.*;
import com.ungabunga.model.enums.DIRECTION;
import com.ungabunga.model.exceptions.FullInventoryException;
import com.ungabunga.model.screen.GameScreen;
import com.ungabunga.model.ui.DialogueBox;
import com.ungabunga.model.ui.OptionBox;
import com.ungabunga.model.utilities.Pair;
import com.ungabunga.model.utilities.ResourceProvider;

import javax.xml.stream.events.EntityReference;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class DialogueController extends InputAdapter {
    private DialogueTraverser traverser;
    private DialogueBox Dbox;
    private OptionBox Obox;

    private ResourceProvider provider;

    private GameScreen gameScreen;

    private boolean startCountdown;
    private DIALOG_STATE dialogState;

    private float dialogueBoxTimer;
    private float TIMER_TIMEOUT = 2f;
    private float BATTLE_TIMEOUT = 4f;

    private List<Skill> skillList;
    private Skill newSkill;
    private WildEngimon wildEngimon;

    public boolean isFullInventory = false;

    public enum DIALOG_STATE{
        BATTLE,
        CHOOSESKILL,
        ELSE,
    }

    public DialogueController(DialogueBox Dbox, OptionBox Obox, GameScreen gameScreen) {
        this.Dbox = Dbox;
        this.Obox = Obox;

        this.provider = gameScreen.getApp().getResourceProvider();

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
                    String AllBattleDialogue = "";
                    Pair<Integer,Integer> dir = new Pair<>(0,0);
                    DIRECTION d = this.gameScreen.getGameState().player.getDirection();
                    if(d == DIRECTION.UP) {
                        dir = new Pair<>(0,1);
                    } else if(d == DIRECTION.DOWN) {
                        dir = new Pair<>(0,-1);
                    } else if(d == DIRECTION.RIGHT) {
                        dir = new Pair<>(1,0);
                    } else if(d == DIRECTION.LEFT) {
                        dir = new Pair<>(-1,0);
                    }
                    if(B.BattleStatusIsWin()) {
                        try {
                            gameScreen.getGameState().getPlayerInventory().insertToBag(new PlayerEngimon(EnemyEngimons));
                            gameScreen.getGameState().getPlayerInventory().insertToBag(new SkillItem(EnemyEngimons.getSkills().get(0).getSkillName(),EnemyEngimons.getSkills().get(0).getBasePower()));
                        } catch (FullInventoryException e) {
                            e.printStackTrace();
                            isFullInventory = true;
                        }
                        AllBattleDialogue += "Good fight you defeated " + this.wildEngimon.getSpecies() + "!";
                        if (PlayerEngimons.getLevel() < 5)
                        {
                            PlayerEngimons.addExp(100);
                        }
                        else if (PlayerEngimons.getLevel() <= 10)
                        {
                            PlayerEngimons.addExp(75);
                        }
                        else if (PlayerEngimons.getLevel() <= 20)
                        {
                            PlayerEngimons.addExp(50);
                        }
                        else
                        {
                            PlayerEngimons.addExp(25);
                        }
                        PlayerEngimons.displayInfo();
                        this.wildEngimon.reduceLives();
                        if(PlayerEngimons.isMaxLevel()){
                            this.gameScreen.getGameState().disposePlayerEngimon();
                        }
                    } else {
                        AllBattleDialogue += "You lose !\n";
                        this.gameScreen.getGameState().player.getActiveEngimon().reduceLives();
                        AllBattleDialogue += "Remaining engimon lives :" + this.gameScreen.getGameState().player.getActiveEngimon().getRemainingLives();
                        if(this.gameScreen.getGameState().player.getActiveEngimon().isDead()){
                            AllBattleDialogue += "Your engimon has died";
                            this.gameScreen.getGameState().disposePlayerEngimon();
                        }
                    }
                    ArrayList<String> Dialog = new ArrayList<String>();
                    Dialog.add(AllBattleDialogue);
                    if(isFullInventory) {
                        Dialog.add("Inventory anda sudah penuh!\n anda tidak dapat menambah engimon dan item baru.");
                        isFullInventory = false;
                    }
                    gameScreen.dialogueController.startBattleDialogue2(Dialog);
                    System.out.println(wildEngimon.getName());
                    this.wildEngimon.isInBattle = false;
                }
                else if(Obox.getSelected() == 1) {
                    this.wildEngimon.isInBattle = false;
                }
            }
            else if(traverser.getType() == NODE_TYPE.MULT && dialogState == DIALOG_STATE.CHOOSESKILL){
                progress(Obox.getSelected());
                if(Obox.getSelected() <= 3) {
                    System.out.println(skillList.get(Obox.getSelected()).getSkillName());
                    gameScreen.getGameState().player.getActiveEngimon().deleteSkill(skillList.get(Obox.getSelected()).getSkillName());
                    gameScreen.getGameState().player.getActiveEngimon().addSkill(provider.getSkill(this.newSkill.getSkillName()));
                }
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
        DialogueNode c1 = new DialogueNode("Press I to open your inventory"+
                "\nPress R to remove active engimon"+
                "\nPress shift to walk faster",3);
        DialogueNode d = new DialogueNode("Press F5 to save the game"+
                "\nYou can breed and open the inventory by"+
                "\nclicking the icons at the top left of the screen",4);
        DialogueNode e = new DialogueNode("Enjoy the game!", 5);

        a.makeLinear(b.getId());
        c.makeLinear(c1.getId());
        c1.makeLinear(d.getId());
        b.addChoice("Yes",5);
        b.addChoice("No",2);

        dialogue.addNode(a);
        dialogue.addNode(b);
        dialogue.addNode(c);
        dialogue.addNode(c1);
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
        Battle B = new Battle();
        Engimon PlayerEngimons = this.gameScreen.getGameState().player.getActiveEngimon();
        Engimon EnemyEngimons = this.wildEngimon;
        B.BattleEngimon(PlayerEngimons, EnemyEngimons);
        String AllBattleDialogue = B.showTotalPower();
        DialogueNode lebihawal = new DialogueNode("=====DETAIL ENEMY ENGIMON=====\n" + EnemyEngimons.displayInfoToString(), 0);
        DialogueNode awal = new DialogueNode(AllBattleDialogue,1);
        DialogueNode a = new DialogueNode("Fight?", 2);
        DialogueNode b = new DialogueNode("Goodluck!", 3);
        DialogueNode c = new DialogueNode("Okay!", 4);
        lebihawal.makeLinear(awal.getId());
        awal.makeLinear(a.getId());
        a.addChoice("Proceed",2);
        a.addChoice("Abort",3);
        dialogue.addNode(lebihawal);
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

    // I.S SkillList 4
    public void startSkillChoiceDialogue(List<Skill> skillList, Skill newSkill){
        dialogState = DIALOG_STATE.CHOOSESKILL;
        this.skillList = skillList;
        this.newSkill = newSkill;

        Dialogue dialogue = new Dialogue();
        DialogueNode a = new DialogueNode("Your engimon already has 4 skills learned" ,0);
        DialogueNode b = new DialogueNode("You already have 4 skills, choose one to unlearn", 1);
        DialogueNode c = new DialogueNode("Wise Choice", 2);
        DialogueNode d = new DialogueNode("Cancelled learn skill!",3);

        a.makeLinear(b.getId());
        c.makeLinear(d.getId());
        b.addChoice(skillList.get(0).getSkillName(),2);
        b.addChoice(skillList.get(1).getSkillName(),2);
        b.addChoice(skillList.get(2).getSkillName(),2);
        b.addChoice(skillList.get(3).getSkillName(),2);
        b.addChoice("Cancel",3);

        dialogue.addNode(a);
        dialogue.addNode(b);
        dialogue.addNode(c);
        dialogue.addNode(d);

        startDialogue(dialogue);
    }

    public void startBattleDialogue2(ArrayList<String> Dialog) {
        Dialogue dialogue = new Dialogue();
        dialogue = dialogue.generateDialogue(Dialog);
        Obox.setVisible(false);
        startDialogue(dialogue);
    }
}
