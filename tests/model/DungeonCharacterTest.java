package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DungeonCharacterTest {
    protected DungeonCharacter testDC = new DungeonCharacter() {
        @Override
        protected String skillDescription() {
            return null;
        }
    };
    @Test
    void testConstructor() {
        assertEquals(-1, testDC.myMaxHP);
        assertEquals(-1, testDC.myHP);
        assertEquals(0, testDC.myAttack);
        assertEquals(0, testDC.myBlockChance);
        assertEquals(0, testDC.myAtkSpd);
        //assertEquals(0,testDC.getPosition());
    }

    @Test
    void setMaxHP() {
        testDC.setMaxHP(10);
        assertEquals(10,testDC.myMaxHP);
    }

    @Test
    void increaseMaxHP() {
        testDC.setMaxHP(10);
        testDC.increaseMaxHP(10);
        assertEquals(20, testDC.myMaxHP);
    }

    @Test
    void multiplyMaxHP() {
        testDC.setMaxHP(10);
        testDC.multiplyMaxHP(10);
        assertEquals(100, testDC.myMaxHP);
    }

    @Test
    void setPosition() {
    }

    @Test
    void healOrDamage() {
        testDC.setMaxHP(10);
        testDC.healOrDamage(-5);
        assertEquals(5,testDC.myHP);
        testDC.healOrDamage(5);
        assertEquals(10,testDC.myHP);
    }

    @Test
    void setAttack() {
        testDC.setAttack(19);
        assertEquals(19, testDC.myAttack);
    }

    @Test
    void increaseAttack() {
        testDC.setAttack(19);
        testDC.increaseAttack(5);
        assertEquals(24,testDC.myAttack);
    }

    @Test
    void multiplyAttack() {
        testDC.setAttack(19);
        testDC.multiplyAttack(2);
        assertEquals(38,testDC.myAttack);
    }

    @Test
    void setAtkSpd() {
        testDC.setAtkSpd(10);
        assertEquals(10,testDC.myAtkSpd);
    }

    @Test
    void increaseAtkSpd() {
        testDC.setAtkSpd(10);
        testDC.increaseAtkSpd(5);
        assertEquals(15,testDC.myAtkSpd);
        testDC.increaseAtkSpd(-5);
        assertEquals(10,testDC.myAtkSpd);
    }

    @Test
    void multiplyAtkSpd() {
        testDC.setAtkSpd(10);
        testDC.multiplyAtkSpd(10);
        assertEquals(100,testDC.myAtkSpd);
    }

    @Test
    void setHitChance() {
        testDC.setHitChance(30);
        assertEquals(30, testDC.myHitChance);
    }

    @Test
    void increaseHitChance() {
        testDC.setHitChance(30);
        testDC.increaseHitChance(5);
        assertEquals(35,testDC.myHitChance);
        testDC.increaseHitChance(-5);
        assertEquals(30, testDC.myHitChance);
    }

    @Test
    void isBlocked() {
    }

    @Test
    void getMaxHP() {
        testDC.setMaxHP(20);
        assertEquals(20, testDC.myMaxHP);
    }

    @Test
    void getHP() {
        testDC.setMaxHP(20);
        testDC.healOrDamage(-10);
        assertEquals(10,testDC.getHP());
    }

    @Test
    void setBlockChance() {
        testDC.setBlockChance(20);
        assertEquals(20, testDC.myBlockChance);
    }

    @Test
    void getBlockChance() {
    }

    @Test
    void getAtkSpd() {
        testDC.setAtkSpd(20);
        assertEquals(20,testDC.myAtkSpd);
    }

    @Test
    void getHitChance() {
        testDC.setHitChance(20);
        assertEquals(20, testDC.getHitChance());

    }

    @Test
    void getAttack() {
        testDC.setAttack(10);
        assertEquals(10, testDC.getAttack());
    }

    @Test
    void getPosition() {
    }

    @Test
    void attack() {
    }

    @Test
    void skill() {
    }

    @Test
    void skillDescription() {
    }

    @Test
    void roll() {
    }

 //   @Test
//    void testToString() {
//        testDC.setMaxHP(10);
//        testDC.setBlockChance(20);
//        testDC.setAttack(30);
//        testDC.setAtkSpd(40);
//        testDC.healOrDamage(5);
//        testDC.setHitChance(50);
//        String expected = "Name: null\nHP: 15/10\nAttack: 30\nHit Chance: 50\nBlock Rate: 20\nSkill: null\n";
//        assertEquals(expected, testDC.toString());
//
//    }
}