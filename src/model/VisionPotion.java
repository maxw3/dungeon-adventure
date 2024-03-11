package model;

import java.util.Set;

/**
 * A consumable item that lets the user see the contents of the adjacent rooms
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
        final Room room = DungeonLogic.MY_INSTANCE.getCurrentRoom();
        final Set<Room> neighbors = DungeonLogic.MY_INSTANCE.getNeighbors(room);
        for (final Room r : neighbors) {
            DungeonLogic.MY_INSTANCE.reveal(r);
        }
    }

    @Override
    public String toString() {
        return "The Vision Potion extends vision to enable the user to have information on the contents of adjacent rooms.";
    }
}
