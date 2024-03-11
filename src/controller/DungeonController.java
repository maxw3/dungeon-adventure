package controller;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.*;

import org.sqlite.SQLiteDataSource;
import view.DungeonView;

public class DungeonController extends JPanel implements PropertyChangeListener {

    public static JFrame myFrame;
    private static final DungeonController MY_INSTANCE;
    private static final Toolkit KIT = Toolkit.getDefaultToolkit();
    private static final Dimension SCREEN_SIZE = KIT.getScreenSize();
    private final DungeonLogic myDungeon;
    private Hero myHero;
    private final Inventory myInventory;
    public static SQLiteDataSource DATA_SOURCE = new SQLiteDataSource();
    public static Connection CONNECTION;
    public static final Statement STATEMENT;
    static {
        try {
            DATA_SOURCE.setUrl("jdbc:sqlite:dungeonData.sqlite");
            CONNECTION = DATA_SOURCE.getConnection();
            STATEMENT = CONNECTION.createStatement();
            MY_INSTANCE = new DungeonController();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public DungeonController() {
        myDungeon = model.DungeonLogic.getDungeonInstance();
        myHero = myDungeon.getHero();
        myInventory = myDungeon.getInventory();
    }

    public static void main(final String[] theArgs) {
        EventQueue.invokeLater(DungeonController::createAndShowGUI);
    }

    public static void createAndShowGUI() {

        // Main Frame/Window
        myFrame = new JFrame("Dungeon Adventure");

        // Main Panel, Contains the Game
        final DungeonView mainPanel = new DungeonView();
        mainPanel.addPropertyChangeListener(MY_INSTANCE);

        // Size of the Main Window
        final Dimension frameSize = new Dimension(1280, 720);

        // Adds property change listeners to the main panel
        DungeonLogic.getDungeonInstance().addPropertyChangeListener(mainPanel);
        DungeonLogic.getDungeonInstance().addPropertyChangeListener(MY_INSTANCE);

        // Sets the Content Pane of the frame to the Main Panel
        myFrame.setContentPane(mainPanel);

        // Disables "window exit" when clicking the X on the window
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Window Listener
        myFrame.addWindowListener(new WindowAdapter() {
            /**
             * Listener when user tries to close the window
             * pops up an option panel that asks for confirmation
             * on whether the user really wants to close the window
             * If confirmed, closes the window
             * Otherwise, do nothing.
             *
             * @param theCloseEvent the event to be processed
             */
            @Override
            public void windowClosing(final WindowEvent theCloseEvent) {
                final String[] exitOptions = {"Yes", "No"};
                final int promptResult = JOptionPane.showOptionDialog(null,
                    "Would you like to save progress before you exit?", "Save on Exit",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
                    null, exitOptions, exitOptions[1]);
                if (promptResult == JOptionPane.YES_OPTION)  {
//                    DungeonLogic.save();
                }
                System.exit(0);
            }
        });

        // Sets the size of the window
        myFrame.setSize(frameSize);

        // Sets the location of the window
        myFrame.setLocation(SCREEN_SIZE.width / 2 - myFrame.getWidth() / 2,
            SCREEN_SIZE.height / 2 - myFrame.getHeight() / 2);

        // Makes the main window visible
        myFrame.setVisible(true);
    }

    /**
     * Helper method for the Use Hit Point Potion Buttons.
     */
// This code does not allow for the updating of potion counts in view (routed through DungeonLogic now)
//    private void drinkPotion() {
//        if (myDungeon.getGameActive()) {
//            myDungeon.getInventory().useItem(new HealthPotion(1));
//        } else {
//            JOptionPane.showMessageDialog(null, "You haven't started a new save yet!");
//        }
//    }

    public void fight (final int theChoice) {
        if (checkGameStatus()) {
            Monster enemy = myDungeon.getEnemy();
            if (myHero.getHP() > 0 && enemy.getHP() > 0) {
                if (theChoice == 1) {
                    String mResult = enemy.attack(myHero);
                    String hResult = myHero.attack(enemy);
                    myDungeon.sendMessage("The " + enemy.getName() + " " + mResult + "\n");
                    myDungeon.sendMessage("You " + hResult + "\n");
                } else if (theChoice == 2) {
                    String mResult = enemy.attack(myHero);
                    String hResult = myHero.skill(enemy);
                    myDungeon.sendMessage("The " + enemy.getName() + " " + mResult + "\n");
                    myDungeon.sendMessage("You " + hResult + "\n");

                } else if (theChoice == 3) {
                    boolean success = myDungeon.useItem(new HealthPotion());
                    if (success) {
                        String mResult = enemy.attack(myHero);
                        myDungeon.sendMessage("You healed for up to " + myHero.getMaxHP() / 2 + "HP! \n");
                        myDungeon.sendMessage("The " + enemy.getName() + " " + mResult + "\n");
                    }
                } else if (theChoice == 4) {
                    String mResult = enemy.attack(myHero);
                    myDungeon.flee();
                    myDungeon.sendMessage("The " + enemy.getName() + " " + mResult + "\n");
                    myDungeon.sendMessage("You fled the encounter!\n");
                }
            }
            if (myHero.getHP() <= 0) {
                endGame(false);
            } else if (enemy.getHP() <= 0) {
                //get pillar or escape through exit if available
                myDungeon.endCombat();
                myDungeon.collect();
                myDungeon.getCurrentRoom().getCharacters().remove(enemy);
            }
        }
    }

    /**
     * Ends the current game making it unable to be saved or loaded to.
     *
     * @param theState  how the game ended.
     *                  True means the Hero has successfully cleared the game.
     *                  False means the Hero has died
     */
    public void endGame(final boolean theState) {
        if (theState) { //beat the game
            JOptionPane.showMessageDialog(null, "Congratulations! You beat the game!");
            //show the stats window
            //show credits page
        } else {
            JOptionPane.showMessageDialog(null, "You have unfortunately met your end.");
            //start new game prompt
        }
    }

    private boolean checkGameStatus() {
        if (myDungeon.getGameActive()) {
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "You haven't started a new save yet!");
            return false;
        }
    }

    /**
     * This method gets called when a bound property is changed.
     *
     * @param theEvent A PropertyChangeEvent object describing the event source
     *            and the property that has changed.
     */
    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        final String s = theEvent.getPropertyName();
        if ("COMBAT STATUS".equals(s)) {
            if (theEvent.getNewValue().equals(true)) {
                for (AbstractDungeonCharacter c : myDungeon.getCurrentRoom().getCharacters()) {
                    if (c instanceof Monster) {
                        myDungeon.setEnemy((Monster)c);
                    }
                }
            }
        } else if ("COMPLETED FLOOR".equals(s)) {
            // Prompt view to offer to save game
            try {
                myDungeon.changeFloor();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else if ("Action".equals(s)) {
            if (myDungeon.getCombatStatus()) {
                try {
                    fight((int) (theEvent.getNewValue()));
                } catch (final NullPointerException exception) {
                    throw new NullPointerException("The current enemy is null! The buttons should be disabled!");
                }
            } else {
                if ((int)(theEvent.getNewValue()) == 3) {
                    boolean success = myDungeon.useItem(new HealthPotion());
                    if (success) {
                        myDungeon.sendMessage("You healed for up to " + myHero.getMaxHP() / 2 + "HP! \n");
                    }
                }
            }
        } else if ("Hero".equals(s)) {
            myHero = myDungeon.getHero();
        }
    }
}
