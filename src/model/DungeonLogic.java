package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.SQLException;

public final class DungeonLogic {
    private static final DungeonLogic MY_INSTANCE;

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

    private Hero myHero;
    private Floor myFloor;
    private Inventory myInventory;
    private boolean myGameActive;
    private String mySaveState;
    private int myFloorLevel;

    private DungeonLogic() throws SQLException {
        startGame();
    }

    private void startGame() throws SQLException {
        myFloorLevel = 1;
        setGameActive(true);
        myFloor = new Floor(myFloorLevel, DUNGEON_SIZE);
        createCharacter();
        myInventory = new Inventory();

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
}
