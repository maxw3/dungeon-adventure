package model;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FloorTest {


    @Test
    void testFloorConstructor() {
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
    @Test
    void addCharacter() {
        Floor testFloor = new Floor(1, 7);
        Room[][] arrayOfRooms = testFloor.getRooms();
        DungeonCharacter dummy = MonsterFactory.createDummy();
        testFloor.addCharacter(3, 3, dummy);
        assertTrue(arrayOfRooms[3][3].getCharacters().contains(dummy));
    }

    @Test
    void removeCharacter() {
        Floor testFloor = new Floor(1, 7);
        Room[][] arrayOfRooms = testFloor.getRooms();
        DungeonCharacter dummy = MonsterFactory.createDummy();
        testFloor.addCharacter(3, 3, dummy);
        testFloor.removeCharacter(3, 3, dummy);
        assertFalse(arrayOfRooms[3][3].getCharacters().contains(dummy));
    }

    /*
       I have some ideas for how we could test the toString, but none are easy to implement. First,
       we could separate the method that fills the maze with items from the constructor call (by the creation
       of a seperate "test" constructor), but that wouldn't fix the fact that the maze generation is random. Second,
       another alternative would be to create some way to substitute the myRooms field with an array of our choosing,
       but having that be anything besides private would be dangerous for the codebase. Any ideas are welcome.
     */
    @Test
    void testToString() {
        assertEquals("", new Floor(1, 7));
    }

    @Test
    void getSize() {
        Floor testFloor = new Floor(1, 7);
        assertEquals(7, testFloor.getSize());
    }
}