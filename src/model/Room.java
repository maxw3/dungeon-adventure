package model;

import java.util.ArrayList;
import java.util.Objects;

public class Room {

    private final ArrayList<Item> myItems;
    private final ArrayList<AbstractDungeonCharacter> myDungeonCharacters;
    private final int myRow;
    private final int myCol;

    private Room myNorthRoom = null;
    private Room myEastRoom = null;
    private Room mySouthRoom = null;
    private Room myWestRoom = null;

    Room() {
        this(0, 0);
    }

    Room(final int theRow, final int theCol) {
        myItems = new ArrayList<Item>();
        myDungeonCharacters = new ArrayList<AbstractDungeonCharacter>();
        myRow = theRow;
        myCol = theCol;
    }

    Room(final Room theNorthRoom, final Room theEastRoom, final Room theSouthRoom, final Room theWestRoom, final int theRow, final int theCol){
        this(theRow, theCol);

        myNorthRoom = theNorthRoom;
        myEastRoom = theEastRoom;
        mySouthRoom = theSouthRoom;
        myWestRoom = theWestRoom;
    }

    public final ArrayList<AbstractDungeonCharacter> getCharacters(){
        return myDungeonCharacters;
    }

    public final void addCharacter(final AbstractDungeonCharacter theCharacter){
        myDungeonCharacters.add(theCharacter);
    }

    public final void removeCharacter(final AbstractDungeonCharacter theCharacter){
        myDungeonCharacters.remove(theCharacter);
    }

    public final void setRooms(final Room theNorthRoom, final Room theEastRoom, final Room theSouthRoom, final Room theWestRoom){
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

    public final boolean canWalkNorth(){
        boolean canWalk = false;

        if(myNorthRoom != null){
            canWalk = true;
        }

        return canWalk;
    }

    public final boolean canWalkEast(){
        boolean canWalk = false;

        if(myEastRoom != null){
            canWalk = true;
        }

        return canWalk;
    }

    public final boolean canWalkSouth(){
        boolean canWalk = false;

        if(mySouthRoom != null){
            canWalk = true;
        }

        return canWalk;
    }

    public final boolean canWalkWest(){
        boolean canWalk = false;

        if(myWestRoom != null){
            canWalk = true;
        }

        return canWalk;
    }

    public final int getRow() {
        return myRow;
    }
    public final int getCol() {
        return myCol;
    }

    public final void setNorthRoom(final Room theNorthRoom){
        myNorthRoom = theNorthRoom;
    }

    public final void setEastRoom(final Room theEastRoom) {
        myEastRoom = theEastRoom;
    }

    public final void setSouthRoom(final Room theSouthRoom){
        mySouthRoom = theSouthRoom;
    }

    public final void setWestRoom(final Room theWestRoom) {
        myWestRoom = theWestRoom;
    }

    @Override
    public boolean equals(final Object theObject) {
        if (!theObject.getClass().getSimpleName().equals("Room")) {
            return false;
        }
        Room otherRoom = (Room)theObject;
        return myDungeonCharacters.equals(otherRoom.myDungeonCharacters) && myItems.equals(otherRoom.myItems)
                && myRow == otherRoom.myRow && myCol == otherRoom.myCol;
    }

    @Override
    public int hashCode() {
        return Objects.hash(myDungeonCharacters, myItems, myRow, myCol);
    }
}
