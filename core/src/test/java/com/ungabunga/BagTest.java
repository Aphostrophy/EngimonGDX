package com.ungabunga;

import com.ungabunga.model.entities.*;
import com.ungabunga.model.enums.IElements;
import com.ungabunga.model.utilities.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class BagTest {
    @Test
    public void testGetCurrBagCapacity(){
        List<Integer> amount = new ArrayList<>(1);
        List<Integer> neff = new ArrayList<>(10);

        Inventory skillItemInventory = new Inventory<SkillItem>();
        Inventory engimonInventory = new Inventory<PlayerEngimon>();
        Assertions.assertEquals(1, skillItemInventory.getSkillItemAmount());
        Assertions.assertEquals(10,engimonInventory.getFilledSlot() );
    }
    @Test
    public void testInsertToBag(){
        List<Integer> amount = new ArrayList<>(1);
        List<Integer> neff = new ArrayList<>(10);

        amount.add(9);
        neff.add(5);

        Inventory skillItemInventory = new Inventory<SkillItem>();
        Inventory engimonInventory = new Inventory<PlayerEngimon>();
        Assertions.assertEquals(10, skillItemInventory.getSkillItemAmount());
        Assertions.assertEquals(15,engimonInventory.getFilledSlot() );

    }

    @Test
    public void testDeleteFromBag() {
        List<Integer> amount = new ArrayList<>(1);
        List<Integer> neff = new ArrayList<>(10);

        amount.remove(1);
        neff.remove(5);

        Inventory skillItemInventory = new Inventory<SkillItem>();
        Inventory engimonInventory = new Inventory<PlayerEngimon>();
        Assertions.assertEquals(0, skillItemInventory.getSkillItemAmount());
        Assertions.assertEquals(5, engimonInventory.getFilledSlot());
    }

    @Test
    public void testGetEngimonIndeks(){
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
        int indeks = 1;
        Inventory engimonInventory = new Inventory<PlayerEngimon>();
        Assertions.assertEquals(indeks, engimonInventory.getItemIndex(engimon));
    }
    @Test
    public void testGetSkillItemIndeks(){
        SkillItem skillItem = new SkillItem("NamaSkilItem",1);

        int indeks = 1;
        Inventory skillItemInventory = new Inventory<SkillItem>();
        Assertions.assertEquals(indeks, skillItemInventory.getItemIndex(skillItem));
    }
    @Test
    public void testGetSkillItemInventory(){
        SkillItem skillItem = new SkillItem("NamaSkilItem",1);
        Inventory skillItemInventory = new Inventory<SkillItem>();
        Assertions.assertEquals(skillItem, skillItemInventory);
    }
    @Test
    public void testGetEngimonInventory(){
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
        Inventory engimonInventory = new Inventory<PlayerEngimon>();
        Assertions.assertEquals(engimon, engimonInventory);
    }
}
