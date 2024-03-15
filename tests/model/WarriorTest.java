package model;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class WarriorTest {

    private final Warrior testWarrior = new Warrior("Test");

    WarriorTest() throws SQLException {
    }

    @Test
    void skill() {
        String s = testWarrior.skill(testWarrior);
        String expected = "You perform a giant haphazard swing at the monster and dealt ";
        s = s.substring(0, expected.length());
        assertEquals(expected, s);
    }

    @Test
    void skillDescription() {
        assertEquals("You perform a giant haphazard swing at the monster", testWarrior.skillDescription());
    }
}