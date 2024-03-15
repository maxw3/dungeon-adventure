package model;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class FloorTest {

    private static final String NEWLINE = System.lineSeparator();
    @Test
    void testFloorConstructor() throws SQLException {
        Floor testFloor = new Floor(1, 5);
        Room[][] rooms = testFloor.getRooms();
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                assertNotNull(rooms[row][col]);
            }
        }
        assertEquals(5, testFloor.getSize());
    }
    @Test
    void testFloorConstructorCorrectness() throws SQLException {
        boolean correct = true;
        for (int attempts = 0; attempts < 10; attempts++) {
            Floor testFloor = new Floor(1, 7);
            Room[][] arrayOfRooms = testFloor.getRooms();
            for (int row = 0; row < testFloor.getSize(); row++) {
                if (arrayOfRooms[row][0].canWalkWest()) {
                    correct = false;
                    break;
                } else if (arrayOfRooms[row][testFloor.getSize() - 1].canWalkEast()) {
                    correct = false;
                    break;
                }
            }
            for (int col = 0; col < testFloor.getSize(); col++) {
                if (arrayOfRooms[0][col].canWalkNorth()) {
                    correct = false;
                    break;
                } else if (arrayOfRooms[testFloor.getSize() - 1][col].canWalkSouth()) {
                    correct = false;
                    break;
                }
            }
            if (!correct) {
                break;
            }
        }
        assertTrue(correct);
    }

    /*
       I have some ideas for how we could test the toString, but none are easy to implement. First,
       we could separate the method that fills the maze with items from the constructor call (by the creation
       of a seperate "test" constructor), but that wouldn't fix the fact that the maze generation is random. Second,
       another alternative would be to create some way to substitute the myRooms field with an array of our choosing,
       but having that be anything besides private would be dangerous for the codebase. Any ideas are welcome.
     */
    @Test
    void testToString() throws SQLException {
        Floor testFloor = new Floor(1, 2);
        boolean doesMatch = false;
        if (("*****" + NEWLINE + "*?|?*" + NEWLINE + "*-*-*" + NEWLINE + "*?*?*" + NEWLINE + "*****" + NEWLINE).equals(testFloor.toString())) {
            doesMatch = true;
        } else if (("*****" + NEWLINE + "*?|?*" + NEWLINE + "*-***" + NEWLINE + "*?|?*" + NEWLINE + "*****" + NEWLINE).equals(testFloor.toString())) {
            doesMatch = true;
        } else if (("*****" + NEWLINE + "*?*?*" + NEWLINE + "*-*-*" + NEWLINE + "*?|?*" + NEWLINE + "*****" + NEWLINE).equals(testFloor.toString())) {
            doesMatch = true;
        } else if (("*****" + NEWLINE + "*?|?*" + NEWLINE + "***-*" + NEWLINE + "*?|?*" + NEWLINE + "*****" + NEWLINE).equals(testFloor.toString())) {
            doesMatch = true;
        }
        assertTrue(doesMatch);
    }

    @Test
    void getSize() throws SQLException {
        Floor testFloor = new Floor(1, 7);
        assertEquals(7, testFloor.getSize());
    }

    @Test
    void getStartingRoom() throws SQLException {
        Floor testFloor = new Floor(1, 1);
        final Room expectedRoom = testFloor.getRooms()[0][0];
        assertEquals(expectedRoom, testFloor.getStartingRoom());
    }
}