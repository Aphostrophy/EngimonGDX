package com.ungabunga;

import com.ungabunga.model.entities.*;
import com.ungabunga.model.enums.IElements;
import com.ungabunga.model.exceptions.FullInventoryException;
import com.ungabunga.model.utilities.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

public class BagTest {
    @Test
    public void testGetCurrBagCapacity(){
        Bag bag = new Bag();

        Assertions.assertEquals(0,bag.getCurrBagCapacity());
    }
    @Test
    public void testInsertToBag(){
        Bag bag = new Bag();

        SkillItem skillItem = new SkillItem("NamaSkilItem",1);
        try {
            bag.insertToBag(skillItem);
        } catch (FullInventoryException e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(1,bag.getCurrBagCapacity());

    }

    @Test
    public void testDeleteFromBag() {
        Bag bag = new Bag();
        SkillItem skillItem = new SkillItem("NamaSkilItem",1);
        try {
            bag.insertToBag(skillItem);
        } catch (FullInventoryException e) {
            e.printStackTrace();
        }
        bag.deleteFromBag(skillItem);
        Assertions.assertEquals(0, bag.getCurrBagCapacity());
    }

    @Test
    public void testGetEngimonIndeks(){
        Bag bag = new Bag();

        List<IElements> engimonElements = new ArrayList<>();
        List<IElements> skillElements = new ArrayList<>();
        List<Skill> skills = new ArrayList<>();

        Pair<String, String> parentName = new Pair("Parent A","Parent B");
        Pair<String, String> parentSpecies = new Pair("Species A","Species B");

        engimonElements.add(IElements.ELECTRIC);
        skillElements.add(IElements.ELECTRIC);

        Skill skill = new Skill("Capacitor", skillElements, 6, 1);
        skills.add(skill);

        Engimon engimon = new Engimon("NamaPokemon", "Jolteon", "Keep the energy", 1, engimonElements, skills, parentName, parentSpecies);
        PlayerEngimon playerEngimon = new PlayerEngimon(engimon);
        try {
            bag.insertToBag(playerEngimon);
        } catch (FullInventoryException e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(0, bag.getEngimonInventory().getItemIndex(playerEngimon));
    }
    @Test
    public void testGetSkillItemIndeks(){
        Bag bag = new Bag();
        SkillItem skillItem = new SkillItem("NamaSkillItem",1);

        try {
            bag.insertToBag(skillItem);
        } catch (FullInventoryException e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(0, bag.getSkillItemInventory().getItemIndex(skillItem));
    }
    @Test
    public void testFullInventory() throws FullInventoryException {
        Bag bag = new Bag();
        SkillItem skillItem = new SkillItem("NamaSkillItem",1);

        try{
            bag.insertToBag(skillItem);
            bag.insertToBag(skillItem);
            bag.insertToBag(skillItem);
            bag.insertToBag(skillItem);
            bag.insertToBag(skillItem);
        } catch (FullInventoryException e){
            e.printStackTrace();
        }
        try{
            bag.insertToBag(skillItem);
        } catch (FullInventoryException e){
            e.printStackTrace();
        }
        Assertions.assertEquals(5,bag.getCurrBagCapacity());
    }
    @Test
    public void testGetEngimonInventory(){
        Bag bag = new Bag();
        List<IElements> engimonElements = new ArrayList<>();
        List<IElements> skillElements = new ArrayList<>();
        List<Skill> skills = new ArrayList<>();

        Pair<String, String> parentName = new Pair("Parent A","Parent B");
        Pair<String, String> parentSpecies = new Pair("Species A","Species B");

        engimonElements.add(IElements.ELECTRIC);
        skillElements.add(IElements.ELECTRIC);

        Skill skill = new Skill("Capacitor", skillElements, 6, 1);
        skills.add(skill);

        Engimon engimon = new Engimon("NamaPokemon", "Jolteon", "Keep the energy", 1, engimonElements, skills, parentName, parentSpecies);
        PlayerEngimon playerEngimon = new PlayerEngimon(engimon);
        try {
            bag.insertToBag(playerEngimon);
        } catch (FullInventoryException e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(playerEngimon,bag.getEngimonByIndex(bag.getEngimonIndex(playerEngimon)));
    }

    @Test
    public void testGetSkillItemInventory(){
        Bag bag = new Bag();
        SkillItem skillItem = new SkillItem("NamaSkillItem",1);
        try {
            bag.insertToBag(skillItem);
        } catch (FullInventoryException e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(skillItem, bag.getSkillItemByIndex(bag.getSkillItemIndex(skillItem)));
    }
}
