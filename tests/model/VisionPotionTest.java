package model;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class VisionPotionTest {

    private final VisionPotion myTestPotion = new VisionPotion();

    @Test
    void testToString() {
        assertEquals("The Vision Potion extends vision to enable the user to have information on the contents of adjacent rooms.", myTestPotion.toString());
    }

    @Test
    void triggerEffect() {
        DungeonLogic dungeon = DungeonLogic.getDungeonInstance();
        Set<Room> neighbors = dungeon.getNeighbors(dungeon.getCurrentRoom());
        myTestPotion.triggerEffect();
        for (Room neighbor : neighbors) {
            assertTrue(neighbor.isVisible());
        }
    }
}