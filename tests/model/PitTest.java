package model;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class PitTest {

    private final Pit testPit = new Pit();
    @Test
    void getImage() {
        assertEquals("resources\\Pit.png", testPit.getImage());
    }

    @Test
    void getName() {
        assertEquals("Pit", testPit.getName());
    }

    @Test
    void getType() {
        assertEquals("PIT", testPit.getType());
    }

    @Test
    void activate() throws SQLException {
        Hero hero = new Warrior("Test");
        testPit.activate(hero);
        assertTrue(hero.getHP() < hero.getMaxHP());
    }
}