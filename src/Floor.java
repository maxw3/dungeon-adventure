import java.util.Random;

public class Floor {
    
    private final Room[][] myRooms;
    private int mySize;
    private Random rand;
    private final float DOOR_CHANCE = 0.5f;

    Floor(){
        mySize = 5;
        myRooms = new Room[mySize][mySize];

        rand = new Random();

        for(int row = 0; row < mySize; row++){
            for(int col = 0; col < mySize; col++){
                myRooms[row][col] = new Room();
            }
        }

        setRandomDoors();
    }

    Floor(int theSize){
        mySize = theSize;
        myRooms = new Room[mySize][mySize];

        rand = new Random();

        for(int row = 0; row < mySize; row++){
            for(int col = 0; col < mySize; col++){
                myRooms[row][col] = new Room();
            }
        }

        setRandomDoors();
    }

    public void addCharacter(int theRoomX, int theRoomY, DungeonCharacter theCharacter){
        myRooms[theRoomY][theRoomX].addCharacter(theCharacter);
    }

    public void removeCharacter(int theRoomX, int theRoomY, DungeonCharacter theCharacter){
        myRooms[theRoomY][theRoomX].removeCharacter(theCharacter);
    }

    private void setRandomDoors(){
        for(int row = 0; row < mySize; row++){
            for(int col = 0; col < mySize; col++){
                if(rand.nextFloat() < DOOR_CHANCE && row - 1 >= 0){
                    myRooms[row][col].setNorthRoom(myRooms[row - 1][col]);
                    myRooms[row - 1][col].setSouthRoom(myRooms[row][col]);
                }
                if(rand.nextFloat() < DOOR_CHANCE && col - 1 >= 0){
                    myRooms[row][col].setWestRoom(myRooms[row][col - 1]);
                    myRooms[row][col - 1].setEastRoom(myRooms[row][col]);
                }
                if(rand.nextFloat() < DOOR_CHANCE && row + 1 < mySize){
                    myRooms[row][col].setSouthRoom(myRooms[row + 1][col]);
                    myRooms[row + 1][col].setNorthRoom(myRooms[row][col]);
                }
                if(rand.nextFloat() < DOOR_CHANCE && col + 1 < mySize){
                    myRooms[row][col].setEastRoom(myRooms[row][col + 1]);
                    myRooms[row][col + 1].setWestRoom(myRooms[row][col]);
                }
            }
        }
    }

    public int getSize(){
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
                    if(dc.getClass().getSimpleName() == "Hero"){
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
