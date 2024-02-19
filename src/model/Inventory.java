package model;

import java.util.ArrayList;

public class Inventory {

    private final ArrayList<AbstractEquipment> myConsumableItems;
    private final AbstractEquipment[] myPillars;


    public Inventory() {
        myConsumableItems = new ArrayList<>();
        myPillars = new AbstractEquipment[4];
    }

    public String toString() {
        return "Inventory is size " + myConsumableItems.size() + ": " + myConsumableItems;
    }

    public String getContents() {
        return myConsumableItems.toString();
    }
    public int getSize() {
        return myConsumableItems.size() + myPillars.length;
    }

    public AbstractEquipment getItem(final int theIndex) {
        if (theIndex >= myConsumableItems.size()) {
            throw new IllegalArgumentException("The index is out of bounds: Is " + theIndex);
        }
        return myConsumableItems.get(theIndex);
    }

    public void addItem(final AbstractEquipment theItem) {
        myConsumableItems.add(theItem);
    }

    public void useItem(final AbstractEquipment theItem) {
        if (theItem.getType().equals("CONSUMABLE")) {
            ((AbstractConsumable) theItem).triggerEffect();
        } /* else if (theItem.getType().equals("PERMANENT")){
               example code here
        } */
        else {
            throw new IllegalArgumentException("The Item has no active ability. Is not CONSUMABLE or PERMANENT");
        }
    }
}
