package model;

import enums.Direction;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Floor {

    private final static String NEWLINE = System.lineSeparator();
    private static final Random RAND = new Random();

    private final int mySize;

    private final Room[][] myRooms;

    private final int myFloorLevel;

    private final Room myStartingRoom;

    Floor() {
        this(1, 5);
    }

    Floor(final int theFloorLevel, final int theSize) {
        myFloorLevel = theFloorLevel;
        mySize = theSize;
        myRooms = new Room[mySize][mySize];

        for (int row = 0; row < mySize; row++) {
            for (int col = 0; col < mySize; col++) {
                myRooms[row][col] = new Room(row, col);
            }
        }
        fillFloor();
        final Room startRoom = createMaze();
        startRoom.emptyRoom();
        myStartingRoom = startRoom;
    }

    public final void addCharacter(final int theRoomX, final int theRoomY, final AbstractDungeonCharacter theCharacter) {
        myRooms[theRoomY][theRoomX].addCharacter(theCharacter);
    }

    public final void removeCharacter(final int theRoomX, final int theRoomY, final AbstractDungeonCharacter theCharacter) {
        myRooms[theRoomY][theRoomX].removeCharacter(theCharacter);
    }

    public final int getSize() {
        return mySize;
    }

    public final Room getStartingRoom() {
        return myStartingRoom;
    }

    public final Room getRoom(final int theRow, final int theCol) {
        if (!outOfBounds(theCol) && !outOfBounds(theRow)) {
            return myRooms[theRow][theCol];
        } else {
            throw new IllegalArgumentException("The position is out of bounds! " + theRow + " " + theCol);
        }
    }

    Room[][] getRooms() {
        return Arrays.copyOf(myRooms, mySize);
    }

    private void fillFloor() {
        for (int row = 0; row < mySize; row++) {
            for (int col = 0; col < mySize; col++) {
                final int choice = RAND.nextInt(100);
                if (choice <= 30) {
                    myRooms[row][col].addCharacter(MonsterFactory.createMonster(myFloorLevel));
                } else if (choice <= 70) {
                    myRooms[row][col].addItem(new HealthPotion());
                } else if (choice <= 80) {
                    myRooms[row][col].addItem(new Pit());
                }
            }
        }

    }

    private Room createMaze() {
        final Set<Room> adjacentToMaze = new HashSet<>();
        final Set<Room> roomsPartOfMaze = new HashSet<>();
        final int row = RAND.nextInt(mySize);
        final int col = RAND.nextInt(mySize);
        final Room startingRoom = myRooms[row][col];
        Room chosenRoom = startingRoom;
        roomsPartOfMaze.add(startingRoom);
        addNeighbors(startingRoom, adjacentToMaze, roomsPartOfMaze);
        while (!adjacentToMaze.isEmpty()) {
            final Object[] workableRooms = adjacentToMaze.toArray();
            final int pick = RAND.nextInt(adjacentToMaze.size());
            chosenRoom = (Room)workableRooms[pick];
            roomsPartOfMaze.add(chosenRoom);
            addNeighbors(chosenRoom, adjacentToMaze, roomsPartOfMaze);
            adjacentToMaze.remove(chosenRoom);
            final Set<Direction> directionOfMazeNeighbor = neighborPartOfMaze(chosenRoom, roomsPartOfMaze);
            boolean didAddDoor = false;
            while (!didAddDoor) {
                for (Direction d : directionOfMazeNeighbor) {
                    final int chance = RAND.nextInt(directionOfMazeNeighbor.size());
                    if (chance == 0) {
                        addDoor(chosenRoom, d);
                        didAddDoor = true;
                        break;
                    }
                }
            }
        }
        addPillar(chosenRoom);
        return startingRoom;
    }

    private void addPillar(final Room theRoom) {
        if (myFloorLevel == 0) {
            theRoom.addItem(new Pillar("Encapsulation"));
        } else if (myFloorLevel == 1) {
            theRoom.addItem(new Pillar("Polymorphism"));
        } else if (myFloorLevel == 2) {
            theRoom.addItem(new Pillar("Inheritance"));
        } else if (myFloorLevel == 3) {
            theRoom.addItem(new Pillar("Abstraction"));
        }
    }

    private void addNeighbors(final Room theRoom, final Set<Room> theAdjacentToMaze, final Set<Room> theRoomsPartOfMaze) {
        Room neighbor;
        final int row = theRoom.getRow();
        final int col = theRoom.getCol();
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

    private void addDoor(final Room theChosenRoom, final Direction theDirection) {
        final int row = theChosenRoom.getRow();
        final int col = theChosenRoom.getCol();
        final Room mazeRoom;
        if (theDirection == Direction.NORTH) {
            mazeRoom = myRooms[row - 1][col];
            theChosenRoom.setNorthRoom(mazeRoom);
            mazeRoom.setSouthRoom(theChosenRoom);
        } else if (theDirection == Direction.SOUTH) {
            mazeRoom = myRooms[row + 1][col];
            theChosenRoom.setSouthRoom(mazeRoom);
            mazeRoom.setNorthRoom(theChosenRoom);
        } else if (theDirection == Direction.WEST) {
            mazeRoom = myRooms[row][col - 1];
            theChosenRoom.setWestRoom(mazeRoom);
            mazeRoom.setEastRoom(theChosenRoom);
        } else if (theDirection == Direction.EAST) {
            mazeRoom = myRooms[row][col + 1];
            theChosenRoom.setEastRoom(mazeRoom);
            mazeRoom.setWestRoom(theChosenRoom);
        }

    }

    private Set<Direction> neighborPartOfMaze(final Room theRoom, final Set<Room> theRoomsPartOfMaze) {
        final Set<Direction> validNeighbors = new HashSet<>();
        Room neighbor;
        final int row = theRoom.getRow();
        final int col = theRoom.getCol();
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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();

        for (int row = 0; row < mySize; row++) {
            for (Room r: myRooms[row]) {
                sb.append('*');
                if (r.canWalkNorth()) {
                    sb.append('-');
                } else {
                    sb.append('*');
                }
            }

            // Print top-right-most corner
            sb.append('*').append(NEWLINE);

            for (Room r: myRooms[row]) {
                if (r.canWalkWest()) {
                    sb.append('|');
                } else {
                    sb.append('*');
                }

                boolean hasHero = false;
                boolean hasMonster = false;
                boolean hasItem = false;

                for (AbstractDungeonCharacter dc: r.getCharacters()) {
                    if (dc instanceof Hero) {
                        hasHero = true;
                    }
                    if (dc instanceof Monster) {
                        hasMonster = true;
                    }
                }

                for (Item i: r.getItems()) {
                    if (i instanceof Item) {
                        hasItem = true;
                        break;
                    }
                }
//                if (r.isExplored()) {
                    if (hasHero) {
                        sb.append('@');
                    } else if (hasMonster) {
                        sb.append('M');
                    } else if (hasItem) {
                        sb.append('\'');
                    } else {
                        sb.append(' ');
                    }
//                } else {
//                    sb.append('?');
//                }
            }

            if (myRooms[row][mySize - 1].canWalkEast()) {
                sb.append('|');
            } else {
                sb.append('*');
            }

            sb.append('\n');


        }
        for (Room r: myRooms[mySize - 1]) {
            sb.append('*');
            if (r.canWalkSouth()) {
                sb.append('-');
            } else {
                sb.append('*');
            }
        }
        sb.append('*').append(NEWLINE);

        return sb.toString();
    }

    private boolean outOfBounds(final int thePosition) {
        return thePosition < 0 || thePosition >= mySize;
    }
}
