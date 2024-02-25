package model;

import enums.ItemType;

public abstract class AbstractEquipment implements Item {

    private static final ItemType MY_TYPE = ItemType.EQUIPMENT;
    private final String myName;

    public AbstractEquipment(final String theName) {
        if (theName == null) {git
            throw new IllegalArgumentException("The name is null.");
        }
        myName = theName;
    }

    @Override
    public String getName() {
        return myName;
    }

    @Override
    public String getType() {
        return MY_TYPE.name();
    }

    @Override
    public String toString() {
        return myName;
    }
}
