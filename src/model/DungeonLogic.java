/*
 *  Dungeon Adventure Project for TCSS 360
 *  Winter 2024, Jordan, Terence, Max, and Gabriel
 */

package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The main class of the game.
 * Starts the game
 * Saves, and loads
 * calls the construction of the dungeon
 * @author Jordan, Max, Gabriel, Terence
 * @version Winter 2024
 */
public final class DungeonLogic implements Serializable {
    /**
     * The instance of this Dungeon Logic
     */
    private static DungeonLogic MY_INSTANCE;
    static {
        try {
            MY_INSTANCE = new DungeonLogic();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Is the game currently active?
     */
    private static boolean myGameActive;
    /**
     * The length of the cube shaped dungeon
     */
    private static final int DUNGEON_SIZE = 5;
    /**
     * Property Change Support
     */
    private transient PropertyChangeSupport myChanges;

    private PropertyChangeSupport myOldChanges;

    /**
     * The messages to print to the console
     */
    private final StringBuilder myMessages;
    /**
     * The user controlled hero
     */
    private Hero myHero;
    /**
     * The current floor layout that the hero is in
     */
    private Floor myFloor;
    /**
     * The inventory of the hero
     */
    private Inventory myInventory;

    /**
     * Is the hero currently in combat?
     */
    private boolean myCombatStatus;
    /**
     * The floor that the hero is in
     */
    private int myFloorLevel;
    /**
     * X-coord of the Hero
     */
    private int myHeroRow;
    /**
     * Y-coord of the Hero
     */
    private int myHeroCol;
    /**
     * The current room the hero is in
     */
    private Room myCurrentRoom;
    /**
     * The previous room the hero was in
     */
    private Room myLastRoom;
    /**
     * The current enemy
     */
    private Monster myEnemy;
    /**
     * How many save files there are in the save folder
     */
    private int mySaveCount;
    private int myStepCount;

    /**
     * private constructor to avoid calling from outside
     * @throws SQLException could not query monster data
     */

    private DungeonLogic() throws SQLException {
        myChanges = new PropertyChangeSupport(this);
        myMessages = new StringBuilder();
        myEnemy =  MonsterFactory.createSkeleton(myFloorLevel);
        mySaveCount = 0;
        startGame();
    }

    /**
     * save the current game
     * @throws IOException file not found
     */
    public void save() throws IOException {
        final File f = new File("saves\\" + myHero.getCharName() + (++mySaveCount) + ".adv");
        final FileOutputStream file = new FileOutputStream(f);
        final ObjectOutputStream out = new ObjectOutputStream(file);
        out.writeObject(this);
        out.close();
        file.close();
        myChanges.firePropertyChange("Can Load", false, true);
    }

    /**
     * Load a game from an existing save file
     * @param theFile the save file
     * @throws IOException file not found
     * @throws ClassNotFoundException class not found
     */
    public void load(final File theFile) throws IOException, ClassNotFoundException, SQLException {
        myCombatStatus = false;
        endCombat();
        myOldChanges = new PropertyChangeSupport(this);
        for (PropertyChangeListener listener : myChanges.getPropertyChangeListeners()) {
            myOldChanges.addPropertyChangeListener(listener);
        }

        FileInputStream file = new FileInputStream(theFile);
        ObjectInputStream in = new ObjectInputStream(file);
        MY_INSTANCE = (DungeonLogic)in.readObject();
        in.close();
        file.close();
        MY_INSTANCE.fix(myOldChanges);
    }

    /**
     * refresh the screen
     */
    public void updateView() {
        int hp = myHero.getHP();
        int hPotions = myInventory.getCount(new HealthPotion());
        int vPotions = myInventory.getCount(new VisionPotion());
        myChanges.firePropertyChange("HP CHANGE", null, hp);
        myChanges.firePropertyChange("Dir", false, true);
        myChanges.firePropertyChange("Health Potion", null, hPotions);
        myChanges.firePropertyChange("Vision Potion", null, vPotions);
        myChanges.firePropertyChange("UPDATE MAP", false, true);
        myChanges.firePropertyChange("MESSAGE", null, myMessages);
        myChanges.firePropertyChange("COMBAT STATUS", !myCombatStatus, myCombatStatus);
    }

    /**
     * Start a new game
     * @throws SQLException could not query hero data
     */
    public void startGame() throws SQLException {
        if (myGameActive) {
            setGameActive(false);
        }
        myFloorLevel = 1;
        myStepCount = 0;
        myInventory = new Inventory();
        myCombatStatus = false;
        setGameActive(true);
        setNewFloor();
        myMessages.delete(0, myMessages.length());
    }

    /**
     * progress to the next floor
     * @throws SQLException could not query hero data
     */
    public void nextFloor() throws SQLException {
        myFloorLevel++;
        myHero.levelUp();
        sendMessage("You levelled up!");
        myChanges.firePropertyChange("LEVEL UP", null,myHero.getMaxHP());
        setNewFloor();
    }


    /**
     * Create the hero
     * @param theName Name of the Hero
     * @param theClass  Class of the Hero
     * @throws SQLException could not query hero data
     */
    public void createCharacter(final String theName, final int theClass) throws SQLException {
        if (theClass == 0) {
            myHero = new Warrior(theName);
        } else if (theClass == 1) {
            myHero = new Rogue(theName);
        } else if (theClass == 2) {
            myHero = new Mage(theName);
        } else {
            setGameActive(false);
        }
        if (DUNGEON_SIZE == 2) {
            myHero.setMaxHP(1000000000);
            myHero.healOrDamage(1000000000);
        }
        myFloor.getStartingRoom().addCharacter(myHero);
        myHeroCol = myFloor.getStartingRoom().getCol();
        myHeroRow = myFloor.getStartingRoom().getRow();
        myChanges.firePropertyChange("Hero",null,myHero.getMyClass() + ".png");
        myChanges.firePropertyChange("Dir",null,true);
    }

    /**
     * Set game status
     * @param theState the Status
     */
    public void setGameActive(final boolean theState) {
        myGameActive = theState;
        myChanges.firePropertyChange("GAME STATE", !theState, theState);
        if (theState) {
            myChanges.firePropertyChange("UPDATE MAP", false, true);
            myChanges.firePropertyChange("Health Potion",0,0);
            myChanges.firePropertyChange("Vision Potion",0,0);
        }
    }

    /**
     * Getter for game status
     * @return  The status
     */
    public boolean getGameActive() {
        return myGameActive;
    }

    /**
     * Getter of myCombatStatus
     * @return The Status
     */
    public boolean getCombatStatus() {
        return myCombatStatus;
    }

    /**
     * Getter for the current instance
     * @return the instance
     */
    public static DungeonLogic getDungeonInstance() {
        if (MY_INSTANCE == null) {
            try {
                MY_INSTANCE = new DungeonLogic();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return MY_INSTANCE;
    }

    /**
     * Getter for the hero
     * @return The hero
     */
    public Hero getHero() {
        return myHero;
    }

    /**
     * Getter for the hero's inventory
     * @return The inventory
     */
    public Inventory getInventory() {
        return myInventory;
    }

    /**
     * Getter for the room the hero is in
     * @return The room
     */
    public Room getCurrentRoom() {
        return myCurrentRoom;
    }

    /**
     * Getter for the latest Monster the hero is fighting
     * @return  The Monster
     */
    public Monster getEnemy() {
        return myEnemy;
    }

    /**
     * Getter for the adjacent rooms of the given room
     * @param theRoom the room
     * @return the adjacent rooms
     */
    public Set<Room> getNeighbors(final Room theRoom) {
        final Set<Room> set = new HashSet<>();
        Room[][] rooms = myFloor.getRooms();
        int row = theRoom.getRow();
        int col = theRoom.getCol();
        if (row > 0) {
            set.add(rooms[row - 1][col]);
        }
        if (row < myFloor.getSize() - 1) {
            set.add(rooms[row + 1][col]);
        }
        if (col > 0) {
            set.add(rooms[row][col - 1]);
        }
        if (col < myFloor.getSize() - 1) {
            set.add(rooms[row][col + 1]);
        }
        return set;
    }
    public int getSteps() {
        return myStepCount;
    }
    public int getFloorLevel() {
        return myFloorLevel;
    }
    /**
     * Start combat with myEnemy
     */
    public void startCombat() {
        if (myGameActive && !myCombatStatus && !myFloor.outOfBounds(myHeroCol) &&
            !myFloor.outOfBounds(myHeroRow)) {
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

    /**
     * End Combat with myEnemy
     */
    public void endCombat() {
        if (myGameActive && myCombatStatus) {
            myCombatStatus = false;
            explore(myCurrentRoom);
            myMessages.append("You defeated the enemy!\n");
            trimMessage();
            myChanges.firePropertyChange("MESSAGE", null, myMessages);
            myChanges.firePropertyChange("COMBAT STATUS", !myCombatStatus, myCombatStatus);
            myChanges.firePropertyChange("Dir", false, true);
            myChanges.firePropertyChange("Room Content", null, "Dead " + myEnemy.getName() + ".png");
        } else if (myCombatStatus) {
            myCombatStatus = false;
        }
    }

    /**
     * set the room to explored
     * @param theRoom the room
     */
    public void explore(final Room theRoom) {
        if (theRoom == null) {
            throw new IllegalArgumentException("The Room is null.");
        }
        theRoom.setExplored(true);
        theRoom.setVisibilty(true);
    }

    /**
     * reveal the contents of the adjacent rooms
     * @param theRoom the room in the middle
     */
    public void reveal(final Room theRoom) {
        if (theRoom == null) {
            throw new IllegalArgumentException("The Room is null.");
        }
        theRoom.setVisibilty(true);
    }

    /**
     * disable the effects of the consumed vision potion
     */
    public void expireVisPot() {
        for (Room r: getNeighbors(myLastRoom)) {
            if(!r.isExplored()) {
                r.setVisibilty(false);
            }
        }
    }


    /**
     * The floor contents and state in String from
     * @return the floor layout represented in ascii symbols
     */
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

    /**
     * Setter for myEnemy
     * @param theEnemy the new enemy
     */
    public void setEnemy(final Monster theEnemy) {
        if (theEnemy == null) {
            throw new NullPointerException();
        }

        myEnemy = theEnemy;
        myChanges.firePropertyChange("IMAGE", null, theEnemy.getImage());
    }

    /**
     * hero moves north
     */
    public void moveUp() {
        if (myCurrentRoom.canWalkNorth() && !myCombatStatus) {
            myLastRoom = myCurrentRoom;
            myCurrentRoom.getNorth().addCharacter(myHero);
            myCurrentRoom = myCurrentRoom.getNorth();
            applyEffects();
        }
    }

    /**
     * hero moves east
     */
    public void moveRight() {
        if (myCurrentRoom.canWalkEast() && !myCombatStatus) {
            myLastRoom = myCurrentRoom;
            myCurrentRoom.getEast().addCharacter(myHero);
            myCurrentRoom = myCurrentRoom.getEast();
            applyEffects();
        }
    }

    /**
     * hero moves south
     */
    public void moveDown() {
        if (myCurrentRoom.canWalkSouth() && !myCombatStatus) {
            myLastRoom = myCurrentRoom;
            myCurrentRoom.getSouth().addCharacter(myHero);
            myCurrentRoom = myCurrentRoom.getSouth();
            applyEffects();
        }
    }

    /**
     * hero moves west
     */
    public void moveLeft() {
        if (myCurrentRoom.canWalkWest() && !myCombatStatus) {
            myLastRoom = myCurrentRoom;
            myCurrentRoom.getWest().addCharacter(myHero);
            myCurrentRoom = myCurrentRoom.getWest();
            applyEffects();
        }
    }


    /**
     * collect the items in the room into the inventory
     */
    public void collect() {
        final List<Item> items = myCurrentRoom.getItems();
//        if(items.size() == 0) {
//            myChanges.firePropertyChange("Room Content", null, "Empty.png");
//        }
        for (int pos = 0; pos < items.size(); pos++) {
            final Item i = items.get(pos);
//            myChanges.firePropertyChange("Room Content", null, i.getName() + ".png");
            if (i.getType().equals("PIT")) {
                final int oldHP = myHero.getHP();
                final int damage = ((Pit)i).activate(myHero);
                myCurrentRoom.removeItem(i);
                myMessages.append("You activated a pit, and took ").append(damage).append(" damage! \n");
                trimMessage();
                myChanges.firePropertyChange("MESSAGE", null, myMessages);
                myChanges.firePropertyChange("HP CHANGE", oldHP, myHero.getHP());
                myChanges.firePropertyChange("IMAGE", null, i.getImage());
            } else if (i.getType().equals("CONSUMABLE")) {
                int before = myInventory.getCount((AbstractEquipment)i);
                final AbstractConsumable consumable = (AbstractConsumable)i;
                myInventory.addItem(consumable);
                myCurrentRoom.removeItem(i);
                myMessages.append("You acquired ").append(consumable.getQuantity()).append(' ').append(consumable.getName()).append("s! \n");
                myChanges.firePropertyChange(i.getName(), before, myInventory.getCount((AbstractEquipment)i));
                trimMessage();
                myChanges.firePropertyChange("MESSAGE", null, myMessages);
                myChanges.firePropertyChange("IMAGE", null, i.getImage());
            } else if (i.getType().equals("PILLAR")) {
                final Pillar pillar = (Pillar)i;
                myInventory.addItem(pillar);
                myCurrentRoom.removeItem(i);
                myMessages.append("You acquired the ").append(pillar.getName()).append(" pillar of OO! \n");
                trimMessage();
                myChanges.firePropertyChange("MESSAGE", null, myMessages);
                myChanges.firePropertyChange("COMPLETED FLOOR", false, true);
                myChanges.firePropertyChange("UPDATE MAP", false, true);
            }
        }
    }

    /**
     * use the item
     * @param theItem the item
     * @return did we use it?
     */
    public boolean useItem(final AbstractEquipment theItem) {
        int before = 0;
        int oldHp = myHero.getHP();
        if (theItem.getType().equals("CONSUMABLE")) {
            before = getInventory().getCount(theItem);
        }
        boolean b = myInventory.useItem(theItem);
        myChanges.firePropertyChange(theItem.getName(), before, myInventory.getCount(theItem));
        myChanges.firePropertyChange("HP CHANGE", oldHp, myHero.getHP());
        return b;
    }

    /**
     * escape from combat
     */
    public void flee() {
        if (myCombatStatus) {
            myCurrentRoom.removeCharacter(myHero);
            myCurrentRoom = myLastRoom;
            myCurrentRoom.addCharacter(myHero);
            myHeroCol = myCurrentRoom.getCol();
            myHeroRow = myCurrentRoom.getRow();
            myCombatStatus = false;
            myChanges.firePropertyChange("COMBAT STATUS", true, false);
            myChanges.firePropertyChange("UPDATE MAP", false, true);
        }
    }


    /**
     * fire the message for the View to catch and use
     * @param theMessage the message for the console
     */
    public void sendMessage(final String theMessage) {
        if (theMessage == null) {
            throw new NullPointerException("The message is null!");
        }
        myMessages.append(theMessage);
        trimMessage();
        myChanges.firePropertyChange("MESSAGE", null, myMessages);
    }

    private void fix(final PropertyChangeSupport oldChanges) {
        myChanges = new PropertyChangeSupport(this);
        for (PropertyChangeListener listener : oldChanges.getPropertyChangeListeners()) {
            myChanges.addPropertyChangeListener(listener);
        }
    }

    /**
     * format the message
     */
    private void trimMessage() {
        if (myMessages.length() > 300) {
            myMessages.delete(0, 50);
        }
    }

    /**
     * trigger the room's events
     */
    private void applyEffects() {
        myChanges.firePropertyChange("EMPTY", false, true);
        myStepCount++;
        expireVisPot();
        myLastRoom.removeCharacter(myHero);
        myHeroCol = myCurrentRoom.getCol();
        myHeroRow = myCurrentRoom.getRow();
        explore(myCurrentRoom);
        myChanges.firePropertyChange("Dir", null,true);
        final boolean previous = myCombatStatus;
        startCombat();
        if (!myCombatStatus) {
            collect();
        }
        myChanges.firePropertyChange("COMBAT STATUS", previous, myCombatStatus);
    }

    /**
     * Creates a new floor for the Hero to explore
     * @throws SQLException could not query hero data
     */
    private void setNewFloor() throws SQLException {
        myFloor = new Floor(myFloorLevel, DUNGEON_SIZE);
        final Room startingRoom = myFloor.getStartingRoom();
        startingRoom.addCharacter(myHero);
        myHeroCol = startingRoom.getCol();
        myHeroRow = startingRoom.getRow();
        myCurrentRoom = startingRoom;
        myLastRoom = myCurrentRoom;
        explore(myCurrentRoom);
        myChanges.firePropertyChange("COMBAT STATUS", true, false);
        myChanges.firePropertyChange("Dir",null,true);
        myChanges.firePropertyChange("UPDATE MAP",null,true);
    }
}
