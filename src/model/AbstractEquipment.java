/*
 *  Dungeon Adventure Project for TCSS 360
 *  Winter 2024, Jordan, Terence, Max, and Gabriel
 */

package model;

import enums.ItemType;

import java.io.Serializable;

/**
 * Abstract class for non-consumable items
 * @author Jordan, Max, Gabriel, Terence
 * @version Winter 2024
 */
public abstract class AbstractEquipment implements Item, Serializable {

    /**
     * The type of Item
     */
    private static final ItemType MY_TYPE = ItemType.EQUIPMENT;
    /**
     * The name of the item
     */
    private final String myName;

    /**
     * Constructor
     * @param theName The name of the item
     */
    public AbstractEquipment(final String theName) {
        if (theName == null) {
            throw new IllegalArgumentException("The name is null.");
        }
        myName = theName;
    }

    /**
     * Getter for myName
     * @return the name of the item
     */
    @Override
    public String getName() {
        return myName;
    }

    /**
     * Getter for MY_TYPE
     * @return The type of item this is
     */
    @Override
    public String getType() {
        return MY_TYPE.name();
    }

    @Override
    public String toString() {
        return myName;
    }
}
