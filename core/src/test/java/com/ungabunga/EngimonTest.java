package com.ungabunga;

import com.ungabunga.model.entities.*;
import com.ungabunga.model.enums.CONSTANTS;
import com.ungabunga.model.enums.IElements;
import com.ungabunga.model.exceptions.FullInventoryException;
import com.ungabunga.model.utilities.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class EngimonTest {

    @Test
    public void testGetName() {
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
        Assertions.assertEquals("NamaPokemon", engimon.getName());
    }

    @Test
    public void testGetSpecies() {
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
        Assertions.assertEquals("Jolteon", engimon.getSpecies());
    }

    @Test
    public void testGetSlogan() {
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
        Assertions.assertEquals("Keep the energy", engimon.getSlogan());
    }

    @Test
    public void testGetLevel() {
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
        Assertions.assertEquals(1, engimon.getLevel());
    }

    @Test
    public void testGetExpAndAddExp() {
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
        Assertions.assertEquals(0, engimon.getExp());

        engimon.addExp(50);

        Assertions.assertEquals(50, engimon.getExp());

        engimon.addExp(50);

        Assertions.assertEquals(0, engimon.getExp());
        Assertions.assertEquals(2, engimon.getLevel());
    }

    @Test
    public void testGetCumulativeExp() {
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
        Assertions.assertEquals(0, engimon.getCumulativeExp());

        engimon.addExp(50);

        Assertions.assertEquals(50, engimon.getCumulativeExp());

        engimon.addExp(50);

        Assertions.assertEquals(100, engimon.getCumulativeExp());
    }

    @Test
    public void testGetElements() {
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
        Assertions.assertEquals(IElements.ELECTRIC, engimon.getElements().get(0));
    }

    @Test
    public void testGetParentNameAndSpecies() {
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
        Assertions.assertEquals("Parent A", engimon.getParentName().getFirst());
        Assertions.assertEquals("Species B", engimon.getParentSpecies().getSecond());
    }

    @Test
    public void testGetSkills() {
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
        Assertions.assertEquals("Capacitor", engimon.getSkills().get(0).getSkillName());
        Assertions.assertEquals(6, engimon.getSkills().get(0).getBasePower());
        Assertions.assertEquals(1, engimon.getSkills().get(0).getMasteryLevel());
    }
    @Test
    public void testGetCapacity(){
        Inventory inventory = new Inventory();
        Assertions.assertEquals(CONSTANTS.INVENTORYCAPACITY, inventory.getMaxCapacity());
    }
    @Test
    public void testGetFileSlot() throws FullInventoryException {
        SkillItem skillItem = new SkillItem("NamaSkill",10);
        Inventory inventory = new Inventory();
        inventory.insertToInventory(skillItem, inventory.getFilledSlot());
        Assertions.assertEquals(1, inventory.getFilledSlot());
    }
    @Test
    public void testGetAmount(){
        SkillItem skillItem = new SkillItem("NamaSkill",10);
        Assertions.assertEquals(1, skillItem.getAmount());
        skillItem.setAmount(10);
        Assertions.assertEquals(10, skillItem.getAmount());
    }
    @Test
    public void testGetSkillName(){
        SkillItem skillItem = new SkillItem("NamaSkill",10);
        Assertions.assertEquals("NamaSkill", skillItem.getName());
    }
}

