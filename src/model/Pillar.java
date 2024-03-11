package model;
import enums.ItemType;

import java.io.Serializable;

public final class Pillar extends AbstractEquipment implements Serializable {

    private static final ItemType MY_TYPE = ItemType.PILLAR;

    public Pillar (final String theName){
        super(theName);
    }
    @Override
    public String getType() {
        return MY_TYPE.name();
    }
}
