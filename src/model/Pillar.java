package model;
import enums.ItemType;

public final class Pillar implements Item {

    private static final ItemType MY_TYPE = ItemType.PILLAR;
    private final String myName;

    public Pillar(final String theName) {
        if (theName == null) {
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
