package model;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class HealthPotionTest {

    HealthPotion myTestPotion = new HealthPotion();
    @Test
    void testGetQuantity() {
        assertEquals(1, myTestPotion.getQuantity());
    }

    @Test
    void testToString() {
        assertEquals("The Health Potion heals for 50% of the model.Hero's maximum Hit Points.", myTestPotion.toString());
    }

    @Test
    void testAdd() {
        myTestPotion.add();
        assertEquals(2, myTestPotion.getQuantity());
    }

    @Test
    void testGetName() {
        assertEquals("Health Potion", myTestPotion.getName());
    }

    @Test
    void testGetType() {
        assertEquals("CONSUMABLE", myTestPotion.getType());
    }

    @Test
    void testTriggerEffect() throws SQLException {
        DungeonLogic.getDungeonInstance().createCharacter("Test", 0);
        Hero hero = DungeonLogic.getDungeonInstance().getHero();
        hero.healOrDamage(-hero.getMaxHP() / 2);
        myTestPotion.triggerEffect();
        assertEquals(hero.getMaxHP(), hero.getHP());
        assertEquals(0, myTestPotion.getQuantity());
    }
}