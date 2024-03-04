package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class DungeonLogic {
    public static final DungeonLogic MY_INSTANCE = new DungeonLogic();
    private static final int DUNGEON_SIZE = 5;
    private final PropertyChangeSupport myChanges
        = new PropertyChangeSupport(this);

    private final StringBuilder myMessages = new StringBuilder();
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

    private DungeonLogic(final Hero theHero, final Inventory theInventory) {
        myFloorLevel = 1;
        setGameActive(true);
        myFloor = new Floor(myFloorLevel, DUNGEON_SIZE);
        myHero = theHero;
        myInventory = theInventory;
        final Room startingRoom = myFloor.getStartingRoom();
        startingRoom.addCharacter(myHero);
        myHeroCol = myHero.getPosition()[1];
        myHeroRow = myHero.getPosition()[0];
        myCurrentRoom = startingRoom;
        reveal(myCurrentRoom);
    }

    private void startGame() {
        myFloorLevel = 1;
        setGameActive(true);
        myFloor = new Floor(myFloorLevel, DUNGEON_SIZE);
        createCharacter();
        myInventory = new Inventory();
        final Room startingRoom = myFloor.getStartingRoom();
        startingRoom.addCharacter(myHero);
        myHeroCol = startingRoom.getCol();
        myHeroRow = startingRoom.getRow();
        myCurrentRoom = startingRoom;
        reveal(myCurrentRoom);
    }

    public void changeFloor() {
        myFloorLevel++;
        myFloor = new Floor(myFloorLevel, DUNGEON_SIZE);
        final Room startingRoom = myFloor.getStartingRoom();
        startingRoom.addCharacter(myHero);
        myHeroCol = startingRoom.getCol();
        myHeroRow = startingRoom.getRow();
        myCurrentRoom = startingRoom;
        reveal(myCurrentRoom);
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
            boolean isMonster = false;
            for (final AbstractDungeonCharacter c : myCurrentRoom.getCharacters()) {
                if (c instanceof Monster) {
                    isMonster = true;
                    myMessages.append("You've encountered a ").append(c.getClass().getSimpleName()).append("!\n");
                    trimMessage();
                    myChanges.firePropertyChange("MESSAGE", null, myMessages);
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
    public void addPropertyChangeListener(final PropertyChangeListener theListener) {
        myChanges.addPropertyChangeListener(theListener);
    }

    /**
     * removes a property change listener to the listener provided
     *
     * @param theListener   The listener (Frame, Panel, etc)
     */
    public void removePropertyChangeListener(final PropertyChangeListener theListener) {
        myChanges.removePropertyChangeListener(theListener);
    }

    public void moveUp() {
        if (myCurrentRoom.canWalkNorth()) {
            myCurrentRoom.removeCharacter(myHero);
            myCurrentRoom.getNorth().addCharacter(myHero);
            myCurrentRoom = myCurrentRoom.getNorth();
            myHeroCol = myHero.getPosition()[1];
            myHeroRow = myHero.getPosition()[0];
            reveal(myCurrentRoom);
            applyEffects();
        }
    }

    public void moveRight() {
        if (myCurrentRoom.canWalkEast()) {
            myCurrentRoom.removeCharacter(myHero);
            myCurrentRoom.getEast().addCharacter(myHero);
            myCurrentRoom = myCurrentRoom.getEast();
            myHeroCol = myHero.getPosition()[1];
            myHeroRow = myHero.getPosition()[0];
            reveal(myCurrentRoom);
            applyEffects();
        }
    }

    public void moveDown() {
        if (myCurrentRoom.canWalkSouth()) {
            myCurrentRoom.removeCharacter(myHero);
            myCurrentRoom.getSouth().addCharacter(myHero);
            myCurrentRoom = myCurrentRoom.getSouth();
            myHeroCol = myHero.getPosition()[1];
            myHeroRow = myHero.getPosition()[0];
            reveal(myCurrentRoom);
            applyEffects();
        }
    }

    public void moveLeft() {
        if (myCurrentRoom.canWalkWest()) {
            myCurrentRoom.removeCharacter(myHero);
            myCurrentRoom.getWest().addCharacter(myHero);
            myCurrentRoom = myCurrentRoom.getWest();
            myHeroCol = myHero.getPosition()[1];
            myHeroRow = myHero.getPosition()[0];
            reveal(myCurrentRoom);
            applyEffects();
        }
    }

    private void applyEffects() {
        final boolean previous = myCombatStatus;
        startCombat();
        if (!myCombatStatus) {
            collect();
        }
        myChanges.firePropertyChange("COMBAT STATUS", previous, myCombatStatus);
    }

    public void collect() {
        final List<Item> items = myCurrentRoom.getItems();

        for (int pos = 0; pos < items.size(); pos++) {
            final Item i = items.get(pos);
            if (i.getType().equals("PIT")) {
                final int damage = ((Pit)i).activate(myHero);
                myCurrentRoom.removeItem(i);
                myMessages.append("You activated a pit, and took ").append(damage).append(" damage! \n");
                trimMessage();
                myChanges.firePropertyChange("MESSAGE", null, myMessages);
            } else if (i.getType().equals("CONSUMABLE")) {
                final AbstractConsumable consumable = (AbstractConsumable)i;
                myInventory.addItem(consumable);
                myCurrentRoom.removeItem(i);
                myMessages.append("You acquired ").append(consumable.getQuantity()).append(' ').append(consumable.getName()).append("s! \n");
                trimMessage();
                myChanges.firePropertyChange("MESSAGE", null, myMessages);
            } else if (i.getType().equals("PILLAR")) {
                final Pillar pillar = (Pillar)i;
                myInventory.addItem(pillar);
                myCurrentRoom.removeItem(i);
                myMessages.append("You acquired the ").append(pillar.getName()).append(" pillar of OO! \n");
                trimMessage();
                myChanges.firePropertyChange("MESSAGE", null, myMessages);
                myChanges.firePropertyChange("COMPLETED FLOOR", false, true);
            }
        }
    }

    private void trimMessage() {
        if (myMessages.length() > 300) {
            myMessages.delete(0, 50);
        }
    }

}
