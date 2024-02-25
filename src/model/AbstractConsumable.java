package model;

import enums.ItemType;

public abstract class AbstractConsumable extends AbstractEquipment {
    private static final ItemType MY_TYPE = ItemType.CONSUMABLE;
    private int myQuantity;
    public AbstractConsumable(final String theName) {
        this(theName, 0);
    }
    public AbstractConsumable(final String theName, final int theQuantity) {
        super(theName);
        setQuantity(theQuantity);
    }

    public int getQuantity() {
        return myQuantity;
    }

    @Override
    public String getType() {
        return MY_TYPE.name();
    }

    @Override
    public String toString() {
        return myQuantity + " " + getName();
    }

    public void triggerEffect() {
        if (theQuantity <= 0) {
            throw new IllegalArgumentException("The quantity is less than 0: Is " + theQuantity);
        }
        myQuantity--;
    }
    public final void add(){
        myQuantity++;
    }
}
