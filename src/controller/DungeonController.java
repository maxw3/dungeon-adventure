package controller;

import model.DungeonLogic;
import model.HealthPotion;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class DungeonController extends JPanel {
    private static final Toolkit KIT = Toolkit.getDefaultToolkit();
    private static final Dimension SCREEN_SIZE = KIT.getScreenSize();

    public static JFrame myFrame;
    final DungeonLogic myDungeon;

    public DungeonController() {
        myDungeon = model.DungeonLogic.getDungeonInstance();
    }

    public static void main(final String[] theArgs){
        EventQueue.invokeLater(() -> createAndShowGUI());
    }

    public static void createAndShowGUI() {
        //main frame
        myFrame = new JFrame("Dungeon Adventure");
        //main panel
        //final DungeonView mainPanel = new DungeonView();
        //size of the main window
        final Dimension frameSize = new Dimension(960,540);
        //adds property change listeners to the main panel
//        DungeonLogic.getDungeonInstance().addPropertyChangeListener(mainPanel);
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
            public void windowClosing(WindowEvent theCloseEvent) {
                String exitOptions[] = {"Yes","No"};
                int PromptResult = JOptionPane.showOptionDialog(null,
                    "Would you like to save progress before you exit?","Save on Exit",
                    JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,
                    null,exitOptions,exitOptions[1]);
                if(PromptResult==JOptionPane.YES_OPTION)
                {
//                    DungeonLogic.save();
                }
                System.exit(0);
            }
        });

        //sets the content pane of the frame
//        myFrame.setContentPane(mainPanel);
        //sets the size of the window
        myFrame.setSize(frameSize);
        //sets the location of the window
        myFrame.setLocation(SCREEN_SIZE.width / 2 - myFrame.getWidth() / 2,
            SCREEN_SIZE.height / 2 - myFrame.getHeight() / 2);
        //makes the main window visible
        myFrame.setVisible(true);
    }

    /**
     * Helper method for the Use Hitpoint Potion Buttons.
     */
    private void drinkPotion() {
        if (myDungeon.getGameActive()) {
            myDungeon.getHero().healOrDamage(myDungeon.getHero().getMaxHP()/2);
            myDungeon.getInventory().useItem(new HealthPotion(1));
        } else {
            JOptionPane.showMessageDialog(null, "You haven't started a new save yet!");
        }
    }

}
