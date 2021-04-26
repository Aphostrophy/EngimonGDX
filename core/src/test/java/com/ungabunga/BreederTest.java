package com.ungabunga;

import com.ungabunga.model.entities.Engimon;
import com.ungabunga.model.entities.Skill;
import com.ungabunga.model.enums.IElements;
import com.ungabunga.model.utilities.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class BreederTest {

    @Test
    public void testReduceLevel(){
        List<IElements> engimonElements = new ArrayList<>();
        List<IElements> skillElements = new ArrayList<>();
        List<Skill> skills = new ArrayList<>();

        Pair<String, String> parentName = new Pair("Parent A","Parent B");
        Pair<String, String> parentSpecies = new Pair("Species A","Species B");

        engimonElements.add(IElements.ELECTRIC);
        skillElements.add(IElements.ELECTRIC);

        Skill skill = new Skill("Capacitor", skillElements, 6, 1);
        skills.add(skill);

        Engimon engimon = new Engimon("NamaPokemon", "Jolteon", "Keep the energy", 2, engimonElements, skills, parentName, parentSpecies);
        Assertions.assertEquals(1, engimon.getLevel()-1);
    }
    @Test
    public void testGetSameSkillIdx(){
        List<IElements> engimonElements = new ArrayList<>();
        List<IElements> skillElements = new ArrayList<>();
        List<Skill> skills = new ArrayList<>();

        Pair<String, String> parentName = new Pair("Parent A","Parent B");
        Pair<String, String> parentSpecies = new Pair("Species A","Species B");

        engimonElements.add(IElements.ELECTRIC);
        skillElements.add(IElements.ELECTRIC);

        Skill skill = new Skill("Capacitor", skillElements, 6, 1);
        skills.add(skill);
        int indexSkill = 2;

        Engimon engimon = new Engimon("NamaPokemon", "Jolteon", "Keep the energy", 2, engimonElements, skills, parentName, parentSpecies);
        Assertions.assertEquals(indexSkill, engimon.getSkills().get(0));
    }
    @Test
    public void testGetLowSkillIdx(){
        List<IElements> engimonElements = new ArrayList<>();
        List<IElements> skillElements = new ArrayList<>();
        List<Skill> skills = new ArrayList<>();

        Pair<String, String> parentName = new Pair("Parent A","Parent B");
        Pair<String, String> parentSpecies = new Pair("Species A","Species B");

        engimonElements.add(IElements.ELECTRIC);
        skillElements.add(IElements.ELECTRIC);

        Skill skill = new Skill("Capacitor", skillElements, 6, 1);
        skills.add(skill);
        int indexSkill = 2;

        Engimon engimon = new Engimon("NamaPokemon", "Jolteon", "Keep the energy", 2, engimonElements, skills, parentName, parentSpecies);
        Assertions.assertEquals(indexSkill, engimon.getSkills().get(0).getMasteryLevel());
    }
    @Test
    public void testGetLowestMastery(){
        List<IElements> engimonElements = new ArrayList<>();
        List<IElements> skillElements = new ArrayList<>();
        List<Skill> skills = new ArrayList<>();

        Pair<String, String> parentName = new Pair("Parent A","Parent B");
        Pair<String, String> parentSpecies = new Pair("Species A","Species B");

        engimonElements.add(IElements.ELECTRIC);
        skillElements.add(IElements.ELECTRIC);

        Skill skill = new Skill("Capacitor", skillElements, 6, 1);
        skills.add(skill);

        Engimon engimon = new Engimon("NamaPokemon", "Jolteon", "Keep the energy", 2, engimonElements, skills, parentName, parentSpecies);
        Assertions.assertEquals(1, engimon.getSkills().get(0).getMasteryLevel());
    }
    @Test
    public void testGetParentDetails() {
        List<IElements> engimonElements = new ArrayList<>();
        List<IElements> skillElements = new ArrayList<>();
        List<Skill> skills = new ArrayList<>();

        Pair<String, String> parentName = new Pair("Parent A", "Parent B");
        Pair<String, String> parentSpecies = new Pair("Species A", "Species B");

        engimonElements.add(IElements.ELECTRIC);
        skillElements.add(IElements.ELECTRIC);

        Skill skill = new Skill("Capacitor", skillElements, 6, 1);
        skills.add(skill);

        Engimon engimon = new Engimon("NamaPokemon", "Jolteon", "Keep the energy", 1, engimonElements, skills, parentName, parentSpecies);
        Assertions.assertEquals("Parent A", engimon.getParentName().getFirst());
        Assertions.assertEquals("Parent B", engimon.getParentName().getSecond());
        Assertions.assertEquals("Species A", engimon.getParentSpecies().getFirst());
        Assertions.assertEquals("Species B", engimon.getParentSpecies().getSecond());
    }
    @Test
    public void testGetChildSkill() {
        List<IElements> engimonElements = new ArrayList<>();
        List<IElements> skillElements = new ArrayList<>();
        List<Skill> skills = new ArrayList<>();

        Pair<String, String> parentName = new Pair("Parent A", "Parent B");
        Pair<String, String> parentSpecies = new Pair("Species A", "Species B");

        engimonElements.add(IElements.ELECTRIC);
        skillElements.add(IElements.ELECTRIC);

        Skill skill = new Skill("Capacitor", skillElements, 6, 1);
        skills.add(skill);

        Engimon engimonA = new Engimon("NamaPokemonA", "Jolteon", "Keep the energy", 1, engimonElements, skills, parentName, parentSpecies);
        Engimon engimonB = new Engimon("NamaPokemonA", "Jolteon", "Keep the energy", 1, engimonElements, skills, parentName, parentSpecies);
        Assertions.assertEquals(6, engimonA.getSkills().get(0).getBasePower());
        Assertions.assertEquals(1, engimonA.getSkills().get(0).getMasteryLevel());
        Assertions.assertEquals(6, engimonB.getSkills().get(0).getBasePower());
        Assertions.assertEquals(1, engimonB.getSkills().get(0).getMasteryLevel());
    }

}
