package model;

import java.beans.PropertyChangeSupport;

public final class DungeonLogic {
    private final static DungeonLogic myInstance = new DungeonLogic();

    private final PropertyChangeSupport myChanges
        = new PropertyChangeSupport(this);

    private Hero myHero;
    private Floor myFloor;
    private Inventory myInventory;
    private boolean myGameActive;
    private String mySaveState;
    private int myFloorLevel;
    private final int DUNGEON_SIZE = 5;

    private DungeonLogic() {
        startGame();
    }

    private void startGame(){
        myFloorLevel = 1;
        setGameActive(true);
        myFloor = new Floor(myFloorLevel, DUNGEON_SIZE);
        createCharacter();
        myInventory = new Inventory();

    }

    private void createCharacter(){
        //Ask for Hero Class and Name
        myHero = new Warrior("Hero Name");
    }

    private void setGameActive(final boolean theState){
        myGameActive = theState;
    }

    public boolean getGameActive() { return myGameActive; }
    public static DungeonLogic getDungeonInstance() { return myInstance; }

    public Hero getHero(){
        return myHero;
    }
    public Inventory getInventory(){
        return myInventory;
    }
}
