package model;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class MonsterTest {

    private final Monster testMonster = MonsterFactory.createSkeleton(0);

    MonsterTest() throws SQLException {
    }

    @Test
    void skill() {
        String s = "";
        int count = 0;
        testMonster.healOrDamage(-1);
        while (s.equals("") && count < 100) {
            s = testMonster.skill(testMonster);
            count++;
        }
        String expected = "Skeleton healed ";
        s = s.substring(0, expected.length());
        assertEquals(expected, s);
        assertEquals(testMonster.getMaxHP(), testMonster.getHP());
    }

    @Test
    void skillDescription() {
        assertEquals("Skeleton has a chance to heal itself after every round.", testMonster.skillDescription());
    }

    @Test
    void healOrDamage() throws SQLException {
        Monster ratKing = MonsterFactory.createBoss(4);
        int atkSpeed = ratKing.getAtkSpd();
        ratKing.healOrDamage(-500);
        assertTrue(atkSpeed > ratKing.getAtkSpd());
        assertTrue(ratKing.getHP() < ratKing.getMaxHP());
    }
}