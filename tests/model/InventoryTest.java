package model;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class InventoryTest {

    private static final String NEWLINE = System.lineSeparator();
    private final Inventory testInventory = new Inventory();
    @Test
    void testToString() {
        assertEquals("You have 0 health potions and 0 vision potions for a total of 0 items." + NEWLINE +
                "Pillar of Abstraction: X" + NEWLINE +
                "Pillar of Encapsulation: X" + NEWLINE +
                "Pillar Inheritance: X" + NEWLINE +
                "Pillar of Polymorphism: X", testInventory.toString());
        testInventory.addItem(new HealthPotion());
        testInventory.addItem(new Pillar("Encapsulation"));
        assertEquals("You have 1 health potions and 0 vision potions for a total of 1 items." + NEWLINE +
                "Pillar of Abstraction: X" + NEWLINE +
                "Pillar of Encapsulation: Obtained" + NEWLINE +
                "Pillar Inheritance: X" + NEWLINE +
                "Pillar of Polymorphism: X", testInventory.toString());
    }

    @Test
    void testGetSize() {
        testInventory.addItem(new HealthPotion());
        assertEquals(1, testInventory.getSize());
    }

    @Test
    void testGetCount() {
        assertEquals(0, testInventory.getCount(new HealthPotion()));
        testInventory.addItem(new HealthPotion());
        testInventory.addItem(new HealthPotion());
        assertEquals(2, testInventory.getCount(new HealthPotion()));
    }

    @Test
    void testAddItem() {
        testInventory.addItem(new HealthPotion());
        testInventory.addItem(new Pillar("Abstraction"));
        assertEquals(1, testInventory.getSize());
        assertEquals("You have 1 health potions and 0 vision potions for a total of 1 items." + NEWLINE +
                "Pillar of Abstraction: Obtained" + NEWLINE +
                "Pillar of Encapsulation: X" + NEWLINE +
                "Pillar Inheritance: X" + NEWLINE +
                "Pillar of Polymorphism: X", testInventory.toString());
    }

    @Test
    void testUseItem() throws SQLException {
        DungeonLogic dungeon = DungeonLogic.getDungeonInstance();
        dungeon.createCharacter("Test", 0);
        testInventory.addItem(new HealthPotion());
        testInventory.addItem(new HealthPotion());
        testInventory.useItem(new HealthPotion());
        assertEquals(1, testInventory.getSize());
        assertFalse(testInventory.useItem(new Pillar("Abstraction")));
    }

    @Test
    void testGetHPPotionAmount() {
        testInventory.addItem(new HealthPotion());
        testInventory.addItem(new HealthPotion());
        assertEquals(2, testInventory.getHPPotionAmount());
    }

    @Test
    void testGetVisionPotionAmount() {
        testInventory.addItem(new VisionPotion());
        testInventory.addItem(new VisionPotion());
        assertEquals(2, testInventory.getVisionPotionAmount());
    }
}