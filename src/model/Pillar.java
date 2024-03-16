/*
 *  Dungeon Adventure Project for TCSS 360
 *  Winter 2024, Jordan, Terence, Max, and Gabriel
 */

package model;
import enums.ItemType;

/**
 * Pillar class
 * equipment to collect throughout the dungeon
 * obtaining one transports the hero to the next floor
 * @author Jordan, Max, Gabriel, Terence
 * @version Winter 2024
 */
public final class Pillar extends AbstractEquipment {
    /**
     * The type of item this is
     */
    private static final ItemType MY_TYPE = ItemType.PILLAR;

    /**
     * Constructor
     * @param theName The name of the Pillar
     */
    public Pillar (final String theName){
        super(theName);
    }
    @Override
    public String getType() {
        return MY_TYPE.name();
    }
}
