package controller;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import enums.Direction;
import model.*;

import view.DungeonView;

public class DungeonController extends JPanel {
    public static JFrame myFrame;
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

    public static void main(final String[] theArgs){
        EventQueue.invokeLater(() -> createAndShowGUI());
    }

    public static void createAndShowGUI() {
        //main frame
        myFrame = new JFrame("Dungeon Adventure");
        //main panel
        final DungeonView mainPanel = new DungeonView();
        //size of the main window
        final Dimension frameSize = new Dimension(960, 540);
        //adds property change listeners to the main panel
        DungeonLogic.getDungeonInstance().addPropertyChangeListener(mainPanel);
        //disables "window exit" when clicking the X on the window
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Window Listener
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

        //sets the content pane of the frame
        myFrame.setContentPane(mainPanel);
        //sets the size of the window
        myFrame.setSize(frameSize);
        //sets the location of the window
        myFrame.setLocation(SCREEN_SIZE.width / 2 - myFrame.getWidth() / 2,
            SCREEN_SIZE.height / 2 - myFrame.getHeight() / 2);
        //makes the main window visible
        myFrame.setVisible(true);
    }

    /**
     * Helper method for the Use Hit Point Potion Buttons.
     */
    private void drinkPotion() {
        myHero.healOrDamage(myHero.getMaxHP()/2);
        myInventory.useItem(new HealthPotion());
    }

    public void fight (final Monster theMonster){
        if (checkGameStatus()) {
            while (myHero.getHP() > 0 && theMonster.getHP() > 0) {
//            prompt user for what action they want to do for their turn
//            listen to key press or button click
//            if attack
                myHero.attack(theMonster);
//            if skill
                myHero.skill(theMonster);
//            if drink potion
                drinkPotion();

                if (theMonster.getHP() > 0) {
                    theMonster.attack(myDungeon.getHero());
                    theMonster.skill(theMonster);
                }
            }
            if(myHero.getHP() <= 0){
                endGame(false);
            } else {
                //won the fight
                //get drop item of monster if available
                //make the room empty and traversable
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
    public void endGame(final boolean theState){
        if (theState) { //beat the game
            JOptionPane.showMessageDialog(null, "Congratulations! You beat the game!");
            //show the stats window
            //show credits page
        } else{
            JOptionPane.showMessageDialog(null, "You have unfortunately met your end.");
            //start new game prompt
        }
    }
    private boolean checkGameStatus(){
        if (myDungeon.getGameActive()) {
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "You haven't started a new save yet!");
            return false;
        }
    }

}
