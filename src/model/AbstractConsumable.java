package model;

import enums.ItemType;

public abstract class AbstractConsumable extends AbstractEquipment {
    private static final ItemType MY_TYPE = ItemType.CONSUMABLE;
    private int myQuantity;
    public AbstractConsumable(final String theName, final int theQuantity) {
        super(theName);
        setQuantity(theQuantity);
    }

    public int getQuantity() {
        return myQuantity;
    }

    @Override
    public String toString() {
        return myQuantity + " " + getName();
    }

    public void setQuantity(final int theQuantity) {
        if (myQuantity <= 0) {
            throw new IllegalArgumentException("The quantity is less than 0: Is " + theQuantity);
        }
        myQuantity = theQuantity;
    }
    public abstract void triggerEffect();
}
