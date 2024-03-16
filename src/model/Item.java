/*
 *  Dungeon Adventure Project for TCSS 360
 *  Winter 2024, Jordan, Terence, Max, and Gabriel
 */

package model;

/**
 * Interface of items
 * @author Jordan, Max, Gabriel, Terence
 * @version Winter 2024
 */
public interface Item {
    /**
     * Getter for the name of the item
     * @return the Name
     */
    String getName();

    /**
     * Getter for the type of item
     * @return What kind of item is this?
     */
    String getType();

    default String getImage() {
        return "resources\\" + getName() + ".png";
    }
}
