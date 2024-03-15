package model;

import enums.Direction;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * The floor of the dungeon
 * contains the rooms of the floor, and how they connect with each other
 */
public final class Floor implements Serializable {

    /**
     * String to use as a line separator for new lines
     */
    private final static String NEWLINE = System.lineSeparator();
    /**
     * random number generator
     */
    private static final Random RAND = new Random();

    /**
     * the size of the floor
     */
    private final int mySize;

    /**
     * The rooms of the floor
     */
    private final Room[][] myRooms;

    /**
     * The level of the floor
     */
    private final int myFloorLevel;

    /**
     * The entrance of the floor
     */
    private final Room myStartingRoom;

    /**
     * Constructor
     *
     * @param theFloorLevel The level of the floor
     * @param theSize   The length of the square floor
     * @throws SQLException could not query monster data
     */
    Floor(final int theFloorLevel, final int theSize) throws SQLException {
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

    /**
     * Getter for mySize
     * @return the size
     */
    public int getSize() { return mySize; }

    /**
     * Getter for myStartingRoom
     * @return the Room
     */
    public Room getStartingRoom() { return myStartingRoom; }

    /**
     * Getter for myRooms
     * @return the rooms of the floor
     */
    Room[][] getRooms() {
        return myRooms;
    }

    /**
     * Helper method to fill myRooms with rooms
     * @throws SQLException could not query monster data
     */
    private void fillFloor() throws SQLException {
        for (int row = 0; row < mySize; row++) {
            for (int col = 0; col < mySize; col++) {
                final int choice = RAND.nextInt(100);
                if (choice <= 30) {
                    myRooms[row][col].addCharacter(MonsterFactory.createMonster(myFloorLevel));
                } else if (choice <= 70) {
                    int r = RAND.nextInt(2);
                    AbstractConsumable[] select = new AbstractConsumable[] {new HealthPotion(), new VisionPotion()};
                    myRooms[row][col].addItem(select[r]);
                } else if (choice <= 80) {
                    myRooms[row][col].addItem(new Pit());
                }
            }
        }

    }

    /**
     * Helper method to connect the rooms in a maze like fashion
     * @return  The entrance of the floor
     * @throws SQLException could not query monster data
     */
    private Room createMaze() throws SQLException {
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
        chosenRoom.emptyRoom();
        addPillar(chosenRoom);
        chosenRoom.addCharacter(MonsterFactory.createBoss(myFloorLevel));
        return startingRoom;
    }

    /**
     * Add a Pillar of OO to the Room
     * @param theRoom the room
     */
    private void addPillar(final Room theRoom) {
        if (myFloorLevel == 1) {
            theRoom.addItem(new Pillar("Encapsulation"));
        } else if (myFloorLevel == 2) {
            theRoom.addItem(new Pillar("Polymorphism"));
        } else if (myFloorLevel == 3) {
            theRoom.addItem(new Pillar("Inheritance"));
        } else if (myFloorLevel == 4) {
            theRoom.addItem(new Pillar("Abstraction"));
        }
    }

    /**
     * Connect the rooms
     * @param theRoom The room
     * @param theAdjacentToMaze the neighboring rooms
     * @param theRoomsPartOfMaze the rooms that are part of the floor
     */
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

    /**
     * Add doors to the rooms to make them traversable
     * @param theChosenRoom the room
     * @param theDirection the direction of the door
     */
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

    /**
     * What are the valid neighbors of the room
     * @param theRoom the room
     * @param theRoomsPartOfMaze the rooms in the floor
     * @return the valid naighbors
     */
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
                boolean hasPillar = false;

                for (AbstractDungeonCharacter dc: r.getCharacters()) {
                    if (dc instanceof Hero) {
                        hasHero = true;
                    }
                    if (dc instanceof Monster) {
                        hasMonster = true;
                    }
                }

                for (Item i: r.getItems()) {
                    if (i instanceof Pillar) {
                        hasPillar = true;
                        break;
                    } else if (i instanceof Item) {
                        hasItem = true;
                        break;
                    }
                }
                if (r.isVisible()) {
                    if (hasPillar) {
                        sb.append('B');
                    } else if (hasHero) {
                        sb.append('@');
                    } else if (hasMonster) {
                        sb.append('M');
                    } else if (hasItem) {
                        sb.append('\'');
                    } else {
                        sb.append(' ');
                    }
                } else {
                    sb.append('?');
                }
            }

            if (myRooms[row][mySize - 1].canWalkEast()) {
                sb.append('|');
            } else {
                sb.append('*');
            }

            sb.append(NEWLINE);


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

    /**
     * helper method to check if pointer is out of bounds
     * @param thePosition the index
     * @return is it out of bounds
     */
    /*Default*/ boolean outOfBounds(final int thePosition) {
        return !(thePosition >= 0 && thePosition < mySize);
    }
}
