/*
 *  Dungeon Adventure Project for TCSS 360
 *  Winter 2024, Jordan, Terence, Max, and Gabriel
 */

package model;

import java.util.Set;

/**
 * A consumable item that lets the user see the contents of the adjacent rooms
 * @author Jordan, Max, Gabriel, Terence
 * @version Winter 2024
 */
public final class VisionPotion extends AbstractConsumable {
    /**
     * Default Constructor
     */
    public VisionPotion() {
        super("Vision Potion");
    }

    /**
     * increases vision of the hero making them able
     * to see the contents of the adjacent rooms
     */
    @Override
    public void triggerEffect() {
        super.triggerEffect();
        final Room room = DungeonLogic.getDungeonInstance().getCurrentRoom();
        final Set<Room> neighbors = DungeonLogic.getDungeonInstance().getNeighbors(room);
        for (final Room r : neighbors) {
            DungeonLogic.getDungeonInstance().reveal(r);
        }
    }

    @Override
    public String toString() {
        return "The Vision Potion extends vision to enable the user to have information on the contents of adjacent rooms.";
    }
}
