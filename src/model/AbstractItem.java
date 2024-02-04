package model;

public abstract class AbstractItem {

    private final String myName;

    public AbstractItem(final String theName) {
        if (theName == null) {
            throw new IllegalArgumentException("The name is null.");
        }
        myName = theName;
    }

    public String toString() {
        return myName;
    }
}
