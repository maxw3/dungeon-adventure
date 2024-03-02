package model;

import java.beans.PropertyChangeListener;
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

        myFloor.addCharacter(0, 0, myHero);

    }

    private void createCharacter() {
        //Ask for Hero Class and Name
        myHero = new Warrior("Hero Name");
    }

    private void setGameActive(final boolean theState) {
        myGameActive = theState;
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
        if (theRoom.canWalkNorth() != null) {
            set.add(theRoom.getNorth());
        }
        if (theRoom.canWalkSouth() != null) {
            set.add(theRoom.getSouth());
        }
        if (theRoom.canWalkWest() != null) {
            set.add(theRoom.getWest());
        }
        if (theRoom.canWalkEast() != null) {
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

    public String getFloorString(){
        return myFloor.toString();
    }

    /**
     * adds a property change listener to the listener provided
     *
     * @param theListener   The listener (Frame, Panel, etc)
     */
    public void addPropertyChangeListener(PropertyChangeListener theListener) {
        myChanges.addPropertyChangeListener(theListener);
    }

    /**
     * removes a property change listener to the listener provided
     *
     * @param theListener   The listener (Frame, Panel, etc)
     */
    public void removePropertyChangeListener(PropertyChangeListener theListener) {
        myChanges.removePropertyChangeListener(theListener);
    }

    public void moveUp() {
        int[] position = myHero.getPosition();

        Room currentRoom = myFloor.getRooms()[position[0]][position[1]];

        if(currentRoom.canWalkNorth() != null){
            // currentRoom.removeCharacter(myHero);
            // currentRoom.canWalkNorth().addCharacter(myHero);
            myFloor.removeCharacter(position[1], position[0], myHero);
            position[0] = Math.max(position[0] - 1, 0);
            myFloor.addCharacter(position[1], position[0], myHero);
        }

    }

    public void moveRight(){
        int[] position = myHero.getPosition();

        Room currentRoom = myFloor.getRooms()[position[0]][position[1]];

        if(currentRoom.canWalkEast() != null){
            myFloor.removeCharacter(position[1], position[0], myHero);
            position[1] = Math.min(position[1] + 1, myFloor.getSize() - 1);
            myFloor.addCharacter(position[1], position[0], myHero);
        }
    }

    public void moveDown() {
        int[] position = myHero.getPosition();

        Room currentRoom = myFloor.getRooms()[position[0]][position[1]];

        if(currentRoom.canWalkSouth() != null){
            myFloor.removeCharacter(position[1], position[0], myHero);
            position[0] = Math.min(position[0] + 1, myFloor.getSize() - 1);
            myFloor.addCharacter(position[1], position[0], myHero);
        }
    }

    public void moveLeft() {
        int[] position = myHero.getPosition();

        Room currentRoom = myFloor.getRooms()[position[0]][position[1]];

        if(currentRoom.canWalkWest() != null){
            myFloor.removeCharacter(position[1], position[0], myHero);
            position[1] = Math.max(position[1] - 1, 0);
            myFloor.addCharacter(position[1], position[0], myHero);
        }
    }
}
