package model;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class HeroTest {

    private final Hero testHero = new Mage("Test");

    HeroTest() throws SQLException {
    }

    @Test
    void getMyClass() {
        assertEquals("Mage", testHero.getMyClass());
    }

    @Test
    void getCharName() {
        assertEquals("Test", testHero.getCharName());
    }

    @Test
    void levelUp() throws SQLException {
        final int health = testHero.getMaxHP();
        final int attack = testHero.getAttack();
        final int hitChance = testHero.getHitChance();
        testHero.levelUp();
        assertTrue(testHero.getMaxHP() > health && testHero.getAttack() > attack && testHero.getHitChance() > hitChance);
    }
}