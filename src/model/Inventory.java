package model;

import java.util.ArrayList;

public class Inventory {

    private final ArrayList<AbstractEquipment> myItems;


    public Inventory() {
        myItems = new ArrayList<>();
    }

    public String toString() {
        return "Inventory is size " + myItems.size() + ": " + myItems;
    }

    public String getContents() {
        return myItems.toString();
    }
    public int getSize() {
        return myItems.size();
    }

    public AbstractEquipment getItem(final int theIndex) {
        if (theIndex >= myItems.size()) {
            throw new IllegalArgumentException("The index is out of bounds: Is " + theIndex);
        }
        return myItems.get(theIndex);
    }

    public void addItem(final AbstractEquipment theItem) {
        myItems.add(theItem);
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
