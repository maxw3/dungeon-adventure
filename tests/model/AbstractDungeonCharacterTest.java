package model;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class AbstractDungeonCharacterTest {

    private static final String NEWLINE = System.lineSeparator();
    private final AbstractDungeonCharacter testCharacter1 = MonsterFactory.createSkeleton(0);
    private final AbstractDungeonCharacter testCharacter2 = MonsterFactory.createSkeleton(0);

    AbstractDungeonCharacterTest() throws SQLException {
    }

    @Test
    void healOrDamage() {
        testCharacter1.healOrDamage(-10);
        assertEquals(testCharacter1.getMaxHP() - 10, testCharacter1.getHP());
        testCharacter1.healOrDamage(10);
        assertEquals(testCharacter1.getMaxHP(), testCharacter1.getHP());
    }

    @Test
    void attack() {
        testCharacter1.setHitChance(100);
        testCharacter1.attack(testCharacter2);
        assertTrue(testCharacter2.getMaxHP() > testCharacter2.getHP());
    }

    @Test
    void setMaxHP() {
        testCharacter1.setMaxHP(50);
        assertEquals(50, testCharacter1.getMaxHP());
    }

    @Test
    void setAttack() {
        testCharacter1.setAttack(1);
        assertEquals(1, testCharacter1.getAttack());
    }

    @Test
    void setAtkSpd() {
        testCharacter1.setAtkSpd(2);
        assertEquals(2, testCharacter1.getAtkSpd());
    }

    @Test
    void setHitChance() {
        testCharacter1.setHitChance(2);
        assertEquals(2, testCharacter1.getHitChance());
    }

    @Test
    void testToString() {
        assertEquals("Name: Skeleton" + NEWLINE + "HP: 100/100" + NEWLINE + "Attack: 50" + NEWLINE +
                "Hit Chance: 50" + NEWLINE + "Block Rate: 0" +
                NEWLINE + "Skill: Skeleton has a chance to heal itself after every round." + NEWLINE, testCharacter1.toString());
    }

    @Test
    void getImage() {
        assertEquals("resources\\" + "skeleton.png", testCharacter1.getImage());
    }

    @Test
    void isBlocked() throws SQLException {
        AbstractDungeonCharacter warrior = new Warrior("Test");
        boolean hit = false;
        boolean blocked = false;
        for (int i = 0; i < 100; i++) {
            if (warrior.isBlocked()) {
                blocked = true;
            } else {
                hit = true;
            }
            if (blocked && hit) {
                break;
            }
        }
        assertTrue(hit && blocked);
    }

    @Test
    void getName() {
        assertEquals("Skeleton", testCharacter1.getName());
    }

    @Test
    void getBlockChance() throws SQLException {
        AbstractDungeonCharacter warrior = new Warrior("Test");
        assertEquals(50, warrior.getBlockChance());
    }

    @Test
    void skillDescription() throws SQLException {
        AbstractDungeonCharacter warrior = new Warrior("Test");
        assertEquals("You perform a giant haphazard swing at the monster", warrior.skillDescription());
    }
}