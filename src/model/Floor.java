package model;

import enums.Direction;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Floor {

    private Room[][] myRooms;
    private int mySize;
    private Random myRandom = new Random();

    public Floor(){
        mySize = 3;
        myRooms = new Room[mySize][mySize];
    }

    public Floor(int theSize){
        mySize = theSize;
        myRooms = new Room[mySize][mySize];

        for(int row = 0; row < mySize; row++){
            for(int col = 0; col < mySize; col++){
                myRooms[row][col] = new Room(new int[]{row, col});
            }
        }
        fillFloor();
        createMaze();
        int hold = 0;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();

        for(int ra = 0; ra < mySize; ra++){
            for(int room = 0; room < mySize; room++){
                sb.append('*');
                if(ra == 0){
                    sb.append('*');
                }else{
                    if(myRooms[ra][room].getNorth()){
                        sb.append('-');
                    }else{
                        sb.append('*');
                    }
                }
                if(room == mySize - 1){
                    sb.append('*');
                }
            }
            sb.append('\n');
            for(int room = 0; room < mySize; room++){
                if(room == 0){
                    sb.append('*');
                }else{
                    if(myRooms[ra][room].getWest()){
                        sb.append('|');
                    }else{
                        sb.append('*');
                    }
                }
                sb.append(' ');
                if(room == mySize - 1){
                    sb.append('*');
                }
                // if(ra[room].getEast()){
                //     sb.append("|");
                // }else{
                //     sb.append("*");
                // }
            }
            sb.append('\n');
            // for(int room = 0; room < mySize; room++){
            //     sb.append('*');
            //     if(ra[room].getSouth()){
            //         sb.append('-');
            //     }else{
            //         sb.append('*');
            //     }
            //     sb.append("*");
            // }
            //sb.append('\n');
        }

        for(int c = 0; c < mySize * 2; c++){
            sb.append('*');
        }
        sb.append('*');

        return sb.toString();
    }

    public int getSize(){
        return mySize;
    }

    private void fillFloor() {
        for(int row = 0; row < mySize; row++) {
            for(int col = 0; col < mySize; col++){
                int choice = myRandom.nextInt(12);
                if (choice >= 3 && choice <= 5) {
                    myRooms[row][col].addCharacter(MonsterFactory.createSkeleton(1));
                } else if (choice > 5 && choice <= 10) {
                    myRooms[row][col].addItem(new HealthPotion("Health Potion", 1));
                } else if (choice > 10) {
                    // Add in Pits
                }
            }
        }
        int x = myRandom.nextInt(mySize);
        int y = myRandom.nextInt(mySize);
        myRooms[x][y].addItem(new Pillar("Encapsulation"));
    }

    private void createMaze() {
        final Set<Room> adjacentToMaze = new HashSet<>();
        final Set<Room> roomsPartOfMaze = new HashSet<>();
        int x = myRandom.nextInt(mySize);
        int y = myRandom.nextInt(mySize);
        roomsPartOfMaze.add(myRooms[x][y]);
        addNeighbors(myRooms[x][y], adjacentToMaze, roomsPartOfMaze);
        while (!adjacentToMaze.isEmpty()) {
            final Object[] workableRooms = adjacentToMaze.toArray();
            final int pick = myRandom.nextInt(adjacentToMaze.size());
            final Room chosenRoom = (Room)workableRooms[pick];
            int[] temp = chosenRoom.getPosition();
            x = temp[0];
            y = temp[1];
            roomsPartOfMaze.add(chosenRoom);
            adjacentToMaze.remove(chosenRoom);
            addNeighbors(chosenRoom, adjacentToMaze, roomsPartOfMaze);
            boolean[] neighborExplored = checkNeighbors(roomsPartOfMaze, chosenRoom);
            for (int selectDoor = 4; selectDoor > 0; selectDoor--) {
                int selection = myRandom.nextInt(selectDoor);
                if (neighborExplored[selection]) {
                    if (selection == 0) {
                        chosenRoom.setHallway(true, Direction.NORTH);
                        myRooms[x][y + 1].setHallway(true, Direction.SOUTH);
                    } else if (selection == 1) {
                        chosenRoom.setHallway(true, Direction.SOUTH);
                        myRooms[x][y - 1].setHallway(true, Direction.NORTH);
                    } else if (selection == 2) {
                        chosenRoom.setHallway(true, Direction.EAST);
                        myRooms[x + 1][y].setHallway(true, Direction.WEST);
                    } else if (selection == 3) {
                        chosenRoom.setHallway(true, Direction.WEST);
                        myRooms[x - 1][y].setHallway(true, Direction.EAST);
                    }
                }
            }
        }
    }

    private void addNeighbors (final Room theRoom, final Set<Room> theAdjacentRooms, final Set<Room> theRoomsInMaze) {
        int[] position = theRoom.getPosition();
        Room roomToAdd;
        if (position[0] != 0) {
            roomToAdd = myRooms[position[0] - 1][position[1]];
            if (!theRoomsInMaze.contains(roomToAdd)) {
                theAdjacentRooms.add(roomToAdd);
            }
        }
        if (position[1] != 0) {
            roomToAdd = myRooms[position[0]][position[1] - 1];
            if (!theRoomsInMaze.contains(roomToAdd)) {
                theAdjacentRooms.add(roomToAdd);
            }
        }
        if (position[0] != mySize - 1) {
            roomToAdd = myRooms[position[0] + 1][position[1]];
            if (!theRoomsInMaze.contains(roomToAdd)) {
                theAdjacentRooms.add(roomToAdd);
            }
        }
        if (position[1] != mySize - 1) {
            roomToAdd = myRooms[position[0]][position[1] + 1];
            if (!theRoomsInMaze.contains(roomToAdd)) {
                theAdjacentRooms.add(roomToAdd);
            }
        }
    }
    private boolean[] checkNeighbors(final Set<Room> theExistingMaze, final Room theRoom) {
        boolean[] neighbors = new boolean[4];
        int[] position = theRoom.getPosition();
        int x = position[0];
        int y = position[1];
        if (x != 0) {
            neighbors[3] = theExistingMaze.contains(myRooms[x - 1][y]); // Check West
        }
        if (y != 0) {
            neighbors[1] = theExistingMaze.contains(myRooms[x][y - 1]); // Check South
        }
        if (x != mySize - 1) {
            neighbors[2] = theExistingMaze.contains(myRooms[x + 1][y]); // Check East
        }
        if (y != mySize - 1) {
            neighbors[0] = theExistingMaze.contains(myRooms[x][y + 1]); // Check North
        }
        return neighbors;
    }
}
