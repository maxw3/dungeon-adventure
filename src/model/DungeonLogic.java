package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public final class DungeonLogic {
    private static final DungeonLogic MY_INSTANCE = new DungeonLogic();
    private static final int DUNGEON_SIZE = 5;
    private final PropertyChangeSupport myChanges
        = new PropertyChangeSupport(this);

    private Hero myHero;
    private Floor myFloor;
    private Inventory myInventory;
    private boolean myGameActive;
    private String mySaveState;
    private int myFloorLevel;

    private DungeonLogic() {
        startGame();
    }

    private void startGame() {
        myFloorLevel = 1;
        setGameActive(true);
        myFloor = new Floor(myFloorLevel, DUNGEON_SIZE);
        createCharacter();
        myInventory = new Inventory();

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
        myFloor.removeCharacter(position[1], position[0], myHero);
        position[0] = Math.max(position[0] - 1, 0);
        myFloor.addCharacter(position[1], position[0], myHero);
    }

    public void moveRight(){
        int[] position = myHero.getPosition();
        myFloor.removeCharacter(position[1], position[0], myHero);
        position[1] = Math.min(position[1] + 1, myFloor.getSize() - 1);
        myFloor.addCharacter(position[1], position[0], myHero);
    }

    public void moveDown() {
        int[] position = myHero.getPosition();
        myFloor.removeCharacter(position[1], position[0], myHero);
        position[0] = Math.min(position[0] + 1, myFloor.getSize() - 1);
        myFloor.addCharacter(position[1], position[0], myHero);
    }

    public void moveLeft() {
        int[] position = myHero.getPosition();
        myFloor.removeCharacter(position[1], position[0], myHero);
        position[1] = Math.max(position[1] - 1, 0);
        myFloor.addCharacter(position[1], position[0], myHero);
    }
}
