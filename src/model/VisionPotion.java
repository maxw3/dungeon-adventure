package model;

import java.util.Set;

public class VisionPotion extends AbstractConsumable {
    public VisionPotion(final int theQuantity) {
        super("Vision Potion", theQuantity);
    }

    @Override
    public void triggerEffect() {
        super.triggerEffect();
        Room room = DungeonLogic.MY_INSTANCE.getCurrentRoom();
        Set<Room> neighbors = DungeonLogic.MY_INSTANCE.getNeighbors(room);
        for (final Room r : neighbors) {
            DungeonLogic.MY_INSTANCE.reveal(r);
        }
    }

    @Override
    public String toString() {
        return "The Vision Potion extends vision to enable the user to have information on the contents of adjacent rooms.";
    }
}
