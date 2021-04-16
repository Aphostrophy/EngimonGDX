package com.ungabunga;

import com.ungabunga.model.enums.IElements;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ExampleTest {
    @Test
    void simpleAssertionTest() {
        Assertions.assertEquals(IElements.ELECTRIC, IElements.ELECTRIC);
    }
}
