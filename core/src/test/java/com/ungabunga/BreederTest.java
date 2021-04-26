package com.ungabunga;

import com.ungabunga.model.entities.Engimon;
import com.ungabunga.model.entities.Skill;
import com.ungabunga.model.entities.Breeder;
import com.ungabunga.model.enums.IElements;
import com.ungabunga.model.utilities.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
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

        Engimon a = new Engimon("NamaPokemon", "Jolteon", "Keep the energy", 10, engimonElements, skills, parentName, parentSpecies);
        Engimon b = new Engimon("NamaPokemon", "Jolteon", "Keep the energy", 6, engimonElements, skills, parentName, parentSpecies);
        Breeder.reduceLevel(a,b);
        Assertions.assertEquals(7, a.getLevel());
        Assertions.assertEquals(3, b.getLevel());
    }
    @Test
    public void testGetSameSkillIdx(){
        ArrayList<IElements> skillElements = new ArrayList<>();
        ArrayList<Skill> skills = new ArrayList<>();

        skillElements.add(IElements.ELECTRIC);

        Skill skillA = new Skill("Capacitor", skillElements, 6, 1);
        Skill skillB = new Skill("Metal", skillElements, 6, 1);
        skills.add(skillA);
        skills.add(skillB);

        Assertions.assertEquals(1, Breeder.getSameSkillIdx(skills, skillB));

    }
    @Test
    public void testGetLowSkillIdx(){
        ArrayList<IElements> skillElements = new ArrayList<>();
        ArrayList<Skill> skills = new ArrayList<>();

        skillElements.add(IElements.ELECTRIC);

        Skill skillA = new Skill("Capacitor", skillElements, 6, 9);
        Skill skillB = new Skill("Metal", skillElements, 6, 3);
        Skill skillC = new Skill("Metal", skillElements, 6, 1);
        skills.add(skillA);
        skills.add(skillB);
        skills.add(skillC);

        Assertions.assertEquals(2, Breeder.getLowSkillIdx(skills));
    }
    @Test
    public void testGetLowestMastery(){
        ArrayList<IElements> skillElements = new ArrayList<>();
        ArrayList<Skill> skills = new ArrayList<>();

        skillElements.add(IElements.ELECTRIC);

        Skill skillA = new Skill("Capacitor", skillElements, 6, 9);
        Skill skillB = new Skill("Metal", skillElements, 6, 3);
        Skill skillC = new Skill("Metal", skillElements, 6, 1);
        skills.add(skillA);
        skills.add(skillB);
        skills.add(skillC);

        Assertions.assertEquals(1, Breeder.getLowestMastery(skills));
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
