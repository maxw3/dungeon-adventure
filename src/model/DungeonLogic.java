package model;

import java.beans.PropertyChangeSupport;
import java.util.HashSet;
import java.util.Set;

public final class DungeonLogic {
    public static final DungeonLogic MY_INSTANCE = new DungeonLogic();
    private static final int DUNGEON_SIZE = 5;
    private final PropertyChangeSupport myChanges
        = new PropertyChangeSupport(this);

    private Hero myHero;
    private Floor myFloor;
    private Inventory myInventory;
    private boolean myGameActive;
    private boolean myCombatStatus;
    private String mySaveState;
    private int myFloorLevel;
    private int myHeroRow;
    private int myHeroCol;
    private Room myCurrentRoom;

    private DungeonLogic() {
        startGame();
    }

    private void startGame() {
        myFloorLevel = 1;
        setGameActive(true);
        myFloor = new Floor(myFloorLevel, DUNGEON_SIZE);
        createCharacter();
        myInventory = new Inventory();
        final Room startingRoom = myFloor.getStartingRoom();
        startingRoom.addCharacter(myHero);
        myHeroCol = myHero.getPosition()[1];
        myHeroRow = myHero.getPosition()[0];
        myCurrentRoom = startingRoom;
    }

    public boolean getGameActive() {
        return myGameActive;
    }
    public static DungeonLogic getDungeonInstance() {
        return MY_INSTANCE;
    }

    public Hero getHero() {
        return myHero;
    }
    public Inventory getInventory() {
        return myInventory;
    }

    public Room getCurrentRoom() {
        return myCurrentRoom;
    }

    public Set<Room> getNeighbors(final Room theRoom) {
        final Set<Room> set = new HashSet<Room>();
        if (theRoom.canWalkNorth()) {
            set.add(theRoom.getNorth());
        }
        if (theRoom.canWalkSouth()) {
            set.add(theRoom.getSouth());
        }
        if (theRoom.canWalkWest()) {
            set.add(theRoom.getWest());
        }
        if (theRoom.canWalkEast()) {
            set.add(theRoom.getEast());
        }
        return set;
    }

    public boolean startCombat() {
        if (myGameActive && !myCombatStatus && !outOfBounds(myHeroCol) && !outOfBounds(myHeroRow)) {
            myCurrentRoom = myFloor.getRoom(myHeroRow, myHeroCol);
            boolean isMonster = false;
            for (final AbstractDungeonCharacter c : myCurrentRoom.getCharacters()) {
                if (c instanceof Monster) {
                    isMonster = true;
                    break;
                }
            }
            if (isMonster) {
                myCombatStatus = true;
                return true;
            }
        }
        return false;
    }

    public boolean endCombat() {
        if (myGameActive && myCombatStatus) {
            myCombatStatus = false;
            return true;
        }
        return false;
    }

    public void reveal(final Room theRoom) {
        if (theRoom == null) {
            throw new IllegalArgumentException("The Room is null.");
        }
        theRoom.setExplored(true);
    }

    private boolean outOfBounds(final int thePosition) {
        return thePosition < 0 || thePosition >= DUNGEON_SIZE;
    }
    private void createCharacter() {
        //Ask for Hero Class and Name
        myHero = new Warrior("Hero Name");
    }

    private void setGameActive(final boolean theState) {
        myGameActive = theState;
    }
}
