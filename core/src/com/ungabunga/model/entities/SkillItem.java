package com.ungabunga.model.entities;

public class SkillItem {
    private String skillName;
    private int amount;

    SkillItem(){
        this.skillName = "XXX";
        this.amount = 0;
    }

    SkillItem(String skillName)
    {
        this.skillName = skillName;
        this.amount = 1;
    }

    public void setAmount(int amount)
    {
        this.amount = amount;
    }

    public int getAmount()
    {
        return this.amount;
    }

    public boolean compareTwoSkillItem(SkillItem skillitem){
        return ((this.amount==skillitem.amount) && (this.skillName.equals(skillitem.getName())));
    }

    public String getName(){
        return this.skillName;
    }

}
