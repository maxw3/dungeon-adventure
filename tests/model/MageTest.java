package model;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class MageTest {

    private final Mage testMage = new Mage("Test");

    MageTest() throws SQLException {
    }

    @Test
    void skill() {
        testMage.healOrDamage(-(testMage.getMaxHP() / 4));
        testMage.skill(testMage);
        assertEquals(testMage.getMaxHP(), testMage.getHP());
    }

    @Test
    void skillDescription() {
        assertEquals("Mage heals itself.", testMage.skillDescription());
    }
}