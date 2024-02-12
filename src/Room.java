import java.util.ArrayList;

import model.Item;

public class Room {

    private final ArrayList<Item> myItems;
    private final ArrayList<DungeonCharacter> myDungeonCharacters;
    
    private Room myNorthRoom;

    private boolean myNorth;
    private boolean myEast;
    private boolean mySouth;
    private boolean myWest;
    private int myEvent;
    private boolean myExplored;

    Room(){
        myItems = new ArrayList<Item>();
        myDungeonCharacters = new ArrayList<DungeonCharacter>();

        myNorth = true;
        myEast = true;
        mySouth = true;
        myWest = true;
    }

    Room(boolean theNorth, boolean theEast, boolean theSouth, boolean theWest){
        myItems = new ArrayList<Item>();
        myDungeonCharacters = new ArrayList<DungeonCharacter>();

        myNorth = theNorth;
        myEast = theEast;
        mySouth = theSouth;
        myWest = theWest;
    }

    public ArrayList<DungeonCharacter> getCharacters(){
        return myDungeonCharacters;
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

}