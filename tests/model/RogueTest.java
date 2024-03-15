package model;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class RogueTest {

    private final Rogue testRogue = new Rogue("Test");

    RogueTest() throws SQLException {
    }

    @Test
    void skill() {
        String s = testRogue.skill(testRogue);
        String expected = "You gain haste allowing you to attack faster and dodge more!" + System.lineSeparator() +
                "You dealt ";
        s = s.substring(0, expected.length());
        assertEquals(expected, s);
    }

    @Test
    void skillDescription() {
        assertEquals("You gain haste allowing you to attack faster and dodge more!", testRogue.skillDescription());
    }
}