import enums.Direction;
import model.HealthPotion;
import model.Pillar;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Floor {
    
    private final float DOOR_CHANCE = 0.5f;

    private final int mySize;

    private final Room[][] myRooms;
    
    private static final Random RAND = new Random();

    Floor(){
        mySize = 5;
        myRooms = new Room[mySize][mySize];

        for(int row = 0; row < mySize; row++){
            for(int col = 0; col < mySize; col++){
                myRooms[row][col] = new Room(row, col);
            }
        }

        setRandomDoors();
    }

    Floor(final int theSize){
        mySize = theSize;
        myRooms = new Room[mySize][mySize];

        for(int row = 0; row < mySize; row++){
            for(int col = 0; col < mySize; col++){
                myRooms[row][col] = new Room(row, col);
            }
        }
        fillFloor();
        createMaze();
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

    private void fillFloor() {
        for(int row = 0; row < mySize; row++) {
            for(int col = 0; col < mySize; col++){
                int choice = RAND.nextInt(12);
                if (choice >= 3 && choice <= 5) {
                    myRooms[row][col].addCharacter(MonsterFactory.createSkeleton(1));
                } else if (choice > 5 && choice <= 10) {
                    myRooms[row][col].addItem(new HealthPotion(1));
                } else if (choice > 10) {
                    // Add in Pits
                }
            }
        }
        int x = RAND.nextInt(mySize);
        int y = RAND.nextInt(mySize);
        myRooms[x][y].addItem(new Pillar("Encapsulation"));
    }

    private void createMaze() {
        final Set<Room> adjacentToMaze = new HashSet<>();
        final Set<Room> roomsPartOfMaze = new HashSet<>();
        int row = RAND.nextInt(mySize);
        int col = RAND.nextInt(mySize);
        roomsPartOfMaze.add(myRooms[row][col]);
        addNeighbors(myRooms[row][col], adjacentToMaze, roomsPartOfMaze);
        while (!adjacentToMaze.isEmpty()) {
            final Object[] workableRooms = adjacentToMaze.toArray();
            final int pick = RAND.nextInt(adjacentToMaze.size());
            final Room chosenRoom = (Room)workableRooms[pick];
            roomsPartOfMaze.add(chosenRoom);
            addNeighbors(chosenRoom, adjacentToMaze, roomsPartOfMaze);
            adjacentToMaze.remove(chosenRoom);
            Set<Direction> directionOfMazeNeighbor = neighborPartOfMaze(chosenRoom, roomsPartOfMaze);
            boolean didAddDoor = false;
            while (!didAddDoor) {
                for (Direction d : directionOfMazeNeighbor) {
                    int chance = RAND.nextInt(directionOfMazeNeighbor.size());
                    if (chance == 0) {
                        addDoor(chosenRoom, d);
                        didAddDoor = true;
                        break;
                    }
                }
            }
        }
    }

    private void addNeighbors(final Room theRoom, final Set<Room> theAdjacentToMaze, final Set<Room> theRoomsPartOfMaze) {
        Room neighbor;
        int row = theRoom.getRow();
        int col = theRoom.getCol();
        if (row != 0) {
            neighbor = myRooms[row - 1][col];
            if (!theRoomsPartOfMaze.contains(neighbor)) {
                theAdjacentToMaze.add(neighbor);
            }
        }
        if (col != 0) {
            neighbor = myRooms[row][col - 1];
            if (!theRoomsPartOfMaze.contains(neighbor)) {
                theAdjacentToMaze.add(neighbor);
            }
        }
        if (row != mySize - 1) {
            neighbor = myRooms[row + 1][col];
            if (!theRoomsPartOfMaze.contains(neighbor)) {
                theAdjacentToMaze.add(neighbor);
            }
        }
        if (col != mySize - 1) {
            neighbor = myRooms[row][col + 1];
            if (!theRoomsPartOfMaze.contains(neighbor)) {
                theAdjacentToMaze.add(neighbor);
            }
        }
    }

    private void addDoor(Room chosenRoom, Direction d) {
        int row = chosenRoom.getRow();
        int col = chosenRoom.getCol();
        if (d == Direction.NORTH) {
            Room mazeRoom = myRooms[row - 1][col];
            chosenRoom.setNorthRoom(mazeRoom);
            mazeRoom.setSouthRoom(chosenRoom);
        } else if (d == Direction.SOUTH) {
            Room mazeRoom = myRooms[row + 1][col];
            chosenRoom.setSouthRoom(mazeRoom);
            mazeRoom.setNorthRoom(chosenRoom);
        } else if (d == Direction.WEST) {
            Room mazeRoom = myRooms[row][col - 1];
            chosenRoom.setWestRoom(mazeRoom);
            mazeRoom.setEastRoom(chosenRoom);
        } else if (d == Direction.EAST) {
            Room mazeRoom = myRooms[row][col + 1];
            chosenRoom.setEastRoom(mazeRoom);
            mazeRoom.setWestRoom(chosenRoom);
        }

    }

    private Set<Direction> neighborPartOfMaze(Room theRoom, Set<Room> theRoomsPartOfMaze) {
        Set<Direction> validNeighbors = new HashSet<>();
        Room neighbor;
        int row = theRoom.getRow();
        int col = theRoom.getCol();
        if (row != 0) {
            neighbor = myRooms[row - 1][col];
            if (theRoomsPartOfMaze.contains(neighbor)) {
                validNeighbors.add(Direction.NORTH);
            }
        }
        if (col != 0) {
            neighbor = myRooms[row][col - 1];
            if (theRoomsPartOfMaze.contains(neighbor)) {
                validNeighbors.add(Direction.WEST);
            }
        }
        if (row != mySize - 1) {
            neighbor = myRooms[row + 1][col];
            if (theRoomsPartOfMaze.contains(neighbor)) {
                validNeighbors.add(Direction.SOUTH);
            }
        }
        if (col != mySize - 1) {
            neighbor = myRooms[row][col + 1];
            if (theRoomsPartOfMaze.contains(neighbor)) {
                validNeighbors.add(Direction.EAST);
            }
        }
        return validNeighbors;
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
