package model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class RoomTest {

    @Test
    void constructorNoParams() {
        Room testRoom = new Room();
        assertEquals(0, testRoom.getRow());
        assertEquals(0, testRoom.getCol());
    }

    @Test
    void constructorPositionParams() {
        Room testRoom = new Room(1, 2);
        assertEquals(1, testRoom.getRow());
        assertEquals(2, testRoom.getCol());
    }

    @Test
    void constructorPositionAndRoomParams() {
        final Room north = new Room();
        final Room south = new Room();
        final Room east = new Room();
        final Room west = new Room();
        Room testRoom = new Room(north, east, south, west, 1, 2);
        assertEquals(1, testRoom.getRow());
        assertEquals(2, testRoom.getCol());
        assertEquals(north, testRoom.getNorth());
        assertEquals(west, testRoom.getWest());
        assertEquals(south, testRoom.getSouth());
        assertEquals(east, testRoom.getEast());
    }
    @Test
    void getCharacters() {
        final ArrayList<AbstractDungeonCharacter> compareList = new ArrayList<>();
        final AbstractDungeonCharacter testChar1 = MonsterFactory.createDummy();
        final AbstractDungeonCharacter testChar2 = MonsterFactory.createDummy();
        final Room testRoom = new Room();
        testRoom.addCharacter(testChar1);
        testRoom.addCharacter(testChar2);
        compareList.add(testChar1);
        compareList.add(testChar2);
        assertEquals(compareList, testRoom.getCharacters());
    }

    @Test
    void getItems() {
        final ArrayList<Item> compareList = new ArrayList<>();
        final Item testItem1 = new VisionPotion(1);
        final Item testItem2 = new HealthPotion(1);
        final Room testRoom = new Room();
        testRoom.addItem(testItem1);
        testRoom.addItem(testItem2);
        compareList.add(testItem1);
        compareList.add(testItem2);
        assertEquals(compareList, testRoom.getItems());
    }

    @Test
    void isExplored() {
        final Room testRoom = new Room();
        assertFalse(testRoom.isExplored());
    }

    @Test
    void setExplored() {
        final Room testRoom = new Room();
        testRoom.setExplored(true);
        assertTrue(testRoom.isExplored());
    }

    @Test
    void removeCharacter() {
        final ArrayList<AbstractDungeonCharacter> compareList = new ArrayList<>();
        final AbstractDungeonCharacter testChar1 = MonsterFactory.createDummy();
        final AbstractDungeonCharacter testChar2 = MonsterFactory.createDummy();
        final Room testRoom = new Room();
        testRoom.addCharacter(testChar1);
        testRoom.addCharacter(testChar2);
        testRoom.removeCharacter(testChar1);
        compareList.add(testChar2);
        assertEquals(compareList, testRoom.getCharacters());
    }

    @Test
    void emptyRoom() {
        final ArrayList<AbstractDungeonCharacter> compareList = new ArrayList<>();
        final AbstractDungeonCharacter testChar1 = MonsterFactory.createDummy();
        final AbstractDungeonCharacter testChar2 = MonsterFactory.createDummy();
        final Room testRoom = new Room();
        testRoom.addCharacter(testChar1);
        testRoom.addCharacter(testChar2);
        final Item testItem1 = new VisionPotion(1);
        final Item testItem2 = new HealthPotion(1);
        testRoom.addItem(testItem1);
        testRoom.addItem(testItem2);
        testRoom.emptyRoom();
        assertEquals(compareList, testRoom.getCharacters());
    }

    @Test
    void setRooms() {
        final Room testRoom = new Room();
        final Room north = new Room();
        final Room south = new Room();
        final Room east = new Room();
        final Room west = new Room();
        testRoom.setRooms(north, east, south, west);
        boolean match = true;
        if (!testRoom.getNorth().equals(north)) {
            match = false;
        } else if (!testRoom.getSouth().equals(south)) {
            match = false;
        } else if (!testRoom.getEast().equals(east)) {
            match = false;
        } else if (!testRoom.getWest().equals(west)) {
            match = false;
        }
        assertTrue(match);
    }

    @Test
    void removeItem() {
        final ArrayList<Item> compareList = new ArrayList<>();
        final Item testItem1 = new VisionPotion(1);
        final Item testItem2 = new HealthPotion(1);
        final Room testRoom = new Room();
        testRoom.addItem(testItem1);
        testRoom.addItem(testItem2);
        testRoom.removeItem(testItem1);
        compareList.add(testItem2);
        assertEquals(compareList, testRoom.getItems());
    }

    @Test
    void canWalkNorth() {
        final Room testRoom = new Room();
        final Room north = new Room();
        testRoom.setNorthRoom(north);
        assertEquals(north, testRoom.getNorth());
        assertTrue(testRoom.canWalkNorth());
    }

    @Test
    void canWalkEast() {
        final Room testRoom = new Room();
        final Room east = new Room();
        testRoom.setEastRoom(east);
        assertEquals(east, testRoom.getEast());
        assertTrue(testRoom.canWalkEast());
    }

    @Test
    void canWalkSouth() {
        final Room testRoom = new Room();
        final Room south = new Room();
        testRoom.setSouthRoom(south);
        assertEquals(south, testRoom.getSouth());
        assertTrue(testRoom.canWalkSouth());
    }

    @Test
    void canWalkWest() {
        final Room testRoom = new Room();
        final Room west = new Room();
        testRoom.setWestRoom(west);
        assertEquals(west, testRoom.getWest());
        assertTrue(testRoom.canWalkWest());
    }

    @Test
    void getRow() {
        final Room testRoom = new Room(1, 2);
        assertEquals(1, testRoom.getRow());
    }

    @Test
    void getCol() {
        final Room testRoom = new Room(1, 2);
        assertEquals(2, testRoom.getCol());
    }

    @Test
    void testEqualsTrue() {
        final Room testRoom1 = new Room(1, 2);
        final Room testRoom2 = new Room(1, 2);
        assertEquals(testRoom1, testRoom2);
    }

    @Test
    void testEqualsFalse() {
        final Room testRoom1 = new Room(1, 2);
        final Room testRoom2 = new Room(1, 3);
        assertNotEquals(testRoom1, testRoom2);
    }

    @Test
    void testHashCode() {
        final Room testRoom1 = new Room(1, 2);
        final Room testRoom2 = new Room(1, 2);
        assertEquals(testRoom1.hashCode(), testRoom2.hashCode());
    }
}