import model.AbstractEquipment;

import java.util.ArrayList;

public class Room {

    //private final Item[] myItems;
    private final ArrayList<DungeonCharacter> myDungeonCharacters = new ArrayList<>();
    private final ArrayList<AbstractEquipment> myEquipment = new ArrayList<>();
    private boolean myNorth;
    private boolean myEast;
    private boolean mySouth;
    private boolean myWest;
    private int myEvent;
    private boolean myExplored;

    Room() {
        myNorth = true;
        myEast = true;
        mySouth = true;
        myWest = true;
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

    public Object[] getEquipment() {
        return myEquipment.toArray();
    }

    /*default*/ void addCharacter(final DungeonCharacter theCharacter) {
        if (theCharacter == null) {
            throw new IllegalArgumentException("The character is null.");
        }
        myDungeonCharacters.add(theCharacter);
    }

    /*default*/ void addEquipment(final AbstractEquipment theEquipment) {
        if (theEquipment == null) {
            throw new IllegalArgumentException("The equipment is null.");
        }
        myEquipment.add(theEquipment);
    }
}