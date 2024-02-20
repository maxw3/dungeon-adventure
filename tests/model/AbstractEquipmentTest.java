package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AbstractEquipmentTest {
    String equipment = "Pepsi";
    AbstractEquipment testEquip = new AbstractEquipment(equipment) {
    };

    @Test
    void getName() {
        String expected = "Pepsi";
        assertEquals(expected, testEquip.getName());
    }

    @Test
    void getType() {
        String expected = "EQUIPMENT";
        assertEquals(expected, testEquip.getType());
    }

    @Test
    void testToString() {
        String expected = "Pepsi";
        assertEquals(expected, testEquip.getName());
    }
}