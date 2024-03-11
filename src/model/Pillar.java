package model;
import enums.ItemType;

import java.io.Serializable;

/**
 * Pillar class
 * equipment to collect throughout the dungeon
 * obtaining one transports the hero to the next floor
 */
public final class Pillar extends AbstractEquipment implements Serializable {
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
