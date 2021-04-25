package com.ungabunga.model.entities;

import java.util.*;
import com.ungabunga.model.enums.IElements;

public class Skill {
    private String skillName;
    private int basePower, masteryLevel, masteryExp;
    private List<IElements> elements;

    public Skill(){

    }

    public Skill(String skillName, List<IElements> elements, int basePower, int masteryLevel) {
        this.skillName = skillName;
        this.elements = elements;
        this.basePower = basePower;
        this.masteryLevel = masteryLevel;
        this.masteryExp = 0;
    }

    public void displaySkillInfo() {
        System.out.println("Nama Skill\t:\t" + this.skillName);
        System.out.println("Base Power\t:\t" + this.basePower);
        System.out.println("Master Level\t:\t" + this.masteryLevel);
        System.out.println("Element\t\t:");
        for (int i = 0; i < this.elements.size(); i++)
        {
            System.out.println("\t- " + this.elements.get(i));
        }
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public int getBasePower() {
        return basePower;
    }

    public void setBasePower(int basePower) {
        this.basePower = basePower;
    }

    public int getMasteryLevel() {
        return masteryLevel;
    }

    public void setMasteryLevel(int masteryLevel) {
        this.masteryLevel = masteryLevel;
    }

    public int getMasteryExp() {
        return masteryExp;
    }

    public void setMasteryExp(int masteryExp) {
        this.masteryExp = masteryExp;
    }

    public List<IElements> getElements() {
        return elements;
    }

    public void setElements(List<IElements> elements) {
        this.elements = elements;
    }

    public Boolean isMasteryLevelUp() {
        return this.masteryExp >= 100;
    }

    public Boolean isMasteryMaxLevel() {
        return this.masteryLevel == 3;
    }

    public void addMasteryExp(int exp) {
        if(!isMasteryMaxLevel()) {
            this.masteryExp += exp;
            if(isMasteryLevelUp()) {
                this.masteryExp %= 100;
                this.masteryLevel += 1;
            }
        }
    }
}
