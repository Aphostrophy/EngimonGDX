package com.ungabunga.model.entities;
import com.ungabunga.model.exceptions.*;

public class Bag {
    Inventory<SkillItem> skillItemInventory;
    Inventory<Engimon> engimonInventory;

    public Bag(){
        this.skillItemInventory = new Inventory<SkillItem>();
        this.engimonInventory = new Inventory<Engimon>();
    }
    public int getCurrBagCapacity(){
        return (this.skillItemInventory.getFilledSlot() + this.engimonInventory.getFilledSlot());
    }
    public void insertToBag(SkillItem skillitem) throws FullInventoryException {
        this.skillItemInventory.insertToInventory(skillitem,getCurrBagCapacity());
    }
    public void insertToBag(Engimon engimon) throws FullInventoryException {
        this.engimonInventory.insertToInventory(engimon,getCurrBagCapacity());
    }
    public void deleteFromBag(SkillItem skillitem){
        this.skillItemInventory.deleteFromInventory(skillitem);
    }
    public void deleteFromBag(Engimon engimon){
        this.skillItemInventory.deleteFromInventory(engimon);
    }
    public Engimon getEngimonByIndex(int index){
        return engimonInventory.getItemByIndex(index);
    }
    public SkillItem getSkillItemByIndex(int index){
        return skillItemInventory.getItemByIndex(index);
    }
    public void showInventory(){
        this.skillItemInventory.displaySkillItem();
        this.engimonInventory.displayEngimon();
    }
    public int getEngimonIndex(Engimon engimon){
        return engimonInventory.getItemIndex(engimon);
    }
    public int getSkillItemIndex(SkillItem skillitem){
        return skillItemInventory.getItemIndex(skillitem);
    }

    public Engimon getEngimonByID(int ID) throws EngimonNotFound{
        return engimonInventory.getItemByIndex(engimonInventory.getEngimonIndexByID(ID));
    }
    public SkillItem getSkillItemByName(String name) throws SkillItemNotFound{
        return skillItemInventory.getItemByIndex(skillItemInventory.getSkillItemIndexByName(name));
    }
}
