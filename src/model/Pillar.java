package model;
import enums.ItemType;

public final class Pillar extends AbstractEquipment {

    private static final ItemType MY_TYPE = ItemType.PILLAR;

    public Pillar (final String theName){
        super(theName);
    }
}
