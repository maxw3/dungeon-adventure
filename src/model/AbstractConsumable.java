/*
 *  Dungeon Adventure Project for TCSS 360
 *  Winter 2024, Jordan, Terence, Max, and Gabriel
 */

package model;

import enums.ItemType;

/**
 * Abstract class for consumable items
 * @author Jordan, Max, Gabriel, Terence
 * @version Winter 2024
 */
public abstract class AbstractConsumable extends AbstractEquipment {
    /**
     * The type of consumable this is
     */
    private static final ItemType MY_TYPE = ItemType.CONSUMABLE;
    /**
     * The amount of this item that's available.
     */
    private int myQuantity;

    /**
     * Constructor
     *
     * @param theName The name of the item
     */
    public AbstractConsumable(final String theName) {
        this(theName, 1);
    }

    /**
     * Constructor
     *
     * @param theName   The name of the item
     * @param theQuantity   The initial amount of the item
     */
    public AbstractConsumable(final String theName, final int theQuantity) {
        super(theName);
        myQuantity = theQuantity;
    }

    /**
     * Getter for myQuantity
     *
     * @return the amount of this item that's available
     */
    public int getQuantity() {
        return myQuantity;
    }

    /**
     * Getter for MY_TYPE
     *
     * @return the type of item this is
     */
    @Override
    public String getType() {
        return MY_TYPE.name();
    }

    @Override
    public String toString() {
        return myQuantity + " " + getName();
    }

    /**
     * Decreases amount by 1 due to consumption
     */
    public void triggerEffect() {
        if (myQuantity <= 0) {
            throw new IllegalArgumentException("The quantity is less than 0: Is " + myQuantity);
        }
        myQuantity--;
    }

    /**
     * Increases amount by 1 by obtaining it.
     */
    public final void add(){
        myQuantity++;
    }
}
