package model;

import enums.Direction;

import java.util.ArrayList;

public class ExperimentRoom {

    //private final Item[] myItems;
    private final ArrayList<DungeonCharacter> myDungeonCharacters = new ArrayList<>();
    private final ArrayList<Item> myItems = new ArrayList<>();
    private final int[] myPosition;
    private ExperimentRoom myNorth;
    private ExperimentRoom myEast;
    private ExperimentRoom mySouth;
    private ExperimentRoom myWest;
    private int myEvent;
    private boolean myExplored;

    ExperimentRoom() {
        this(new int[]{0, 0}, null, null, null, null);
    }

    ExperimentRoom(final int[] thePosition) {
        this(thePosition, null, null, null, null);
    }
    ExperimentRoom(final int[] thePosition, final ExperimentRoom theNorth, final ExperimentRoom theEast, final ExperimentRoom theSouth, final ExperimentRoom theWest) {
        myNorth = theNorth;
        myEast = theEast;
        mySouth = theSouth;
        myWest = theWest;
        myPosition = new int[]{thePosition[0], thePosition[1]};
    }

    public ExperimentRoom getNorth(){
        return myNorth;
    }

    public ExperimentRoom getWest(){
        return myWest;
    }

    public ExperimentRoom getEast(){
        return myEast;
    }

    public ExperimentRoom getSouth(){
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
        if(myNorth != null){
            sb.append("-");
        }else{
            sb.append("*");
        }
        sb.append("*\n");

        if(myWest != null){
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
        if(myEast != null){
            sb.append('|');
        }else{
            sb.append('*');
        }
        sb.append('\n');

        // Print 3rd Row
        sb.append('*');
        if(mySouth != null){
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

    /*default*/ void setHallway(final ExperimentRoom theRoom, final Direction theDirection) {
        if (theDirection == Direction.NORTH) {
            myNorth = theRoom;
        } else if (theDirection == Direction.SOUTH) {
            mySouth = theRoom;
        } else if (theDirection == Direction.EAST) {
            myEast = theRoom;
        } else {
            myWest = theRoom;
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