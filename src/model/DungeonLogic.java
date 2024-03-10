package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class DungeonLogic {
    public static final DungeonLogic MY_INSTANCE;
    static {
        try {
            MY_INSTANCE = new DungeonLogic();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static final int DUNGEON_SIZE = 5;
    private final PropertyChangeSupport myChanges
        = new PropertyChangeSupport(this);

    private final StringBuilder myMessages = new StringBuilder();
    private Hero myHero;
    private Floor myFloor;
    private Inventory myInventory;
    private static boolean myGameActive;
    private boolean myCombatStatus;
    private int myFloorLevel;
    private int myHeroRow;
    private int myHeroCol;
    private Room myCurrentRoom;
    private Room myLastRoom;

    private DungeonLogic() throws SQLException {
        startGame();
    }

    public void save() {
        StringBuilder saveState = new StringBuilder();
//         StringBuilder.append(myFloorLevel);
//         for each room of the Floor, append room serial to saveState
//         append player stats to saveState
//         append Inventory to saveState
//         save saveState to an external text file
        myChanges.firePropertyChange("Can Load", false, true);
    }

    private void startGame() throws SQLException {
        myFloorLevel = 1;
        setGameActive(true);
        myFloor = new Floor(myFloorLevel, DUNGEON_SIZE);
        myInventory = new Inventory();
        final Room startingRoom = myFloor.getStartingRoom();
        myCurrentRoom = startingRoom;
        myLastRoom = startingRoom;
        reveal(myCurrentRoom);
    }

    public void changeFloor() throws SQLException {
        myFloorLevel++;
        myFloor = new Floor(myFloorLevel, DUNGEON_SIZE);
        final Room startingRoom = myFloor.getStartingRoom();
        startingRoom.addCharacter(myHero);
        myHeroCol = startingRoom.getCol();
        myHeroRow = startingRoom.getRow();
        myCurrentRoom = startingRoom;
        reveal(myCurrentRoom);
        myChanges.firePropertyChange("Dir",null,true);
    }

    public void createCharacter(final String theName, final int theClass) throws SQLException {
        if (theClass == 0) {
            myHero = new Warrior(theName);
        } else if (theClass == 1) {
            myHero = new Rogue(theName);
        } else if (theClass == 2) {
            myHero = new Mage(theName);
        } else {
            throw new IllegalArgumentException("Invalid Class on Character Creation!");
        }
        myFloor.getStartingRoom().addCharacter(myHero);
        myHeroCol = myFloor.getStartingRoom().getCol();
        myHeroRow = myFloor.getStartingRoom().getRow();
        myChanges.firePropertyChange("Hero",null,true);
        myChanges.firePropertyChange("Dir",null,true);
    }

    public void setGameActive(final boolean theState) {
        myGameActive = theState;
        if (theState) {
            myChanges.firePropertyChange("Can Save", false, true);
        }
    }

    public boolean getGameActive() {
        return myGameActive;
    }
    public boolean getCombatStatus() {
        return myCombatStatus;
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
        final Set<Room> set = new HashSet<>();
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

    public void startCombat() {
        if (myGameActive && !myCombatStatus && !outOfBounds(myHeroCol) && !outOfBounds(myHeroRow)) {
            boolean isMonster = false;
            for (final AbstractDungeonCharacter c : myCurrentRoom.getCharacters()) {
                if (c instanceof Monster) {
                    isMonster = true;
                    myMessages.append("You've encountered a ").append(c.getName()).append("!\n");
                    trimMessage();
                    myChanges.firePropertyChange("MESSAGE", null, myMessages);
                    break;
                }
            }
            if (isMonster) {
                myCombatStatus = true;
                myChanges.firePropertyChange("Dir",true,false);
            }
        }
    }

    public void endCombat() {
        if (myGameActive && myCombatStatus) {
            myCombatStatus = false;
            reveal(myCurrentRoom);
            myMessages.append("You defeated the enemy!\n");
            trimMessage();
            myChanges.firePropertyChange("MESSAGE", null, myMessages);
            myChanges.firePropertyChange("COMBAT STATUS", true, false);
        }
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
        if (myCurrentRoom.canWalkNorth() && !myCombatStatus) {
            myLastRoom = myCurrentRoom;
            myCurrentRoom.removeCharacter(myHero);
            myCurrentRoom.getNorth().addCharacter(myHero);
            myCurrentRoom = myCurrentRoom.getNorth();
            myHeroCol = myCurrentRoom.getCol();
            myHeroRow = myCurrentRoom.getRow();
            reveal(myCurrentRoom);
            applyEffects();
            myChanges.firePropertyChange("North", null,true);
        }
    }

    public void moveRight() {
        if (myCurrentRoom.canWalkEast() && !myCombatStatus) {
            myLastRoom = myCurrentRoom;
            myCurrentRoom.removeCharacter(myHero);
            myCurrentRoom.getEast().addCharacter(myHero);
            myCurrentRoom = myCurrentRoom.getEast();
            myHeroCol = myCurrentRoom.getCol();
            myHeroRow = myCurrentRoom.getRow();
            reveal(myCurrentRoom);
            applyEffects();
            myChanges.firePropertyChange("East", null,true);
        }
    }

    public void moveDown() {
        if (myCurrentRoom.canWalkSouth() && !myCombatStatus) {
            myLastRoom = myCurrentRoom;
            myCurrentRoom.removeCharacter(myHero);
            myCurrentRoom.getSouth().addCharacter(myHero);
            myCurrentRoom = myCurrentRoom.getSouth();
            myHeroCol = myCurrentRoom.getCol();
            myHeroRow = myCurrentRoom.getRow();
            reveal(myCurrentRoom);
            applyEffects();
            myChanges.firePropertyChange("South", null,true);
        }
    }

    public void moveLeft() {
        if (myCurrentRoom.canWalkWest() && !myCombatStatus) {
            myLastRoom = myCurrentRoom;
            myCurrentRoom.removeCharacter(myHero);
            myCurrentRoom.getWest().addCharacter(myHero);
            myCurrentRoom = myCurrentRoom.getWest();
            myHeroCol = myCurrentRoom.getCol();
            myHeroRow = myCurrentRoom.getRow();
            reveal(myCurrentRoom);
            applyEffects();
            myChanges.firePropertyChange("West", null,true);
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
                int before = myInventory.getCount((AbstractEquipment)i);
                final AbstractConsumable consumable = (AbstractConsumable)i;
                myInventory.addItem(consumable);
                myCurrentRoom.removeItem(i);
                myMessages.append("You acquired ").append(consumable.getQuantity()).append(' ').append(consumable.getName()).append("s! \n");
                myChanges.firePropertyChange(i.getName(), before, myInventory.getCount((AbstractEquipment)i));
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

    public boolean useItem(final AbstractEquipment theItem) {
        int before = 0;
        if (theItem.getType().equals("CONSUMABLE")) {
            before = getInventory().getCount(theItem);
        }
        boolean b = myInventory.useItem(theItem);
        myHero.healOrDamage(myHero.getMaxHP()/2);
        myChanges.firePropertyChange(theItem.getName(), before, myInventory.getCount(theItem));
        return b;
    }

    public void flee() {
        if (myCombatStatus) {
            myCurrentRoom.removeCharacter(myHero);
            myCurrentRoom = myLastRoom;
            myCurrentRoom.addCharacter(myHero);
            myHeroCol = myCurrentRoom.getCol();
            myHeroRow = myCurrentRoom.getRow();
            myCombatStatus = false;
            myChanges.firePropertyChange("COMBAT STATUS", true, false);
            myChanges.firePropertyChange("FLED", false, true);
        }
    }
    private void trimMessage() {
        if (myMessages.length() > 300) {
            myMessages.delete(0, 50);
        }
    }

    public void sendMessage(final String theMessage) {
        if (theMessage == null) {
            throw new NullPointerException("The message is null!");
        }

        myMessages.append(theMessage);
        trimMessage();
        myChanges.firePropertyChange("MESSAGE", null, myMessages);
    }
}
