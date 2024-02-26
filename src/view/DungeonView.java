package view;

import controller.DungeonController;
import model.*;
import enums.Direction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.border.Border;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public final class DungeonView extends JPanel implements PropertyChangeListener{
    private final static String NEWLINE = System.lineSeparator();
    /** Font for buttons on the main frame */
    private final static Font BUTTON_FONT = new Font("Arial", Font.PLAIN, 20);
    /** Font for labels on the main frame */
    private static final Font FONT = new Font("Arial", Font.PLAIN, 15);

    private JMenuBar myMenu;
    private JMenu myFile;
    private JMenu myHelp;
    private JMenuItem myStartGame;
    private JMenuItem mySaveGame;
    private JMenuItem myLoadGame;
    private JMenuItem myExitGame;
    private JMenuItem myAbout;
    private JMenuItem myRules;
    private JMenuItem myStats;
    private JMenuItem myShortcuts;
    private Color myBackgroundColor;
    private JButton myNorth;
    private JButton myWest;
    private JButton mySouth;
    private JButton myEast;
    private JButton myHPPotion;
    private JButton myVisionPotion;
    private JButton myAttack;
    private JButton mySkill;
    private JButton myFlee;
    private JLabel myName;
    private JLabel myHPPotionAmount;
    private JLabel myVisionPotionAmount;
    private JPanel myPanel;
    private JPanel myMovePanel;
    private JPanel myInventoryPanel;
    private JPanel myRoomPanel;
    private JPanel myFightPanel;
    final DungeonLogic myDungeon;

    public DungeonView() {
        myDungeon = model.DungeonLogic.getDungeonInstance();

        myBackgroundColor = Color.gray;

        setMenuBar();
        DungeonController.myFrame.setJMenuBar(myMenu);

        myPanel = new JPanel();
        GridBagLayout theLayout = new GridBagLayout();
        GridBagConstraints theConstraints = new GridBagConstraints();
        myPanel.setLayout(theLayout);

        setFightPanel();

//        myPanel.add(myRoomPanel);
//        myPanel.add(myMovePanel);
        myPanel.add(myFightPanel);
//        myPanel.add(myInventoryPanel);

//        addListeners();

        add(myPanel);

    }
    private void setMenuBar() {
        myMenu = new JMenuBar();

        myFile = new JMenu("File");
        myFile.setMnemonic('F');
        myHelp = new JMenu("Help");
        myHelp.setMnemonic('h');

        myStartGame = new JMenuItem("Start New Game", KeyEvent.VK_G);
        myStartGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.CTRL_MASK));
        mySaveGame = new JMenuItem("Save Game", KeyEvent.VK_S);
        mySaveGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        myLoadGame = new JMenuItem("Load Game", KeyEvent.VK_L);
        myLoadGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
        myExitGame = new JMenuItem("Exit Game", KeyEvent.VK_X);
        myExitGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));

        myAbout = new JMenuItem("Backstory", KeyEvent.VK_B);
        myAbout.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, ActionEvent.CTRL_MASK));
        myRules = new JMenuItem("How to play", KeyEvent.VK_H);
        myRules.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK));
        myStats = new JMenuItem("Stats", KeyEvent.VK_T);
        myStats.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.CTRL_MASK));
        myShortcuts = new JMenuItem("Shortcuts", KeyEvent.VK_C);
        myShortcuts.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));

        myFile.add(myStartGame);
        myFile.add(mySaveGame);
        myFile.add(myLoadGame);
        myFile.add(myExitGame);
        myHelp.add(myAbout);
        myHelp.add(myRules);
        myHelp.add(myStats);
        myHelp.add(myShortcuts);

        myMenu.add(myFile);
        myMenu.add(myHelp);
    }

    private void setFightPanel(){
        Color color = Color.LIGHT_GRAY.darker();
        Border border = BorderFactory.createLineBorder(Color.DARK_GRAY,2);
        myFightPanel = new JPanel();
        myFightPanel.setBackground(color);
        myFightPanel.setLayout(new GridLayout(4,1,0,5));

        myHPPotion = new JButton("Health Potion");
        myHPPotion.setBackground(color);
        myHPPotion.setFont(BUTTON_FONT);
        myHPPotionAmount = new JLabel(
            String.valueOf(myDungeon.getInventory().getHPPotionAmount()));
        myHPPotion.setBackground(color);
        myHPPotionAmount.setOpaque(false);
        myHPPotionAmount.setFont(FONT);
        JPanel hp = new JPanel();
        hp.setLayout(new BoxLayout(hp, BoxLayout.X_AXIS));
        hp.setBorder(border);
        hp.setBackground(color);
        myAttack = new JButton("Attack");
        myAttack.setBackground(color);
        myAttack.setBorder(border);
        myAttack.setFont(BUTTON_FONT);
        mySkill = new JButton("Use Skill");
        mySkill.setBackground(color);
        mySkill.setBorder(border);
        mySkill.setFont(BUTTON_FONT);
        myFlee = new JButton("FLEE!");
        myFlee.setFont(BUTTON_FONT);

        myFightPanel.add(myAttack);
        myFightPanel.add(mySkill);
        hp.add(myHPPotion);
        hp.add(myHPPotionAmount);
        myFightPanel.add(hp);
        myFightPanel.add(myFlee);
    }

    /**
     * Helper method that attaches listeners to everything.
     */
    private void addListeners() {
        myNorth.addActionListener(theEvent -> traverse(Direction.NORTH));
        myEast.addActionListener(theEvent -> traverse(Direction.EAST));
        mySouth.addActionListener(theEvent -> traverse(Direction.SOUTH));
        myWest.addActionListener(theEvent -> traverse(Direction.WEST));

//        myHPPotion.addActionListener((theEvent -> {}));
//        myVisionPotion.addActionListener((theEvent -> {}));
//        mySaveGame.addActionListener((theEvent -> {}));
//        myLoadGame.addActionListener((theEvent -> {}));

        //listener for exit game button
        myExitGame.addActionListener((theEvent -> {
            String exitOptions[] = {"Yes","No", "Cancel"};
            int PromptResult = JOptionPane.showOptionDialog(null,
                "Do you want to Save before exiting?","Save Confirmation",
                JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,
                null,exitOptions,exitOptions[1]);
            if(PromptResult==JOptionPane.YES_OPTION)
            {
//save game
                System.exit(0);
            }
        }));

        //listener for about button
        myAbout.addActionListener(theEvent ->{
            JOptionPane.showMessageDialog(null,"story stuff" + NEWLINE + "Developers: " +
                NEWLINE + "Gabriel, Jordan, Max, Terence", "About",JOptionPane.DEFAULT_OPTION);
        });

        //listener for rules button
        myRules.addActionListener(theEvent ->{
            JOptionPane.showMessageDialog(null,"Try to complete the dungeon"
                    + NEWLINE + "Every room except the starting room contains an event." + NEWLINE +
                    "The possible events are a monster fight, trap trigger, an item to collect, or a boss monster." + NEWLINE +
                    "Boss monsters defend the pillar needed to get to the next level." + NEWLINE +
                    "On level 5, the boss monster is defending the exit to the dungeon."
                , "Rules",JOptionPane.DEFAULT_OPTION);
        });

        //listener for statistics button
        myStats.addActionListener(theEvent ->{
            JOptionPane.showMessageDialog(null,"Hero stats: "
                + NEWLINE + "Number of movement: "
                + NEWLINE + "Current level: ", "Statistics",JOptionPane.DEFAULT_OPTION);
        });
    }

    private void traverse (final Direction theDir){
        //fire propertychange to controller to make the hero move.
    }

    /**
     * Property Change Listeners for all the property change fires.
     *
     * @param theEvent A PropertyChangeEvent object describing the event source
     *          and the property that has changed.
     */
    @Override
    public void propertyChange(PropertyChangeEvent theEvent) {
        //update Inventory UI
        //update map
        //Hero hp falls to 0
        //Monster hp falls to 0
    }
}
