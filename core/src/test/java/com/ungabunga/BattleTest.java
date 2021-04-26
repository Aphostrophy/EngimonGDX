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

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class BattleTest {
    Battle B = new Battle(true);
    BattleTest() {
        int i =0, j=0;
        try {
            BufferedReader inputFile = new BufferedReader(new FileReader("../core/src/com/ungabunga/model/constants/advantageChart.csv"));
            String line;
            while ((line = inputFile.readLine()) != null) {
                String[] values = line.split(",");
                j = 0;
                for (String str : values) {
                    float str_float = Float.parseFloat(str);
                    B.advantageChart[i][j] = str_float;
                    System.out.print(B.advantageChart[i][j] + " ");
                    j++;
                }
                i++;
                System.out.println("");
            }
            inputFile.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
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
        skills2.add(skill2);

        Engimon engimon2 = new Engimon("NamaPokemon2", "Jolteons", "Keep the energy", 1, engimonElements2, skills2, parentName2, parentSpecies2);

        B.BattleEngimon(engimon1, engimon2);
        Assertions.assertEquals(false, B.BattleStatusIsWin());
    }
    @Test
    public void testIsPlayerWin() {
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
        skills2.add(skill2);

        Engimon engimon2 = new Engimon("NamaPokemon2", "Jolteons", "Keep the energy", 1, engimonElements2, skills2, parentName2, parentSpecies2);

        B.BattleEngimon(engimon2, engimon1);
        Assertions.assertEquals(true, B.BattleStatusIsWin());
    }
}
