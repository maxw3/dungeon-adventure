package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

/**
 * The room class for the dungeon
 * stores data of a single room
 * if its cardinal edges are traversable or not
 * if there are any characters or items in the room
 */
public final class Room implements Serializable {

    /**
     * The items in the room
     */
    private final ArrayList<Item> myItems;
    /**
     * the characters in the room
     */
    private final ArrayList<AbstractDungeonCharacter> myDungeonCharacters;
    /**
     * the X-coord of the room in relation to the floor layout
     */
    private final int myRow;
    /**
     * the Y-coord of the room in relation to the floor layout
     */
    private final int myCol;

    /**
     * the room north of this room
     */
    private Room myNorthRoom;
    /**
     * the room east of this room
     */
    private Room myEastRoom;
    /**
     * the room south of this room
     */
    private Room mySouthRoom;
    /**
     * the room west of this room
     */
    private Room myWestRoom;
    /**
     * has the room been explored by the hero?
     */
    private boolean myExplored;
    /**
     * Are the contents of the room visible to the hero?
     */
    private boolean myVisible;

    /**
     * private constructor to avoid calls
     */
    Room() {
        this(0, 0);
    }

    /**
     * Constructor
     * @param theRow the X-coord of the room
     * @param theCol the Y-coord of the room
     */
    Room(final int theRow, final int theCol) {
        myItems = new ArrayList<Item>();
        myDungeonCharacters = new ArrayList<AbstractDungeonCharacter>();
        myRow = theRow;
        myCol = theCol;
        myExplored = false;
        myVisible = false;
    }

    /**
     * Getter for myDungeonCharacters
     * @return the characters in the room
     */
    public ArrayList<AbstractDungeonCharacter> getCharacters() {
        return myDungeonCharacters;
    }

    /**
     * Getter for myItems
     * @return the items in the room
     */
    public ArrayList<Item> getItems() {
        return myItems;
    }

    /**
     * The visibility of the room
     * @return are the contents of the room visible?
     */
    public boolean isVisible() {
        return myVisible;
    }

    /**
     * the exploration state of the room
     * @return has the room already been explored by the hero?
     */
    public boolean isExplored() {
        return myExplored;
    }

    /**
     * setter for myExplored
     * @param theState has the room already been explored by the hero?
     */
    public void setExplored(final boolean theState) {
        myExplored = theState;
    }

    /**
     * Setter for myVisibility
     * @param theState are the contents of the room visible?
     */
    public void setVisibilty(final boolean theState) {
        myVisible = theState;
    }

    /**
     * Add a character to the room
     * @param theCharacter The character to add
     */
    public void addCharacter(final AbstractDungeonCharacter theCharacter) {
        myDungeonCharacters.add(theCharacter);
    }

    /**
     * remove a character from the room
     * @param theCharacter the character to remove
     */
    public void removeCharacter(final AbstractDungeonCharacter theCharacter) {
        myDungeonCharacters.remove(theCharacter);
        if(theCharacter instanceof Hero) {
            ((Hero) theCharacter).setPosition(myRow, myCol);
        }
    }

    /**
     * remove an item from the room
     * @param theItem the item to remove
     */
    public void removeItem(final Item theItem) {
        myItems.remove(theItem);
    }

    /**
     * empty out the room
     */
    public void emptyRoom() {
        myItems.clear();
        myDungeonCharacters.clear();
    }

    /**
     * Add an item to the room
     * @param theEquipment the item to add
     */
    void addItem(final Item theEquipment) {
        if (theEquipment == null) {
            throw new IllegalArgumentException("The item is null.");
        }
        myItems.add(theEquipment);
    }

    /**
     * can I go north from here?
     * @return can I go north from here?
     */
    public boolean canWalkNorth() {
        return myNorthRoom != null;
    }

    /**
     * can I go east from here?
     * @return can I go east from here?
     */
    public boolean canWalkEast() {
        return myEastRoom != null;
    }

    /**
     * can I go south from here?
     * @return can I go south from here?
     */
    public boolean canWalkSouth() {
        return mySouthRoom != null;
    }

    /**
     * can I go west from here?
     * @return can I go west from here?
     */
    public boolean canWalkWest() {
        return myWestRoom != null;
    }

    /**
     * What room is to the north of this room?
     * @return the room
     */
    public Room getNorth() {
        return myNorthRoom;
    }
    /**
     * What room is to the east of this room?
     * @return the room
     */
    public Room getEast() {
        return myEastRoom;
    }
    /**
     * What room is to the south of this room?
     * @return the room
     */
    public Room getSouth() {
        return mySouthRoom;
    }
    /**
     * What room is to the west of this room?
     * @return the room
     */
    public Room getWest() {
        return myWestRoom;
    }

    /**
     * Getter for myRow
     * @return the X-coord
     */
    public int getRow() {
        return myRow;
    }

    /**
     * Getter for myCol
     * @return the Y-coord
     */
    public int getCol() {
        return myCol;
    }

    /**
     * Setter for myNorthRoom
     * @param theNorthRoom the Room North of here
     */
    public void setNorthRoom(final Room theNorthRoom) {
        myNorthRoom = theNorthRoom;
    }

    /**
     * Setter for myEastRoom
     * @param theEastRoom the Room east of here
     */
    public void setEastRoom(final Room theEastRoom) {
        myEastRoom = theEastRoom;
    }
    /**
     * Setter for mySouthRoom
     * @param theSouthRoom the Room south of here
     */
    public void setSouthRoom(final Room theSouthRoom) {
        mySouthRoom = theSouthRoom;
    }
    /**
     * Setter for myWestRoom
     * @param theWestRoom the Room west of here
     */
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
