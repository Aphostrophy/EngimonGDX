package com.ungabunga.model.entities;

public class SkillItem {
    private String skillName;
    private int amount;
    private int power;

    SkillItem(){
        this.skillName = "XXX";
        this.amount = 0;
        this.power = 0;
    }

    public SkillItem(String skillName, int power)
    {
        this.skillName = skillName;
        this.amount = 1;
        this.power = power;
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

    public int getPower(){return this.power;}

}
