package com.ungabunga;

import com.ungabunga.model.entities.SkillItem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SkillItemTest {
    @Test
    public void getSkillItemNameTest() {
        SkillItem skillItem = new SkillItem("Spikes",17);
        Assertions.assertEquals("Spikes", skillItem.getName());
    }

    @Test
    public void getSkillItemAmountTest() {
        SkillItem skillItem = new SkillItem("Spikes",17);
        Assertions.assertEquals(1, skillItem.getAmount());
    }

    @Test
    public void compareSkillItemTest() {
        SkillItem skillItemA = new SkillItem("Spikes",17);
        SkillItem skillItemB = new SkillItem("Spikes",17);
        Assertions.assertEquals(true, skillItemA.compareTwoSkillItem(skillItemB));
    }

    @Test
    public void getSkillItemPowerTest() {
        SkillItem skillItem = new SkillItem("Spikes",17);
        Assertions.assertEquals(17, skillItem.getPower());
    }
}
