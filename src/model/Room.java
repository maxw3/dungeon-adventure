package model;

import java.util.ArrayList;
import java.util.Objects;

public final class Room {

    private final ArrayList<Item> myItems;
    private final ArrayList<AbstractDungeonCharacter> myDungeonCharacters;
    private final int myRow;
    private final int myCol;

    private Room myNorthRoom;
    private Room myEastRoom;
    private Room mySouthRoom;
    private Room myWestRoom;
    private boolean myExplored;

    private Room() {
        this(0, 0);
    }

    Room(final int theRow, final int theCol) {
        myItems = new ArrayList<Item>();
        myDungeonCharacters = new ArrayList<AbstractDungeonCharacter>();
        myRow = theRow;
        myCol = theCol;
        myExplored = false;
    }

    Room(final Room theNorthRoom, final Room theEastRoom, final Room theSouthRoom, final Room theWestRoom, final int theRow, final int theCol) {
        this(theRow, theCol);

        myNorthRoom = theNorthRoom;
        myEastRoom = theEastRoom;
        mySouthRoom = theSouthRoom;
        myWestRoom = theWestRoom;
    }

    public ArrayList<AbstractDungeonCharacter> getCharacters() {
        return myDungeonCharacters;
    }

    public ArrayList<Item> getItems() {
        return myItems;
    }

    public final boolean isExplored() {
        return myExplored;
    }

    public void setExplored(final boolean theState) {
        myExplored = theState;
    }
    public void addCharacter(final AbstractDungeonCharacter theCharacter) {
        myDungeonCharacters.add(theCharacter);
    }

    public void removeCharacter(final AbstractDungeonCharacter theCharacter) {
        myDungeonCharacters.remove(theCharacter);
        if(theCharacter instanceof Hero) {
            ((Hero) theCharacter).setPosition(myRow, myCol);
        }
    }

    public void removeItem(final Item theItem) {
        myItems.remove(theItem);
    }

    public void emptyRoom() {
        myItems.clear();
        myDungeonCharacters.clear();
    }

    public void setRooms(final Room theNorthRoom, final Room theEastRoom, final Room theSouthRoom, final Room theWestRoom) {
        myNorthRoom = theNorthRoom;
        myEastRoom = theEastRoom;
        mySouthRoom = theSouthRoom;
        myWestRoom = theWestRoom;
    }

    /*default*/ void addItem(final Item theEquipment) {
        if (theEquipment == null) {
            throw new IllegalArgumentException("The item is null.");
        }
        myItems.add(theEquipment);
    }

    public boolean canWalkNorth() {
        return myNorthRoom != null;
    }

    public boolean canWalkEast() {
        return myEastRoom != null;
    }

    public boolean canWalkSouth() {
        return mySouthRoom != null;
    }

    public boolean canWalkWest() {
        return myWestRoom != null;
    }

    public Room getNorth() {
        return myNorthRoom;
    }

    public Room getEast() {
        return myEastRoom;
    }

    public Room getSouth() {
        return mySouthRoom;
    }

    public Room getWest() {
        return myWestRoom;
    }


    public int getRow() {
        return myRow;
    }
    public int getCol() {
        return myCol;
    }

    public void setNorthRoom(final Room theNorthRoom) {
        myNorthRoom = theNorthRoom;
    }

    public void setEastRoom(final Room theEastRoom) {
        myEastRoom = theEastRoom;
    }

    public void setSouthRoom(final Room theSouthRoom) {
        mySouthRoom = theSouthRoom;
    }

    public void setWestRoom(final Room theWestRoom) {
        myWestRoom = theWestRoom;
    }

    @Override
    public boolean equals(final Object theObject) {
        if (!theObject.getClass().getSimpleName().equals("Room")) {
            return false;
        }
        final Room otherRoom = (Room)theObject;
        return myDungeonCharacters.equals(otherRoom.myDungeonCharacters) && myItems.equals(otherRoom.myItems)
                && myRow == otherRoom.myRow && myCol == otherRoom.myCol;
    }

    @Override
    public int hashCode() {
        return Objects.hash(myDungeonCharacters, myItems, myRow, myCol);
    }
}
