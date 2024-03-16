package controller;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
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

    /**
     * The main frame of the program
     */
    private static JFrame myFrame;
    /**
     * The instance of the Controller of the game to prevent duplicates
     */
    private static final DungeonController MY_INSTANCE;
    /**
     * Toolkit of the program to obtain external information
     */
    private static final Toolkit KIT = Toolkit.getDefaultToolkit();
    /**
     * What is the screen size of the screen of the user?
     */
    private static final Dimension SCREEN_SIZE = KIT.getScreenSize();
    /**
     * The Dungeon instance being used for the game
     */
    private DungeonLogic myDungeon;
    /**
     * The playable character for the game
     */
    private Hero myHero;
    /**
     * The database of the game
     */
    public static SQLiteDataSource DATA_SOURCE = new SQLiteDataSource();
    /**
     * The connection of the database
     */
    public static Connection CONNECTION;
    /**
     * The statement to send queries to the database
     */
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

    /**
     * Constructor of the Controller
     * Initializes the dungeon and hero
     */
    public DungeonController() {
        myDungeon = model.DungeonLogic.getDungeonInstance();
        myHero = myDungeon.getHero();
    }

    /**
     *  Start the game and tell view to show its contents
     * @param theArgs the arguments used for file redirection
     */

    public static void main(final String[] theArgs) {
        EventQueue.invokeLater(DungeonController::createAndShowGUI);
    }

    /**
     * The GUI for the game. initializes the frame and sets its properties
     * Starts up View
     */
    private static void createAndShowGUI() {

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
        if (MY_INSTANCE.checkGameStatus()) {
            // Disables "window exit" when clicking the X on the window
            myFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

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
                    final String[] exitOptions = {"Yes", "No", "Cancel"};
                    final int promptResult = JOptionPane.showOptionDialog(null,
                        "Would you like to save progress before you exit?", "Save on Exit",
                        JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE,
                        null, exitOptions, exitOptions[0]);
                    if (promptResult == 0) {
                        try {
                            MY_INSTANCE.myDungeon.save();
                            System.exit(0);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    } else if (promptResult == 1) {
                        System.exit(0);
                    }
                }
            });
        }

        // Sets the size of the window
        myFrame.setSize(frameSize);

        // Sets the location of the window
        myFrame.setLocation(SCREEN_SIZE.width / 2 - myFrame.getWidth() / 2,
            SCREEN_SIZE.height / 2 - myFrame.getHeight() / 2);

        // Makes the main window visible
        myFrame.setVisible(true);
    }

    /**
     * The combat gameplay of the game.
     * Simulates 1 round of the fight
     * @param theChoice What the hero does for the current round of the fight
     */
    public void fight (final int theChoice) {
        if (checkGameStatus()) {
            Monster enemy = myDungeon.getEnemy();
            if (myHero.getHP() > 0 && enemy.getHP() > 0) {
                String mResult = enemy.attack(myHero);
                myDungeon.sendMessage("The " + enemy.getName() + " " + mResult + "\n");
                if (myHero.getHP() > 0) {
                    if (theChoice == 1) {
                        String hResult = myHero.attack(enemy);
                        myDungeon.sendMessage("You " + hResult + "\n");
                    } else if (theChoice == 2) {
                        String hResult = myHero.skill(enemy);
                        myDungeon.sendMessage(hResult + "\n");
                    } else if (theChoice == 3) {
                        boolean success = myDungeon.useItem(new HealthPotion());
                        if (success) {
                            myDungeon.sendMessage("You healed for up to " + myHero.getMaxHP() / 2 + "HP! \n");
                            myDungeon.sendMessage("The " + enemy.getName() + " " + mResult + "\n");
                        }
                    } else if (theChoice == 4) {
                        myDungeon.flee();
                        myDungeon.sendMessage("You fled the encounter!\n");
                    }
                }
            }
            if (myHero.getHP() <= 0) {
                endGame(false);
            } else if (enemy.getHP() <= 0) {
                myDungeon.endCombat();
                if (!enemy.getName().equals("Hydra")) {
                    myDungeon.collect();
                    myDungeon.getCurrentRoom().getCharacters().remove(myDungeon.getEnemy());
                } else {
                    endGame(true);
                }
            } else {
                String msg = enemy.skill(enemy);
                if (!msg.isEmpty()) {
                    myDungeon.sendMessage(msg + "\n");
                }
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
        myDungeon.setGameActive(false);
        if (theState) { //beat the game
            JOptionPane.showMessageDialog(null, "Congratulations! You beat the game!");
            //show the stats window
            //show credits page
        } else {
            JOptionPane.showMessageDialog(null, "You have unfortunately met your end.");
        }
    }

    /**
     * Getter for status of the game
     * @return Is there currently an active game?
     */
    private boolean checkGameStatus() {
        if (!myDungeon.getGameActive()) {
            JOptionPane.showMessageDialog(null, "You haven't started a new save yet!");
        }
        return myDungeon.getGameActive();
    }

    /**
     * Getter for myFrame
     * @return the Frame
     */
    public static JFrame getFrame() {
        return myFrame;
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
                myDungeon.nextFloor();
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
        } else if ("SEE".equals(s)) {
            boolean success = myDungeon.useItem(new VisionPotion());
            if (success) {
                myDungeon.sendMessage("You can see what's inside the adjacent rooms.\n");
            }
        } else if ("LOAD GAME".equals(s)) {
            try {
                myDungeon.load((File)(theEvent.getNewValue()));
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            myDungeon = model.DungeonLogic.getDungeonInstance();
            myHero = myDungeon.getHero();
        }
    }
}
