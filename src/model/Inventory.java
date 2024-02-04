package model;

import java.util.ArrayList;

public class Inventory {

    private final ArrayList<AbstractItem> myItems;
    private int mySize;

    public Inventory() {
        myItems = new ArrayList<>();
        mySize = 0;
    }

    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Inventory is size ").append(mySize).append(": ").append(myItems.toString());
        return sb.toString();
    }

    public String getContents() {
        return myItems.toString();
    }

    public AbstractItem getItem(final int theIndex) {
        if (theIndex >= mySize) {
            throw new IllegalArgumentException("The index is out of bounds: Is " + theIndex);
        }
        return myItems.get(theIndex);
    }

    public void addItem(final AbstractItem theItem) {
        myItems.add(theItem);
    }

    /* public void useItem(final AbstractItem theItem) {
         We cannot directly feed it an "AbstractItem" because not all AbstractItems
         have an effect i.e. Pillars of OO.
    } */
}
