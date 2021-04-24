package com.ungabunga.model.entities;

import com.ungabunga.model.exceptions.FeatureNotImplementedException;
import com.ungabunga.model.enums.CONSTANTS;
import com.ungabunga.model.enums.IElements;
import com.ungabunga.model.utilities.Pair;

import java.util.ArrayList;
import java.util.List;

public class Engimon {
    protected static int count = 0;
    protected int id;
    protected String name, species, slogan;
    protected int level, exp, cumulativeExp;
    protected List<IElements> elements;
    protected Pair<String, String> parentName, parentSpecies;
    protected List<com.ungabunga.model.entities.Skill> skills;

    public Engimon(){

    }

    public Engimon(Engimon e) {
        this.id = e.id;
        this.name = e.name;
        this.species = e.species;
        this.slogan = e.slogan;
        this.elements = e.elements;
        this.skills = e.skills;
        this.level = e.level;
        this.parentName = e.parentName;
        this.parentSpecies = e.parentSpecies;
        this.exp = e.exp;
        this.cumulativeExp = e.cumulativeExp;
    }

    public Engimon(String name, String species, String slogan, int level, List<IElements> elements, List<Skill> skills, Pair<String, String> parentName, Pair<String, String> parentSpecies) {
        this.id = count++;
        this.name = name;
        this.species = species;
        this.slogan = slogan;
        this.elements = elements;
        this.skills = skills;
        this.level = level;
        this.parentName = parentName;
        this.parentSpecies = parentSpecies;
        this.exp = 0;
        this.cumulativeExp = 0;
    }

    public Engimon(String species, String slogan, List<IElements> elements, Skill skill){
        this.id = count++;
        this.name = species;
        this.species = species;
        this.slogan = slogan;
        this.elements = elements;
        ArrayList<Skill> skills = new ArrayList<>();
        skills.add(skill);
        this.skills = skills;
        this.level = 1;
        Pair<String,String> parentSpecies = new Pair<>("Unknown", "Unknown");
        this.parentName = parentSpecies;
        this.parentSpecies = parentSpecies;
        this.exp = 0;
        this.cumulativeExp = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getCumulativeExp() {
        return cumulativeExp;
    }

    public void setCumulativeExp(int cumulativeExp) {
        this.cumulativeExp = cumulativeExp;
    }

    public List<IElements> getElements() {
        return elements;
    }

    public void setElements(List<IElements> elements) {
        this.elements = elements;
    }

    public Pair<String, String> getParentName() {
        return parentName;
    }

    public void setParentName(Pair<String, String> parentName) {
        this.parentName = parentName;
    }

    public Pair<String, String> getParentSpecies() {
        return parentSpecies;
    }

    public void setParentSpecies(Pair<String, String> parentSpecies) {
        this.parentSpecies = parentSpecies;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) { this.skills = skills; }

    public Boolean isLevelUp() {
        return this.exp >= 100;
    }

    public Boolean isMaxLevel() {
        return this.cumulativeExp >= 1000;
    }

    public void addExp(int exp) {
        this.exp += exp;
        this.cumulativeExp += exp;
        if(this.isLevelUp()) {
            this.exp %= 100;
            this.level += 1;
        }
    }

    public void displayInfo() throws FeatureNotImplementedException {
        throw new FeatureNotImplementedException("Blom diimplementasiin krn bingung ama GUI-nya");
    }

    public void displaySkills() throws FeatureNotImplementedException {
        throw new FeatureNotImplementedException("Blom diimplementasiin krn bingung ama GUI-nya");
    }

    public void increaseMastery() {
        for (int i = 0; i < this.skills.size(); i++)
        {
            this.skills.get(i).addMasteryExp(25);
        }
    }

    public void learnSkill(Skill skill) throws FeatureNotImplementedException {
        if(this.skills.size() == CONSTANTS.MAXSKILL) {
            throw new FeatureNotImplementedException("Blom diimplementasiin soalnya berhubungan ama GUI juga :'v");
        } else {
            this.skills.add(skill);
        }
    }
}
