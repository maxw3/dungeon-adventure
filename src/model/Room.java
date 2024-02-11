package model;

import enums.Direction;

import java.util.ArrayList;

public class Room {

    //private final Item[] myItems;
    private final ArrayList<DungeonCharacter> myDungeonCharacters = new ArrayList<>();
    private final ArrayList<Item> myItems = new ArrayList<>();
    private final int[] myPosition;
    private boolean myNorth;
    private boolean myEast;
    private boolean mySouth;
    private boolean myWest;
    private int myEvent;
    private boolean myExplored;

    Room() {
        this(new int[]{0, 0}, false, false, false, false);
    }

    Room(final int[] thePosition) {
        this(thePosition, false, false, false, false);
    }
    Room(final int[] thePosition, final boolean theNorth, final boolean theEast, final boolean theSouth, final boolean theWest) {
        myNorth = theNorth;
        myEast = theEast;
        mySouth = theSouth;
        myWest = theWest;
        myPosition = new int[]{thePosition[0], thePosition[1]};
    }

    public boolean getNorth(){
        return myNorth;
    }

    public boolean getWest(){
        return myWest;
    }

    public boolean getEast(){
        return myEast;
    }

    public boolean getSouth(){
        return mySouth;
    }

    public int[] getPosition() {
        return myPosition;
    }
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();

        // Print First Row
        sb.append('*');
        if(myNorth){
            sb.append("-");
        }else{
            sb.append("*");
        }
        sb.append("*\n");

        if(myWest){
            sb.append("|");
        }else{
            sb.append("*");
        }

        // Print Second Row
        if(myDungeonCharacters.size() > 1){
            sb.append('m');
        }else{
            sb.append(' ');
        }
        if(myEast){
            sb.append('|');
        }else{
            sb.append('*');
        }
        sb.append('\n');

        // Print 3rd Row
        sb.append('*');
        if(mySouth){
            sb.append('-');
        }else{
            sb.append('*');
        }
        sb.append("*\n");

        return sb.toString();
    }

    public Object[] getCharacters() {
        return myDungeonCharacters.toArray();
    }

    public Object[] getItem() {
        return myItems.toArray();
    }

    /*default*/ void addCharacter(final DungeonCharacter theCharacter) {
        if (theCharacter == null) {
            throw new IllegalArgumentException("The character is null.");
        }
        myDungeonCharacters.add(theCharacter);
    }

    /*default*/ void setHallway(final boolean theState, final Direction theDirection) {
        if (theDirection == null) {
            throw new IllegalArgumentException("The direction is null.");
        }
        if (theDirection == Direction.NORTH) {
            myNorth = theState;
        } else if (theDirection == Direction.SOUTH) {
            mySouth = theState;
        } else if (theDirection == Direction.EAST) {
            myEast = theState;
        } else {
            myWest = theState;
        }
    }
    /*default*/ void addItem(final Item theEquipment) {
        if (theEquipment == null) {
            throw new IllegalArgumentException("The item is null.");
        }
        myItems.add(theEquipment);
    }

    /*default*/ void setExplored(final boolean theState) {
        myExplored = theState;
    }
}