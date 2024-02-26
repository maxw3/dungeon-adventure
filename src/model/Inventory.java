package model;

import java.util.ArrayList;

public class Inventory {

    private final ArrayList<AbstractEquipment> myConsumableItems;
    private final ArrayList<Pillar> myPillars = new ArrayList<>();


    public Inventory() {
        myConsumableItems = new ArrayList<>();
    }

    public String toString() {
        return "Inventory is size " + myConsumableItems.size() + ": " + myConsumableItems;
    }

    public String getContents() {
        return myConsumableItems.toString();
    }

    private AbstractEquipment containsItem(final String theName) {
        for (final AbstractEquipment i : myConsumableItems) {
            if (i.getName().equalsIgnoreCase(theName)) {
                return i;
            }
        }
        return null;
    }
    public int getSize() {
        return myConsumableItems.size() + myPillars.size();
    }

    public AbstractEquipment getItem(final int theIndex) {
        if (theIndex >= myConsumableItems.size()) {
            throw new IllegalArgumentException("The index is out of bounds: Is " + theIndex);
        }
        return myConsumableItems.get(theIndex);
    }

    public void addItem(final AbstractEquipment theItem) {
        if (theItem == null) {
            throw new IllegalArgumentException("The Item is null.");
        }
        final AbstractEquipment firstInstance = containsItem(theItem.getName());
        if (firstInstance != null) {
            if (theItem.getType().equals("CONSUMABLE")) {
                final AbstractConsumable newConsumable = (AbstractConsumable) theItem;
                final AbstractConsumable oldConsumable = (AbstractConsumable) firstInstance;
                oldConsumable.setQuantity(newConsumable.getQuantity() + oldConsumable.getQuantity());
            } else {
                myConsumableItems.add(theItem);
            }
        } else {
            myConsumableItems.add(theItem);
        }
    }

    public void useItem(final AbstractEquipment theItem) {
        if (theItem == null) {
            throw new IllegalArgumentException("The Item is null.");
        }
        final AbstractEquipment firstInstance = containsItem(theItem.getName());
        if (firstInstance != null) {
            if (theItem.getType().equals("CONSUMABLE")) {
                final AbstractConsumable oldConsumable = (AbstractConsumable) firstInstance;
                oldConsumable.triggerEffect();
                if (oldConsumable.getQuantity() <= 0) {
                    myConsumableItems.remove(oldConsumable);
                }
            } /*
             else if is permanent{
            } */
            else {
                throw new IllegalArgumentException("The Item has no active ability. Is not CONSUMABLE or PERMANENT");
            }
        }
    }
}
