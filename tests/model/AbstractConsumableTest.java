package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AbstractConsumableTest {
    AbstractConsumable testCons = new AbstractConsumable("Pepsi",3) {
    };

    @Test
    void getQuantity() {
        assertEquals(3, testCons.getQuantity());

    }

    @Test
    void getType() {
        assertEquals("CONSUMABLE", testCons.getType());

    }

    @Test
    void testToString() {
        String expected = "3 Pepsi";
        assertEquals(expected, testCons.toString());
    }

    @Test
    void setQuantity() {
        testCons.setQuantity(4);
        assertEquals(4,testCons.getQuantity());
    }

    @Test
    void triggerEffect() {
        testCons.setQuantity(10);
        testCons.triggerEffect();
        assertEquals(9,testCons.getQuantity());
    }
}