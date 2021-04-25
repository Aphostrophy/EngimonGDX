package com.ungabunga.model.ui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.ungabunga.model.GameState;
import com.ungabunga.model.entities.*;
import com.ungabunga.model.exceptions.CellOccupiedException;
import com.ungabunga.model.exceptions.DuplicateSkillException;
import com.ungabunga.model.exceptions.NotEnoughSkillItemException;
import com.ungabunga.model.screen.InventoryScreen;
import com.ungabunga.model.utilities.ResourceProvider;

public class InventoryUI extends Table {

    private final static int ROW = 5;
    private final static int COLUMN = 7;
    private final int slotWidth = 72;
    private final int slotHeight = 72;

    private boolean isDelete;
    private boolean isDetail;

    private int amount;

    public InventoryUI(Skin skin, Inventory inventory, InventoryItem.ItemType itemType, ResourceProvider provider, GameState gameState, InventoryScreen inventoryScreen){
       super(skin);
       this.setBackground("dialoguebox");

       Integer count = 0;
       int idx = 0;
       for(int i = 1; i <= ROW; i++) {
           for(int j = 1; j <= COLUMN; j++) {
               InventoryItem item = null;
               InventorySlot inventorySlot = null;
               if(idx < inventory.getFilledSlot()) {
                   if(itemType == InventoryItem.ItemType.ENGIMON) {
                       item = new InventoryItem(provider.getSprite((PlayerEngimon) inventory.getItemByIndex(idx)), itemType);
                       inventorySlot = new InventorySlot(skin, item, 1, count);
                   } else {
                       inventory.displaySkillItem();
                       item = new InventoryItem(provider.getSprite((SkillItem) inventory.getItemByIndex(idx)), itemType);
                       inventorySlot = new InventorySlot(skin, item, ((SkillItem) inventory.getItemByIndex(idx)).getAmount(), count);
                   }

                   inventorySlot.addListener(new ClickListener() {
                       @Override
                       public void clicked(InputEvent event, float x, float y) {
                           super.clicked(event, x, y);
                           InventorySlot slot = (InventorySlot) event.getListenerActor();

                           if (slot.getItemType() == InventoryItem.ItemType.ENGIMON) {

                               PlayerEngimon chosenEngimon = (PlayerEngimon) inventory.getItemByIndex(slot.getIdx());
                               if(isDelete) {
                                    inventory.deleteFromInventory(chosenEngimon);
                                    slot.decrementItemCount(1);
                                    inventoryScreen.dialogueController.startInventoryDialogue(chosenEngimon.getName() + " removed");
                               } else if (isDetail) {
                                    inventoryScreen.dialogueController.startInventoryDialogue(chosenEngimon.displayInfoToString());
                               } else {
                                   try {
                                       gameState.removePlayerEngimon();
                                       gameState.spawnActiveEngimon(chosenEngimon);
                                       inventoryScreen.dialogueController.startInventoryDialogue(chosenEngimon.getName() + " is now the active engimon!!");
                                   } catch (CellOccupiedException e) {
                                       inventoryScreen.dialogueController.startInventoryDialogue(e.getMessage());
                                   }
                               }

                           } else if (slot.getItemType() == InventoryItem.ItemType.SKILLITEM) {
                               SkillItem chosenSkillItem = (SkillItem) inventory.getItemByIndex(slot.getIdx());
                               if(isDelete) {
                                   if(amount <= slot.getNumItemsVal()) {
                                       try {
                                           inventory.deleteFromInventory(chosenSkillItem, amount);
                                           slot.decrementItemCount(amount);
                                           inventoryScreen.dialogueController.startInventoryDialogue(amount + " " + chosenSkillItem.getName() + " removed");
                                       } catch (NotEnoughSkillItemException e) {
                                           inventoryScreen.dialogueController.startInventoryDialogue(e.getMessage());
                                       }
                                   }
                               } else if (isDetail){
                                   Skill chosenSkill = provider.getSkill(chosenSkillItem.getName());
                                   inventoryScreen.dialogueController.startInventoryDialogue(chosenSkill.displaySkillInfoString());
                               } else {
                                   if(gameState.player.getActiveEngimon() != null) {
                                       if (chosenSkillItem.getAmount() > 0) {
                                           Skill chosenSkill = provider.getSkill(chosenSkillItem.getName());
                                           if(gameState.player.getActiveEngimon().getSkills().size()<4){
                                               try {
                                                   gameState.player.getActiveEngimon().learnSkill(chosenSkill);
                                                   inventory.deleteFromInventory(chosenSkillItem);
                                                   slot.decrementItemCount(1);
                                                   inventoryScreen.dialogueController.startInventoryDialogue(gameState.player.getActiveEngimon().getName() + " learned " + chosenSkillItem.getName() + "!!");
                                               } catch (DuplicateSkillException e) {
                                                   inventoryScreen.dialogueController.startInventoryDialogue(e.getMessage());
                                               }
                                           }
                                           else{
                                               inventoryScreen.dialogueController.startSkillChoiceDialogue(gameState.player.getActiveEngimon().getSkills());
                                           }
                                       }
                                   }
                               }
                           }
                       }
                   });

                   this.add(inventorySlot).size(slotWidth, slotHeight).pad(5f);
                   idx++;
               } else {
                   inventorySlot = new InventorySlot(skin, item, 0, count);
                   this.add(inventorySlot).size(slotWidth, slotHeight).pad(5f);
               }
               count++;
           }
           this.row();
       }
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }

    public void setDetail(boolean detail) {
        isDetail = detail;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
