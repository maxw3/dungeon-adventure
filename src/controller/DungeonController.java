package controller;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import enums.Direction;
import model.*;

import view.DungeonView;

public class DungeonController extends JPanel implements PropertyChangeListener {

    public static JFrame myFrame;
    private static final DungeonController MY_INSTANCE = new DungeonController();
    private static final Toolkit KIT = Toolkit.getDefaultToolkit();
    private static final Dimension SCREEN_SIZE = KIT.getScreenSize();
    private final DungeonLogic myDungeon;
    private final Hero myHero;
    private final Inventory myInventory;

    public DungeonController() {
        myDungeon = model.DungeonLogic.getDungeonInstance();
        myHero = myDungeon.getHero();
        myInventory = myDungeon.getInventory();
    }

    public static void main(final String[] theArgs) {
        EventQueue.invokeLater(() -> createAndShowGUI());
    }

    public static void createAndShowGUI() {

        // Main Frame/Window
        myFrame = new JFrame("Dungeon Adventure");

        // Main Panel, Contains the Game
        final DungeonView mainPanel = new DungeonView();

        // Size of the Main Window
        final Dimension frameSize = new Dimension(960, 540);

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
                if (promptResult == JOptionPane.YES_OPTION) {
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
    private void drinkPotion() {
        if (myDungeon.getGameActive()) {
            myDungeon.getInventory().useItem(new HealthPotion(1));
        } else {
            JOptionPane.showMessageDialog(null, "You haven't started a new save yet!");
        }
    }

    public void fight (final Monster theMonster) {
        if (checkGameStatus()) {
            while (myHero.getHP() > 0 && theMonster.getHP() > 0) {
//            prompt user for what action they want to do for their turn
//            listen to key press or button click
//            if attack
//                myHero.attack(theMonster);
//            if skill
//                myHero.skill(theMonster);
//            if drink potion
//                drinkPotion();

                // Temporarily makes both sides attack each other
                if (theMonster.getHP() > 0) {
                    theMonster.attack(myDungeon.getHero());
                    myHero.attack(theMonster);
                    theMonster.skill(theMonster);
                }
            }
            if (myHero.getHP() <= 0) {
                endGame(false);
            } else {
                //won the fight
                //get drop item of monster if available
                //make the room empty and traversable
                myDungeon.collect();
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
                        myDungeon.getCurrentRoom().removeCharacter(c);
                        fight((Monster)c);
                        myDungeon.endCombat();
                    }
                }
            }
        } else if ("COMPLETED FLOOR".equals(s)) {
            // Prompt view to offer to save game
            myDungeon.changeFloor();
        }
    }
}
