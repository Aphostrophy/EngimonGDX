package com.ungabunga;

import com.ungabunga.model.entities.Battle;
import com.ungabunga.model.entities.Engimon;
import com.ungabunga.model.entities.Skill;
import com.ungabunga.model.entities.SkillItem;
import com.ungabunga.model.enums.IElements;
import com.ungabunga.model.utilities.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.ungabunga.model.utilities.ResourceProvider;

import java.util.ArrayList;
import java.util.List;

public class BattleTest {
//    @Test
//    public void testGetHitungPower(){
//
//        List<IElements> engimonElements = new ArrayList<>();
//        List<IElements> skillElements = new ArrayList<>();
//        List<Skill> skills = new ArrayList<>();
//
//        Pair<String, String> parentName = new Pair("Parent A","Parent B");
//        Pair<String, String> parentSpecies = new Pair("Species A","Species B");
//
//        engimonElements.add(IElements.ELECTRIC);
//        skillElements.add(IElements.ELECTRIC);
//
//        Skill skill = new Skill("Capacitor", skillElements, 6, 1);
//        skills.add(skill);
//
//        Engimon engimon = new Engimon("NamaPokemon", "Jolteon", "Keep the energy", 1, engimonElements, skills, parentName, parentSpecies);
//        Assertions.assertEquals(1, engimon.getLevel());
//        Assertions.assertEquals(6, engimon.getSkills().get(0).getBasePower());
//    }
    @Test
    public void testIsPlayerLose() {
        List<IElements> engimonElements = new ArrayList<>();
        List<IElements> skillElements = new ArrayList<>();
        List<Skill> skills = new ArrayList<>();

        Pair<String, String> parentName = new Pair("Parent A","Parent B");
        Pair<String, String> parentSpecies = new Pair("Species A","Species B");

        engimonElements.add(IElements.ELECTRIC);
        skillElements.add(IElements.ELECTRIC);

        Skill skill = new Skill("Capacitor", skillElements, 6, 1);
        skills.add(skill);

        Engimon engimon1 = new Engimon("NamaPokemon1", "Jolteon", "Keep the energy", 1, engimonElements, skills, parentName, parentSpecies);

        List<IElements> engimonElements2 = new ArrayList<>();
        List<IElements> skillElements2 = new ArrayList<>();
        List<Skill> skills2 = new ArrayList<>();

        Pair<String, String> parentName2 = new Pair("Parent A","Parent B");
        Pair<String, String> parentSpecies2= new Pair("Species A","Species B");

        engimonElements2.add(IElements.ELECTRIC);
        skillElements2.add(IElements.ELECTRIC);

        Skill skill2 = new Skill("Capacitor", skillElements2, 200, 1);
        skills.add(skill2);

        Engimon engimon2 = new Engimon("NamaPokemon", "Jolteon", "Keep the energy", 1, engimonElements2, skills2, parentName2, parentSpecies2);

        Battle B = new Battle();
        B.BattleEngimon(engimon1, engimon2);
        Assertions.assertEquals(false, B.BattleStatusIsWin());
    }
}
