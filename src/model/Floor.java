package model;

import java.util.Random;

public class Floor {
    
    private final float DOOR_CHANCE = 0.5f;

    private final int mySize;

    private final Room[][] myRooms;
    
    private static final Random RAND = new Random();
    private final int myFloorLevel;

    Floor(){
        this(1, 5);
    }

    Floor(final int theFloorLevel, final int theSize){
        myFloorLevel = theFloorLevel;
        mySize = theSize;
        myRooms = new Room[mySize][mySize];

        for(int row = 0; row < mySize; row++){
            for(int col = 0; col < mySize; col++){
                myRooms[row][col] = new Room();
            }
        }

        setRandomDoors();
    }

    public final void addCharacter(final int theRoomX, final int theRoomY, final DungeonCharacter theCharacter){
        myRooms[theRoomY][theRoomX].addCharacter(theCharacter);
    }

    public final void removeCharacter(final int theRoomX, final int theRoomY, final DungeonCharacter theCharacter){
        myRooms[theRoomY][theRoomX].removeCharacter(theCharacter);
    }

    private final void setRandomDoors(){
        for(int row = 0; row < mySize; row++){
            for(int col = 0; col < mySize; col++){
                if(RAND.nextFloat() < DOOR_CHANCE && row - 1 >= 0){
                    myRooms[row][col].setNorthRoom(myRooms[row - 1][col]);
                    myRooms[row - 1][col].setSouthRoom(myRooms[row][col]);
                }
                if(RAND.nextFloat() < DOOR_CHANCE && col - 1 >= 0){
                    myRooms[row][col].setWestRoom(myRooms[row][col - 1]);
                    myRooms[row][col - 1].setEastRoom(myRooms[row][col]);
                }
                if(RAND.nextFloat() < DOOR_CHANCE && row + 1 < mySize){
                    myRooms[row][col].setSouthRoom(myRooms[row + 1][col]);
                    myRooms[row + 1][col].setNorthRoom(myRooms[row][col]);
                }
                if(RAND.nextFloat() < DOOR_CHANCE && col + 1 < mySize){
                    myRooms[row][col].setEastRoom(myRooms[row][col + 1]);
                    myRooms[row][col + 1].setWestRoom(myRooms[row][col]);
                }
            }
        }
    }

    public final int getSize(){
        return mySize;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();

        for(int row = 0; row < mySize; row++){
            for(Room r: myRooms[row]){
                sb.append('*');
                if(r.canWalkNorth()){
                    sb.append('-');
                }else{
                    sb.append('*');
                }
            }

            // Print top-right-most corner
            sb.append("*\n");

            for(Room r: myRooms[row]){
                if(r.canWalkWest()){
                    sb.append('|');
                }else{
                    sb.append('*');
                }
                boolean hasHero = false;
                for(DungeonCharacter dc: r.getCharacters()){
                    if(dc.getClass().getSimpleName() == "model.Hero"){
                        hasHero = true;
                    }
                }
                if(hasHero){
                    sb.append('@');
                }else{
                    sb.append(' ');
                }
            }

            if(myRooms[row][mySize - 1].canWalkEast()){
                sb.append('|');
            }else{
                sb.append('*');
            }

            sb.append('\n');

            
        }
        for(Room r: myRooms[mySize - 1]){
            sb.append('*');
            if(r.canWalkSouth()){
                sb.append('-');
            }else{
                sb.append('*');
            }
        }
        sb.append("*\n");

        return sb.toString();
    }
}
