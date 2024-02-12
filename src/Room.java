import java.util.ArrayList;

import model.Item;

public class Room {

    private final ArrayList<Item> myItems;
    private final ArrayList<DungeonCharacter> myDungeonCharacters;

    private Room myNorthRoom = null;
    private Room myEastRoom = null;
    private Room mySouthRoom = null;
    private Room myWestRoom = null;

    Room(){
        myItems = new ArrayList<Item>();
        myDungeonCharacters = new ArrayList<DungeonCharacter>();
    }

    Room(Room theNorthRoom, Room theEastRoom, Room theSouthRoom, Room theWestRoom){
        myItems = new ArrayList<Item>();
        myDungeonCharacters = new ArrayList<DungeonCharacter>();

        myNorthRoom = theNorthRoom;
        myEastRoom = theEastRoom;
        mySouthRoom = theSouthRoom;
        myWestRoom = theWestRoom;
    }

    public ArrayList<DungeonCharacter> getCharacters(){
        return myDungeonCharacters;
    }

    public void addCharacter(DungeonCharacter theCharacter){
        myDungeonCharacters.add(theCharacter);
    }

    public void removeCharacter(DungeonCharacter theCharacter){
        myDungeonCharacters.remove(theCharacter);
    }

    public void setRooms(Room theNorthRoom, Room theEastRoom, Room theSouthRoom, Room theWestRoom){
        myNorthRoom = theNorthRoom;
        myEastRoom = theEastRoom;
        mySouthRoom = theSouthRoom;
        myWestRoom = theWestRoom;
    }

    public boolean canWalkNorth(){
        boolean canWalk = false;

        if(myNorthRoom != null){
            canWalk = true;
        }

        return canWalk;
    }

    public boolean canWalkEast(){
        boolean canWalk = false;

        if(myEastRoom != null){
            canWalk = true;
        }

        return canWalk;
    }

    public boolean canWalkSouth(){
        boolean canWalk = false;

        if(mySouthRoom != null){
            canWalk = true;
        }

        return canWalk;
    }

    public boolean canWalkWest(){
        boolean canWalk = false;

        if(myWestRoom != null){
            canWalk = true;
        }

        return canWalk;
    }

    public void setNorthRoom(Room theNorthRoom){
        myNorthRoom = theNorthRoom;
    }

    public void setEastRoom(Room theEastRoom) {
        myEastRoom = theEastRoom;
    }

    public void setSouthRoom(Room theSouthRoom){
        myNorthRoom = theSouthRoom;
    }

    public void setWestRoom(Room theWestRoom) {
        myEastRoom = theWestRoom;
    }
}
