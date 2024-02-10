package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HealthPotionTest {

    HealthPotion myTestPotion = new HealthPotion("Health Potion", 2);
    @Test
    void testGetQuantity() {
        assertEquals(2, myTestPotion.getQuantity());
    }

    @Test
    void testToString() {
        assertEquals("2 Health Potion", myTestPotion.toString());
    }

    @Test
    void testSetQuantity() {
        myTestPotion.setQuantity(1);
        assertEquals(1, myTestPotion.getQuantity());
    }

    @Test
    void testGetName() {
        assertEquals("Health Potion", myTestPotion.getName());
    }

    @Test
    void testGetType() {
        assertEquals("CONSUMABLE", myTestPotion.getType());
    }

    @Test
    void testTriggerEffect() {
        // Does nothing currently.
    }
}