package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DungeonLogicTest implements PropertyChangeListener {

    private String myMessages;
    private int myHPPotionAmount = 0;
    private boolean myCanLoad = false;
    private int myHeroHP = 0;
    private String myHeroName;
    private DungeonLogic testDungeon = DungeonLogic.getDungeonInstance();

    @BeforeEach
    void setup() throws SQLException {
        testDungeon.createCharacter("Test", 0);
        testDungeon.addPropertyChangeListener(this);
    }
    @Test
    void save() throws IOException {
        testDungeon.save();
        File file = new File("saves\\Test1.adv");
        assertTrue(file.isFile());
        assertTrue(myCanLoad);
    }

    @Test
    void load() throws IOException, ClassNotFoundException {
        DungeonLogic before = testDungeon;
        testDungeon.save();
        File file = new File("saves\\Test1.adv");
        assertDoesNotThrow(() -> testDungeon.load(file));
        assertEquals(before.getSteps(), DungeonLogic.getDungeonInstance().getSteps());
    }

    @Test
    void startGame() throws SQLException {
        testDungeon.nextFloor();
        int level = testDungeon.getFloorLevel();
        testDungeon.startGame();
        assertEquals(1, testDungeon.getFloorLevel());
        assertNotEquals(level, testDungeon.getFloorLevel());
    }

    @Test
    void nextFloor() throws SQLException {
        testDungeon.startGame();
        int hp = myHeroHP;
        int level = testDungeon.getFloorLevel();
        testDungeon.nextFloor();
        assertEquals(2, testDungeon.getFloorLevel());
        assertNotEquals(level, testDungeon.getFloorLevel());
        assertNotEquals(hp, myHeroHP);
        assertEquals("You levelled up!", myMessages);
    }

    @Test
    void createCharacter() throws SQLException {
        testDungeon.createCharacter("Test", 0);
        assertEquals("Test", testDungeon.getHero().getCharName());
        assertEquals("Warrior", testDungeon.getHero().getName());
    }

    @Test
    void setGameActive() {
        testDungeon.setGameActive(true);
        assertTrue(testDungeon.getGameActive());
        testDungeon.setGameActive(false);
        assertFalse(testDungeon.getGameActive());
    }

    @Test
    void getCombatStatus() throws SQLException {
        assertFalse(testDungeon.getCombatStatus());
        testDungeon.getCurrentRoom().addCharacter(new Monster("Skeleton", 1));
        testDungeon.startCombat();
        assertTrue(testDungeon.getCombatStatus());
    }

    @Test
    void getDungeonInstance() {
        assertEquals(testDungeon, DungeonLogic.getDungeonInstance());
    }

    @Test
    void getHero() throws SQLException {
        testDungeon.createCharacter("Test", 0);
        assertEquals("Test", testDungeon.getHero().getCharName());
        assertEquals("Warrior", testDungeon.getHero().getName());
        assertNotNull((testDungeon.getHero()));
        assertEquals("Warrior.png", myHeroName);
    }

    @Test
    void getInventory() {
        Room room = testDungeon.getCurrentRoom();
        room.addItem(new HealthPotion());
        testDungeon.collect();
        assertEquals(1, testDungeon.getInventory().getHPPotionAmount());
        testDungeon.useItem(new HealthPotion());
        assertEquals(0, testDungeon.getInventory().getHPPotionAmount());
    }

    @Test
    void getCurrentRoom() {
        Room current = testDungeon.getCurrentRoom();
        if (current.canWalkNorth()) {
            testDungeon.moveUp();
            assertEquals(current.getNorth(), testDungeon.getCurrentRoom());
        } else if (current.canWalkSouth()) {
            testDungeon.moveDown();
            assertEquals(current.getSouth(), testDungeon.getCurrentRoom());
        } else if (current.canWalkEast()) {
            testDungeon.moveRight();
            assertEquals(current.getEast(), testDungeon.getCurrentRoom());
        } else if (current.canWalkWest()) {
            testDungeon.moveLeft();
            assertEquals(current.getWest(), testDungeon.getCurrentRoom());
        }
    }

    @Test
    void getNeighbors() {
        Room current = testDungeon.getCurrentRoom();
        Set<Room> neighbors = testDungeon.getNeighbors(current);
        boolean r = true;
        for (final Room neighbor : neighbors) {
            boolean result = false;
            int row = neighbor.getRow();
            int col = neighbor.getCol();
            if (row - 1 == current.getRow() && col == current.getCol()) {
                result = true;
            } else if (row + 1 == current.getRow() && col == current.getCol()) {
                result = true;
            } else if (row == current.getRow() && col - 1 == current.getCol()) {
                result = true;
            } else if (row == current.getRow() && col + 1 == current.getCol()) {
                result = true;
            }
            if (!result) {
                r = false;
                break;
            }
        }
        assertTrue(r);
    }

    @Test
    void getSteps() {
        Room current = testDungeon.getCurrentRoom();
        assertEquals(0, testDungeon.getSteps());
        if (current.canWalkNorth()) {
            testDungeon.moveUp();
        } else if (current.canWalkSouth()) {
            testDungeon.moveDown();
        } else if (current.canWalkEast()) {
            testDungeon.moveRight();
        } else if (current.canWalkWest()) {
            testDungeon.moveLeft();
        }
        assertEquals(1, testDungeon.getSteps());
    }

    @Test
    void getFloorLevel() throws SQLException {
        assertEquals(1, testDungeon.getFloorLevel());
        testDungeon.nextFloor();
        assertEquals(2, testDungeon.getFloorLevel());
    }

    @Test
    void startCombat() throws SQLException {
        testDungeon.startGame();
        testDungeon.startCombat();
        assertFalse(testDungeon.getCombatStatus());
        testDungeon.getCurrentRoom().addCharacter(MonsterFactory.createMonster(0));
        testDungeon.startCombat();
        assertTrue(testDungeon.getCombatStatus());
    }

    @Test
    void endCombat() throws SQLException {
        testDungeon.getCurrentRoom().addCharacter(MonsterFactory.createMonster(0));
        testDungeon.startCombat();
        testDungeon.endCombat();
        assertFalse(testDungeon.getCombatStatus());
    }

    @Test
    void explore() {
        final Room current = testDungeon.getCurrentRoom();
        current.setExplored(false);
        current.setVisibilty(false);
        testDungeon.explore(current);
        assertTrue(current.isExplored());
        assertTrue(current.isVisible());
    }

    @Test
    void reveal() {
        final Room current = testDungeon.getCurrentRoom();
        current.setExplored(false);
        current.setVisibilty(false);
        testDungeon.reveal(current);
        assertFalse(current.isExplored());
        assertTrue(current.isVisible());
    }

    @Test
    void expireVisPot() {
        Room current = testDungeon.getCurrentRoom();
        Set<Room> neighbors = testDungeon.getNeighbors(current);
        current.addItem(new VisionPotion());
        testDungeon.collect();
        testDungeon.useItem(new VisionPotion());
        testDungeon.expireVisPot();
        for (Room neighbor : neighbors) {
            assertFalse(neighbor.isVisible());
        }
    }

    @Test
    void getFloorString() {
        // Would be extremely hard to test because there is no way to change floor size to trivial amount here.
        // It should be apparent that this works since this is just a wrapper for Floor.toString().
    }

    @Test
    void addPropertyChangeListener() throws SQLException {
        testDungeon.startGame();
        testDungeon.nextFloor();
        assertNotNull(myMessages);
    }

    @Test
    void removePropertyChangeListener() throws SQLException {
        testDungeon.removePropertyChangeListener(this);
        testDungeon.startGame();
        testDungeon.nextFloor();
        assertNull(myMessages);
    }

    @Test
    void setEnemy() throws SQLException {
        Monster monster = MonsterFactory.createMonster(0);
        testDungeon.setEnemy(monster);
        assertEquals(monster, testDungeon.getEnemy());
    }

    @Test
    void moveUp() {
        final Room current = testDungeon.getCurrentRoom();
        final Room room = new Room();
        current.setNorthRoom(room);
        testDungeon.moveUp();
        assertEquals(room, testDungeon.getCurrentRoom());
    }

    @Test
    void moveRight() {
        final Room current = testDungeon.getCurrentRoom();
        final Room room = new Room();
        current.setEastRoom(room);
        testDungeon.moveRight();
        assertEquals(room, testDungeon.getCurrentRoom());
    }

    @Test
    void moveDown() {
        final Room current = testDungeon.getCurrentRoom();
        final Room room = new Room();
        current.setSouthRoom(room);
        testDungeon.moveDown();
        assertEquals(room, testDungeon.getCurrentRoom());
    }

    @Test
    void moveLeft() {
        final Room current = testDungeon.getCurrentRoom();
        final Room room = new Room();
        current.setWestRoom(room);
        testDungeon.moveLeft();
        assertEquals(room, testDungeon.getCurrentRoom());
    }

    @Test
    void collect() {
        Room room = testDungeon.getCurrentRoom();
        room.addItem(new HealthPotion());
        testDungeon.collect();
        assertEquals(1, testDungeon.getInventory().getHPPotionAmount());
        assertEquals(1, myHPPotionAmount);
    }

    @Test
    void useItem() {
        Room room = testDungeon.getCurrentRoom();
        testDungeon.getHero().healOrDamage(-1);
        room.addItem(new HealthPotion());
        testDungeon.collect();
        testDungeon.useItem(new HealthPotion());
        assertEquals(0, testDungeon.getInventory().getHPPotionAmount());
        assertEquals(0, myHPPotionAmount);
        assertEquals(testDungeon.getHero().getMaxHP(), testDungeon.getHero().getHP());
    }

    @Test
    void flee() throws SQLException {
        final Room room = new Room();
        final Room currentRoom = testDungeon.getCurrentRoom();
        currentRoom.setNorthRoom(room);
        room.addCharacter(MonsterFactory.createMonster(0));
        testDungeon.moveUp();
        testDungeon.flee();
        assertFalse(testDungeon.getCombatStatus());
        assertEquals(currentRoom, testDungeon.getCurrentRoom());
    }

    @Test
    void sendMessage() throws SQLException {
        testDungeon.startGame();
        testDungeon.sendMessage("Testing");
        assertEquals("Testing", myMessages);
    }

    /**
     * This method gets called when a bound property is changed.
     *
     * @param theEvent A PropertyChangeEvent object describing the event source
     *            and the property that has changed.
     */
    @Override
    public void propertyChange(PropertyChangeEvent theEvent) {
        final String s = theEvent.getPropertyName();
        if ("MESSAGE".equals(s)) {
            final String t = theEvent.getNewValue().toString();
            myMessages = t;
        } else if ("Health Potion".equals(s)) {
            myHPPotionAmount = (int)(theEvent.getNewValue());
        } else if ("Can Load".equals(s)) {
            myCanLoad = (boolean)(theEvent.getNewValue());
        } else if ("HP CHANGE".equals(s)) {
            myHeroHP = (int)(theEvent.getNewValue());
        } else if ("LEVEL UP".equals(s)) {
            myHeroHP = (int)(theEvent.getNewValue());
        } else if ("Hero".equals(s)) {
            myHeroName = (String)(theEvent.getNewValue());
        }
    }
}