package com.ungabunga;

import com.ungabunga.model.entities.Skill;
import com.ungabunga.model.enums.IElements;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class SkillTest {
    @Test
    public void getSkillNameTest() {
        List<IElements> skillElements = new ArrayList<>();
        skillElements.add(IElements.ELECTRIC);
        Skill skill = new Skill("Capacitor", skillElements, 6, 1);
        Assertions.assertEquals("Capacitor", skill.getSkillName());
    }

    @Test
    public void getMasteryLevelTest() {
        List<IElements> skillElements = new ArrayList<>();
        skillElements.add(IElements.ELECTRIC);
        Skill skill = new Skill("Capacitor", skillElements, 6, 1);
        Assertions.assertEquals(1, skill.getMasteryLevel());
    }

    @Test
    public void getBasePowerTest() {
        List<IElements> skillElements = new ArrayList<>();
        skillElements.add(IElements.ELECTRIC);
        Skill skill = new Skill("Capacitor", skillElements, 6, 1);
        Assertions.assertEquals(6, skill.getBasePower());
    }

    @Test
    public void getElementsTest() {
        List<IElements> skillElements = new ArrayList<>();
        skillElements.add(IElements.ELECTRIC);
        Skill skill = new Skill("Capacitor", skillElements, 6, 1);
        Assertions.assertEquals(IElements.ELECTRIC, skill.getElements().get(0));
    }

    @Test
    public void masteryLevelUpTest() {
        List<IElements> skillElements = new ArrayList<>();
        skillElements.add(IElements.ELECTRIC);
        Skill skill = new Skill("Capacitor", skillElements, 6, 1);
        Assertions.assertEquals(1, skill.getMasteryLevel());
        skill.addMasteryExp(100);
        Assertions.assertEquals(2, skill.getMasteryLevel());
        skill.addMasteryExp(50);
        Assertions.assertEquals(50, skill.getMasteryExp());
    }
}
