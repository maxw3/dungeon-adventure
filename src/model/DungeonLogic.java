package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.*;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class DungeonLogic implements Serializable {
    public static DungeonLogic MY_INSTANCE;
    static {
        try {
            MY_INSTANCE = new DungeonLogic();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static final int DUNGEON_SIZE = 5;
    private PropertyChangeSupport myChanges
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
    private Monster myEnemy = new Monster("Skeleton");
    private int mySaveCount = 0;

    private DungeonLogic() throws SQLException {
        startGame();
    }

    public void save() throws IOException {
        File f = new File("saves\\" + myHero.getCharName() + (++mySaveCount) + ".adv");
        FileOutputStream file = new FileOutputStream(f);
        ObjectOutputStream out = new ObjectOutputStream(file);
        out.writeObject(this);
        out.close();
        file.close();
        myChanges.firePropertyChange("Can Load", false, true);
    }

    public void load(final File theFile) throws IOException, ClassNotFoundException {
        FileInputStream file = new FileInputStream(theFile);
        ObjectInputStream in = new ObjectInputStream(file);
        MY_INSTANCE = (DungeonLogic)in.readObject();
        in.close();
        file.close();
        myChanges = new PropertyChangeSupport(this);
    }

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

    private void startGame() throws SQLException {
        myFloorLevel = 1;
        setGameActive(true);
        reset();
    }

    public void changeFloor() throws SQLException {
        myFloorLevel++;
        myHero.levelUp();
        sendMessage("You levelled up!");
        myChanges.firePropertyChange("LEVEL UP", null,myHero.getMaxHP());
        Inventory inv = myInventory;
        reset();
        myInventory = inv;
    }
    public void reset() throws SQLException {
        myFloor = new Floor(myFloorLevel, DUNGEON_SIZE);
        final Room startingRoom = myFloor.getStartingRoom();
        startingRoom.addCharacter(myHero);
        myHeroCol = startingRoom.getCol();
        myHeroRow = startingRoom.getRow();
        myCurrentRoom = startingRoom;
        myLastRoom = myCurrentRoom;
        explore(myCurrentRoom);
        myInventory = new Inventory();
        myChanges.firePropertyChange("Dir",null,true);
        myChanges.firePropertyChange("UPDATE MAP",null,true);
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
        myChanges.firePropertyChange("GAME STATE", !theState, theState);
        if (theState) {
            myChanges.firePropertyChange("UPDATE MAP", false, true);
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

    public Monster getEnemy() {
        return myEnemy;
    }

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
            explore(myCurrentRoom);
            myMessages.append("You defeated the enemy!\n");
            trimMessage();
            myChanges.firePropertyChange("MESSAGE", null, myMessages);
            myChanges.firePropertyChange("COMBAT STATUS", !myCombatStatus, myCombatStatus);
            myChanges.firePropertyChange("Dir", false, true);
        }
    }

    public void explore(final Room theRoom) {
        if (theRoom == null) {
            throw new IllegalArgumentException("The Room is null.");
        }
        theRoom.setExplored(true);
        theRoom.setVisibilty(true);
    }
    public void reveal(final Room theRoom) {
        if (theRoom == null) {
            throw new IllegalArgumentException("The Room is null.");
        }
        theRoom.setVisibilty(true);
    }
    public void expireVisPot() {
        Set<Room> rooms = getNeighbors(myCurrentRoom);
        for (Room r: rooms) {
            if(!r.isExplored()) {
                r.setVisibilty(false);
            }
        }
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

    public void setEnemy(final Monster theEnemy) {
        if (theEnemy == null) {
            throw new NullPointerException();
        }

        myEnemy = theEnemy;
    }
    public void moveUp() {
        if (myCurrentRoom.canWalkNorth() && !myCombatStatus) {
            myLastRoom = myCurrentRoom;
            expireVisPot();
            myCurrentRoom.removeCharacter(myHero);
            myCurrentRoom.getNorth().addCharacter(myHero);
            myCurrentRoom = myCurrentRoom.getNorth();
            myHeroCol = myCurrentRoom.getCol();
            myHeroRow = myCurrentRoom.getRow();
            explore(myCurrentRoom);
            myChanges.firePropertyChange("Dir", null,true);
            applyEffects();
        }
    }

    public void moveRight() {
        if (myCurrentRoom.canWalkEast() && !myCombatStatus) {
            myLastRoom = myCurrentRoom;
            expireVisPot();
            myCurrentRoom.removeCharacter(myHero);
            myCurrentRoom.getEast().addCharacter(myHero);
            myCurrentRoom = myCurrentRoom.getEast();
            myHeroCol = myCurrentRoom.getCol();
            myHeroRow = myCurrentRoom.getRow();
            explore(myCurrentRoom);
            myChanges.firePropertyChange("Dir", null,true);
            applyEffects();
        }
    }

    public void moveDown() {
        if (myCurrentRoom.canWalkSouth() && !myCombatStatus) {
            myLastRoom = myCurrentRoom;
            expireVisPot();
            myCurrentRoom.removeCharacter(myHero);
            myCurrentRoom.getSouth().addCharacter(myHero);
            myCurrentRoom = myCurrentRoom.getSouth();
            myHeroCol = myCurrentRoom.getCol();
            myHeroRow = myCurrentRoom.getRow();
            explore(myCurrentRoom);
            myChanges.firePropertyChange("Dir", null,true);
            applyEffects();
        }
    }

    public void moveLeft() {
        if (myCurrentRoom.canWalkWest() && !myCombatStatus) {
            myLastRoom = myCurrentRoom;
            expireVisPot();
            myCurrentRoom.removeCharacter(myHero);
            myCurrentRoom.getWest().addCharacter(myHero);
            myCurrentRoom = myCurrentRoom.getWest();
            myHeroCol = myCurrentRoom.getCol();
            myHeroRow = myCurrentRoom.getRow();
            explore(myCurrentRoom);
            myChanges.firePropertyChange("Dir", null,true);
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
                final int oldHP = myHero.getHP();
                final int damage = ((Pit)i).activate(myHero);
                myCurrentRoom.removeItem(i);
                myMessages.append("You activated a pit, and took ").append(damage).append(" damage! \n");
                trimMessage();
                myChanges.firePropertyChange("MESSAGE", null, myMessages);
                myChanges.firePropertyChange("HP CHANGE", oldHP, myHero.getHP());
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
                myChanges.firePropertyChange("UPDATE MAP", false, true);
            }
        }
    }

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
