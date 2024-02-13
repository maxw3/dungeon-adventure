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

    Room(final Room theNorthRoom, final Room theEastRoom, final Room theSouthRoom, final Room theWestRoom){
        myItems = new ArrayList<Item>();
        myDungeonCharacters = new ArrayList<DungeonCharacter>();

        myNorthRoom = theNorthRoom;
        myEastRoom = theEastRoom;
        mySouthRoom = theSouthRoom;
        myWestRoom = theWestRoom;
    }

    public final ArrayList<DungeonCharacter> getCharacters(){
        return myDungeonCharacters;
    }

    public final void addCharacter(final DungeonCharacter theCharacter){
        myDungeonCharacters.add(theCharacter);
    }

    public final void removeCharacter(final DungeonCharacter theCharacter){
        myDungeonCharacters.remove(theCharacter);
    }

    public final void setRooms(final Room theNorthRoom, final Room theEastRoom, final Room theSouthRoom, final Room theWestRoom){
        myNorthRoom = theNorthRoom;
        myEastRoom = theEastRoom;
        mySouthRoom = theSouthRoom;
        myWestRoom = theWestRoom;
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
}
