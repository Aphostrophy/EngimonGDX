package com.ungabunga;

import com.ungabunga.model.entities.*;
import com.ungabunga.model.enums.CONSTANTS;
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
            bag.insertToBagForTesting(skillItem, CONSTANTS.INVENTORYCAPACITY-1);
            bag.insertToBag(skillItem);
        } catch (FullInventoryException e){

        }
        Assertions.assertEquals(CONSTANTS.INVENTORYCAPACITY,bag.getCurrBagCapacity());
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

        }
        Assertions.assertEquals(skillItem, bag.getSkillItemByIndex(bag.getSkillItemIndex(skillItem)));
    }
    @Test
    public void sortSkillItemInventory(){
        Bag bag = new Bag();
        SkillItem skillItem = new SkillItem("SkillA",5);
        SkillItem skillItem2 = new SkillItem("SkillB",10);
        try {
            bag.insertToBag(skillItem);
            bag.insertToBag(skillItem2);
        } catch (FullInventoryException e) {

        }
        Assertions.assertEquals(skillItem2, bag.getSkillItemByIndex(0));
    }

    @Test
    public void sortEngimonInventory(){
        Bag bag = new Bag();

        List<IElements> engimonElements = new ArrayList<>();
        List<IElements> engimonElements1 = new ArrayList<>();
        List<IElements> engimonElements2 = new ArrayList<>();
        List<IElements> engimonElements3 = new ArrayList<>();

        List<IElements> skillElements = new ArrayList<>();
        List<Skill> skills = new ArrayList<>();

        Pair<String, String> parentName = new Pair("Parent A","Parent B");
        Pair<String, String> parentSpecies = new Pair("Species A","Species B");

        engimonElements.add(IElements.WATER);
        engimonElements1.add(IElements.FIRE);
        engimonElements2.add(IElements.FIRE);
        engimonElements3.add(IElements.FIRE);

        skillElements.add(IElements.ELECTRIC);

        Skill skill = new Skill("Capacitor", skillElements, 6, 1);
        skills.add(skill);

        PlayerEngimon engimon = new PlayerEngimon(new Engimon("A", "Jolteon", "Keep the energy", 2, engimonElements, skills, parentName, parentSpecies)) ;
        PlayerEngimon engimon1 = new PlayerEngimon(new Engimon("B", "Jolteon", "Keep the energy", 25, engimonElements1, skills, parentName, parentSpecies)) ;
        PlayerEngimon engimon2 = new PlayerEngimon(new Engimon("C", "Jolteon", "Keep the energy", 32, engimonElements2, skills, parentName, parentSpecies)) ;
        PlayerEngimon engimon3 = new PlayerEngimon(new Engimon("D", "Jolteon", "Keep the energy", 52, engimonElements3, skills, parentName, parentSpecies)) ;

        try {
            bag.insertToBag(engimon);
            bag.insertToBag(engimon1);
            bag.insertToBag(engimon2);
            bag.insertToBag(engimon3);
        } catch (FullInventoryException e) {

        }
        Assertions.assertEquals(engimon3, bag.getEngimonByIndex(1));
    }
}
