package view;

import controller.DungeonController;
import model.DungeonLogic;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public final class DungeonView extends JPanel implements PropertyChangeListener {
    /**
     * A constant string that acts as a line separator for Strings that want a new line
     */
    private final static String NEWLINE = System.lineSeparator();
    /** Font for buttons on the main frame */
    private final static Font BUTTON_FONT = new Font("Arial", Font.PLAIN, 20);
    /** Font for labels on the main frame */
    private static final Font FONT = new Font("Arial", Font.BOLD, 20);
    /**
     * Size of the portrait images of the Hero and interactables in the room
     */
    public static final int PORTRAIT_SIZE = 90;

    /**
     * Property change support for the Class
     */
    private final PropertyChangeSupport myChanges;

    /**
     * Menu bar of the program
     */
    private JMenuBar myMenu;
    /**
     * File menu tab of the menu bar
     */
    private JMenu myFile;
    /**
     * Help menu tab for the menu bar
     */
    private JMenu myHelp;
    /**
     * Menu button to start a new game
     */
    private JMenuItem myStartGame;
    /**
     * Menu button to save the current game
     */
    private JMenuItem mySaveGame;
    /**
     * Menu button to load a save file
     */
    private JMenuItem myLoadGame;
    /**
     * Menu button to exit the program
     */
    private JMenuItem myExitGame;
    /**
     * Menu button to open the window that shows the backend info of the program
     */
    private JMenuItem myAbout;
    /**
     * Menu button to open the window that shows the rules of the game
     */
    private JMenuItem myRules;
    /**
     * Menu button to open the window that shows the stats of the current game
     */
    private JMenuItem myStats;
    /**
     * Menu button to open the window that displays the hotkeys of the game
     */
    private JMenuItem myShortcuts;
    /**
     * The default color of the background of JItems
     */
    private final Color myBackgroundColor;
    /**
     * The default border settings of and JItem
     */
    private final Border myBorder;
    /**
     * Button to go north
     */
    private JButton myNorth;
    /**
     * Button to go west
     */
    private JButton myWest;
    /**
     * Button to go South
     */
    private JButton mySouth;
    /**
     * Button to go east
     */
    private JButton myEast;
    /**
     * Button to start a game if no games are active
     */
    private JButton myStart;
    /**
     * Button to save the current active game
     */
    private JButton mySave;
    /**
     * Button to load a save file if at least one exists
     */
    private JButton myLoad;
    /**
     * The image of the Hero
     */
    private JLabel myHero;
    /**
     * What is inside the current room with the Hero
     */
    private JLabel myRoomContents;
    /**
     * Label to tell the user the panel this is in is the inventory
     */
    private JLabel myInventoryLabel;
    /**
     * The current HP of the hero
     */
    private JLabel myHeroHP;
    /**
     * The max HP of the hero
     */
    private JLabel myHeroMaxHP;
    /**
     * The current HP of the enemy
     */
    private JLabel myOppHP;
    /**
     * The max HP of the enemy
     */
    private JLabel myOppMaxHP;
    /**
     * Button to consume an HP potion
     */
    private JButton myHPPotion;
    /**
     * Button to consume a Vision Potion
     */
    private JButton myVisionPotion;
    /**
     * Button to attack the current enemy
     */
    private JButton myAttack;
    /**
     * Button to use the Hero's skill
     */
    private JButton mySkill;
    /**
     * Button to flee from the current fight
     */
    private JButton myFlee;
    /**
     * The name chosen for the Hero
     */
    private JLabel myName;
    /**
     * The type of enemy the hero is facing
     */
    private JLabel myOppName;
    /**
     * How many HP potions does the hero currently have?
     */
    private JLabel myHPPotionAmount;
    /**
     * How many Vision potions does the hero currently have?
     */
    private JLabel myVisionPotionAmount;
    /**
     * The main panel of the program
     */
    private final JPanel myPanel;
    /**
     * The panel that contains game options
     */
    private JPanel myGamePanel;
    /**
     * The panel that contains the movement keys
     */
    private JPanel myMovePanel;
    /**
     * The panel that contains the Inventory
     */
    private JPanel myInventoryPanel;
    /**
     * The panel that contains the visualization of the current room
     */
    private JPanel myRoomPanel;
    /**
     * The panel that contains combat actions
     */
    private JPanel myFightPanel;
    /**
     * The panel that contains The info of the enemy
     */
    private JPanel myOppHpPanel;
    /**
     * The map of the game
     */
    private final JTextArea myMap;
    /**
     * The event window
     */
    private JTextArea myMessages;

    /**
     * The instance of the dungeon
     */
    DungeonLogic myDungeon;

    /**
     * Constructor that sets up the contents of the pane
     */
    public DungeonView() {
        myChanges = new PropertyChangeSupport(this);
        myDungeon = model.DungeonLogic.getDungeonInstance();

        myBackgroundColor = Color.LIGHT_GRAY.darker();
        myBorder = BorderFactory.createLineBorder(Color.DARK_GRAY,2);

        myMap = new JTextArea(myDungeon.getFloorString());
        myMap.setFont(new Font("Consolas", Font.BOLD, 32));
        myMap.setBackground(getBackground());

        myMessages = new JTextArea("Welcome to Dungeon Adventure!" + NEWLINE);
        myMessages.setPreferredSize(new Dimension(480,210));
        myMessages.setEditable(false);

        setMenuBar();
        DungeonController.getFrame().setJMenuBar(myMenu);

        myPanel = new JPanel();
        myPanel.setBackground(myBackgroundColor);

        setGamePanel();
        setRoomPanel();
        setFightPanel();
        setMovementPanel();
        setInventoryPanel();

        JPanel mainPanel = setMainPanel();
        JPanel actionPanel = setActionPanel(setFightMovePanel());

        myPanel.add(myMap, BorderLayout.LINE_START);
        myPanel.add(mainPanel, BorderLayout.CENTER);
        myPanel.add(actionPanel, BorderLayout.LINE_END);

        add(myPanel);
        myMap.setCursor(null);
        myMap.setFocusable(false);
        myMap.setEditable(false);

        addListeners();

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

    /**
     * Helper method to set the right panels of the game
     * @param theFightMovePanel The fight and move panel
     * @return the Action panel
     */
    private JPanel setActionPanel(final JPanel theFightMovePanel) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3,1, 0, 10));
        panel.add(myGamePanel);
        panel.add(theFightMovePanel);
        panel.add(myInventoryPanel);
        panel.setBackground(myBackgroundColor);
        return panel;
    }

    /**
     * Helper method to set the fight and move panels into its own panel for orienting purposes
     * @return the fight and movel panel combined
     */
    private JPanel setFightMovePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1,2));
        panel.add(myFightPanel);
        panel.add(myMovePanel);
        return panel;
    }

    /**
     * Helper method to set the Room panel and message panel into a single panel
     * for orienting purposes
     * @return the Room panel and message text area combined
     */
    private JPanel setMainPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.add(myRoomPanel);
        panel.add(myMessages);
        return panel;
    }

    /**
     * Helper method to set the game panel
     */
    private void setGamePanel() {
        myGamePanel = new JPanel();
        myGamePanel.setLayout(new GridLayout(3,1));
        myGamePanel.setBackground(myBackgroundColor);

        myStart = setButton("Start Game", BUTTON_FONT, myBackgroundColor.brighter());
        mySave = setButton("Save Game", BUTTON_FONT, myBackgroundColor.brighter());
        myLoad = setButton("Load Game", BUTTON_FONT, myBackgroundColor.brighter());
        mySave.setEnabled(false);
        checkSaveFile();

        myGamePanel.add(myStart);
        myGamePanel.add(mySave);
        myGamePanel.add(myLoad);
        myGamePanel.setPreferredSize(new Dimension(300,150));

        myGamePanel.setVisible(true);
    }

    /**
     * Helper method to activate load options if a save file exists in the save file directory
     */
    private void checkSaveFile() {
        //if save file does not exist
        myLoad.setEnabled(false);
        myLoadGame.setEnabled(false);
    }

    /**
     * Sets a button given its properties
     * @param theLabel The label of the button
     * @param theFont   The font of the button
     * @param theBG The background color of the button
     * @return  The button
     */
    private JButton setButton(final String theLabel, final Font theFont, final Color theBG) {
        JButton button = new JButton(theLabel);
        button.setFont(theFont);
        button.setBackground(theBG);
        return button;
    }

    /**
     * Sets a label given its properties
     * @param theLabel  The text of the label
     * @param theBG The background color of the label
     * @param theOpaque Is the label Opaque?
     * @return  The label
     */
    private JLabel setLabel(final String theLabel, final Color theBG, final boolean theOpaque) {
        JLabel label = new JLabel(theLabel);
        label.setFont(FONT);
        label.setBackground(theBG);
        label.setOpaque(theOpaque);
        return label;
    }

    /**
     * helper method to set the room panel
     */
    private void setRoomPanel() {
        myRoomPanel = new JPanel();
        myRoomPanel.setLayout( new GridLayout(3,3));
        myRoomPanel.setPreferredSize(new Dimension(480,300));
        myRoomPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
        myRoomPanel.setBackground(Color.LIGHT_GRAY);

        myName = new JLabel();
        myName.setFont(BUTTON_FONT);

        myHero = new JLabel("Hero Portrait");
        myHero.setBorder( BorderFactory.createLineBorder(Color.BLACK,2));
        myOppName = new JLabel();
        myOppName.setFont(BUTTON_FONT);
        myRoomContents = new JLabel("Monster/Item/Pit.png");
        myRoomContents.setBorder( BorderFactory.createLineBorder(Color.BLACK,2));

        JPanel hpPanel = setHPIndicator(myDungeon.getHero(), true);
        myOppHpPanel = setHPIndicator(getEnemy(), false);
        myOppHpPanel.setVisible(false);
        myOppName.setVisible(false);

        JPanel namePanel = centeredPanel(myName);
        JPanel oppNamePanel = centeredPanel(myOppName);
        JPanel heroPanel = centeredPanel(myHero);
        JPanel rCPanel = centeredPanel(myRoomContents);

        myRoomPanel.add(namePanel, 0);
        myRoomPanel.add(new JLabel());
        myRoomPanel.add(oppNamePanel, 2);

        myRoomPanel.add(heroPanel, 3);
        myRoomPanel.add(new JLabel());
        myRoomPanel.add(rCPanel, 5);

        myRoomPanel.add(hpPanel,6);
        myRoomPanel.add(new JLabel());
        myRoomPanel.add(myOppHpPanel,8);

        myRoomPanel.setVisible(true);
    }

    /**
     * Helper method to center a label in its own panel
     * @param theLabel  The label you want cetered
     * @return The panel that contains the centered label
     */
    private JPanel centeredPanel(final JLabel theLabel) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.add(theLabel);
        panel.setOpaque(false);
        return panel;
    }

    /**
     * Helper method to set the format of health information on the panel
     * @param theChar   The character whose information you want printed
     * @param theHero   is the character the hero?
     * @return  The panel that contains the HP information of the character
     *          in the format "current health / max health"
     */
    private JPanel setHPIndicator(final model.AbstractDungeonCharacter theChar, final boolean theHero) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.LINE_AXIS));

        String hp = "0";
        String maxHP = "0";
        if (theChar != null) {
            hp = String.valueOf(theChar.getHP());
            maxHP = String.valueOf(theChar.getMaxHP());
        }

        JLabel hpLabel = new JLabel("HP: ");
        if (theHero) {
            myHeroHP = new JLabel(hp);
        } else {
            myOppHP = new JLabel(hp);
        }
        JLabel hpDivider = new JLabel("/");
        if (theHero) {
            myHeroMaxHP = new JLabel(maxHP);
        } else {
            myOppMaxHP = new JLabel(maxHP);
        }

        panel.add(hpLabel);
        if (theHero) {
            panel.add(myHeroHP);
        } else {
            panel.add(myOppHP);
        }
        panel.add(hpDivider);
        if (theHero) {
            panel.add(myHeroMaxHP);
        } else {
            panel.add(myOppMaxHP);
        }
        panel.setOpaque(false);

        return panel;
    }

    /**
     * Getter for myEnemy
     * @return  Who is the current enemy?
     */
    private model.Monster getEnemy() {
        model.AbstractDungeonCharacter monster = null;
        for (model.AbstractDungeonCharacter character: myDungeon.getCurrentRoom().getCharacters()) {
            if(character instanceof model.Monster) {
                monster = character;
            }
        }
        return (model.Monster) monster;
    }

    /**
     * Helper method to set the movement panel
     */
    private void setMovementPanel(){
        myMovePanel = new JPanel();
        myMovePanel.setBackground(myBackgroundColor);

        myNorth = setButton("^", FONT, myBackgroundColor.brighter());
        myEast = setButton(">", FONT, myBackgroundColor.brighter());
        mySouth = setButton("v", FONT, myBackgroundColor.brighter());
        myWest = setButton("<", FONT, myBackgroundColor.brighter());
        myNorth.setEnabled(false);
        myEast.setEnabled(false);
        mySouth.setEnabled(false);
        myWest.setEnabled(false);

        myMovePanel.setLayout(new GridLayout(3,3));

        myMovePanel.add(new JLabel());
        myMovePanel.add(myNorth);
        myMovePanel.add(new JLabel());

        myMovePanel.add(myWest, 3);
        myMovePanel.add(new JLabel());
        myMovePanel.add(myEast, 5);

        myMovePanel.add(new JLabel());
        myMovePanel.add(mySouth, 7);
        myMovePanel.add(new JLabel());

        myMovePanel.setPreferredSize(new Dimension(150,150));

        KeyStroke northKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_W, 0);
        KeyStroke eastKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_D, 0);
        KeyStroke southKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_S, 0);
        KeyStroke westKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_A, 0);


        myMovePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(northKeyStroke, "^");
        myMovePanel.getActionMap().put("^", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                myNorth.doClick();
            }
        });

        myMovePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(eastKeyStroke, ">");
        myMovePanel.getActionMap().put(">", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                myEast.doClick();
            }
        });

        myMovePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(southKeyStroke, "v");
        myMovePanel.getActionMap().put("v", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                mySouth.doClick();
            }
        });

        myMovePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(westKeyStroke, "<");
        myMovePanel.getActionMap().put("<", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                myWest.doClick();
            }
        });

        myMovePanel.setVisible(true);
    }

    /**
     * Helper method toinitialize and obtain information for the JItems relating to the potions
     */
    private void setPotions() {
        myHPPotion = setButton("Health Potion", BUTTON_FONT, myBackgroundColor);
        myHPPotion.setBorder(myBorder);
        myHPPotionAmount = setLabel(String.valueOf(myDungeon.getInventory().getHPPotionAmount()),
            myBackgroundColor, false);

        myVisionPotion = setButton("Vision Potion", BUTTON_FONT, myBackgroundColor);
        myVisionPotion.setBorder(myBorder);
        myVisionPotionAmount = setLabel(String.valueOf(myDungeon.getInventory().getVisionPotionAmount()),
            myBackgroundColor, false);
    }

    /**
     * Helper method to set the Inventory panel
     */
    private void setInventoryPanel(){
        myInventoryPanel = new JPanel();
        myInventoryPanel.setBackground(myBackgroundColor);

        myInventoryLabel = setLabel("Inventory", myBackgroundColor, true);
        setPotions();

        JPanel hpInv = setItemPanel(myHPPotion, myHPPotionAmount);
        JPanel vision = setItemPanel(myVisionPotion, myVisionPotionAmount);

        myInventoryPanel.setLayout(new GridLayout(3,1));

        myInventoryPanel.add(myInventoryLabel);
        myInventoryPanel.add(hpInv);
        myInventoryPanel.add(vision);

        KeyStroke HPKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_6, 0);
        KeyStroke VisionKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_7, 0);

        myInventoryPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(HPKeyStroke, "6");
        myInventoryPanel.getActionMap().put("6", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                myHPPotion.doClick();
            }
        });

        myInventoryPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(VisionKeyStroke, "7");
        myInventoryPanel.getActionMap().put("7", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                myVisionPotion.doClick();
            }
        });

        myInventoryPanel.setPreferredSize(new Dimension(300,150));
        myInventoryPanel.setVisible(true);
    }

    /**
     * Helper method to set the panels for the consumable
     * @param theButton The button for consumable consumption
     * @param theLabel  The amount of consumables currently available
     * @return  The panel that contains the information in the format "Button, amount"
     */
    private JPanel setItemPanel(final JButton theButton, final JLabel theLabel){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
        panel.setBackground(myBackgroundColor);
        panel.add(theButton);
        panel.add(theLabel);
        theButton.setEnabled(false);
        return panel;
    }

    /**
     * Helper method to set the menu bar for the program
     */
    private void setMenuBar() {
        myMenu = new JMenuBar();

        myFile = new JMenu("File");
        myFile.setMnemonic('F');
        myHelp = new JMenu("Help");
        myHelp.setMnemonic('h');

        myStartGame = new JMenuItem("Start New Game", KeyEvent.VK_G);
        myStartGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.CTRL_MASK));
        mySaveGame = new JMenuItem("Save Game", KeyEvent.VK_S);
        mySaveGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
        mySaveGame.setEnabled(false);
        myLoadGame = new JMenuItem("Load Game", KeyEvent.VK_L);
        myLoadGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_MASK));
        myExitGame = new JMenuItem("Exit Game", KeyEvent.VK_X);
        myExitGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));

        myAbout = new JMenuItem("Backstory", KeyEvent.VK_B);
        myAbout.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, InputEvent.CTRL_MASK));
        myRules = new JMenuItem("How to play", KeyEvent.VK_H);
        myRules.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, InputEvent.CTRL_MASK));
        myStats = new JMenuItem("Stats", KeyEvent.VK_T);
        myStats.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.CTRL_MASK));
        myShortcuts = new JMenuItem("Shortcuts", KeyEvent.VK_C);
        myShortcuts.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));

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

    /**
     * Helper method to set the fight panel
     */
    private void setFightPanel(){
        myFightPanel = new JPanel();
        myFightPanel.setBackground(myBackgroundColor);
        myFightPanel.setLayout(new GridLayout(3,1,0,5));

        myAttack = setButton("Attack", BUTTON_FONT, myBackgroundColor);
        myAttack.setBorder(myBorder);
        mySkill = setButton("Use Skill", BUTTON_FONT, myBackgroundColor);
        mySkill.setBorder(myBorder);
        myFlee = new JButton("FLEE!");
        myFlee.setFont(BUTTON_FONT);

        myFightPanel.add(myAttack);
        myFightPanel.add(mySkill);
        myFightPanel.add(myFlee);
        myFightPanel.setPreferredSize(new Dimension(150,150));
        myAttack.setEnabled(false);
        mySkill.setEnabled(false);
        myFlee.setEnabled(false);
    }

    /**
     * Helper method that attaches listeners to everything.
     */
    private void addListeners() {
        myNorth.addActionListener(theEvent -> {
            myDungeon.moveUp();
            myMap.setText(myDungeon.getFloorString());
        });
        myEast.addActionListener(theEvent -> {
            myDungeon.moveRight();
            myMap.setText(myDungeon.getFloorString());
        });
        mySouth.addActionListener(theEvent -> {
            myDungeon.moveDown();
            myMap.setText(myDungeon.getFloorString());
        });
        myWest.addActionListener(theEvent -> {
            myDungeon.moveLeft();
            myMap.setText(myDungeon.getFloorString());
        });

        myStart.addActionListener(theEvent -> {
            try {
                startConfirm();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        myStartGame.addActionListener(theEvent -> {
            try {
                startConfirm();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        mySaveGame.addActionListener((theEvent -> {
            try {
                myDungeon.save();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));
        mySave.addActionListener((theEvent -> {
            try {
                myDungeon.save();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));
        myLoadGame.addActionListener((theEvent -> loadLogic()));
        myLoad.addActionListener((theEvent -> loadLogic()));


        //listener for exit game button
        myExitGame.addActionListener((theEvent -> {
            String[] exitOptions = {"Yes","No", "Cancel"};
            int PromptResult = JOptionPane.showOptionDialog(null,
                "Do you want to Save before exiting?","Save Confirmation",
                JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,
                null,exitOptions,exitOptions[1]);
            if(PromptResult==JOptionPane.YES_OPTION)
            {
                try {
                    myDungeon.save();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                System.exit(0);
            }
        }));

        //listener for about button
        myAbout.addActionListener(theEvent -> JOptionPane.showMessageDialog(null,"story stuff" + NEWLINE + "Developers: " +
            NEWLINE + "Gabriel, Jordan, Max, Terence", "About", JOptionPane.PLAIN_MESSAGE));

        //listener for rules button
        myRules.addActionListener(theEvent -> JOptionPane.showMessageDialog(null,"Try to complete the dungeon"
                + NEWLINE + "Every room except the starting room contains an event." + NEWLINE +
                "The possible events are a monster fight, trap trigger, an item to collect, or a boss monster." + NEWLINE +
                "Boss monsters defend the pillar needed to get to the next level." + NEWLINE +
                "On level 5, the boss monster is defending the exit to the dungeon."
            , "Rules", JOptionPane.PLAIN_MESSAGE));

        //listener for statistics button
        myStats.addActionListener(theEvent -> JOptionPane.showMessageDialog(null,"Hero stats: "
            + NEWLINE + myDungeon.getHero().toString() + "Number of movement: " + myDungeon.getSteps()
            + NEWLINE + "Current level: " + myDungeon.getFloorLevel(), "Statistics", JOptionPane.PLAIN_MESSAGE));

        //listeners for attack actions
        myAttack.addActionListener(theEvent -> {
            if (myAttack.isEnabled()) {
                myChanges.firePropertyChange("Action", 0, 1);
                myHeroHP.setText(String.valueOf(myDungeon.getHero().getHP()));
                myOppHP.setText(String.valueOf(myDungeon.getEnemy().getHP()));
            }
        });
        mySkill.addActionListener(theEvent -> {
            if (mySkill.isEnabled()) {
                myChanges.firePropertyChange("Action", 0, 2);
                myHeroHP.setText(String.valueOf(myDungeon.getHero().getHP()));
                myOppHP.setText(String.valueOf(myDungeon.getEnemy().getHP()));
            }
        });
        myHPPotion.addActionListener(theEvent -> {
            myChanges.firePropertyChange("Action", 0, 3);
            myHeroHP.setText(String.valueOf(myDungeon.getHero().getHP()));
            myOppHP.setText(String.valueOf(myDungeon.getEnemy().getHP()));
        });
        myFlee.addActionListener(theEvent -> {
            if (myFlee.isEnabled()) {
                myChanges.firePropertyChange("Action", 0, 4);
                myHeroHP.setText(String.valueOf(myDungeon.getHero().getHP()));
            }
        });

        myVisionPotion.addActionListener(theEvent -> {
            myChanges.firePropertyChange("SEE", false, true);
            myMap.setText(myDungeon.getFloorString());
        });
    }

    /**
     * Sequence and logic for when load button is clicked or pressed
     */
    private void loadLogic() {
        String promptResult = JOptionPane.showInputDialog(null,
                "What is the save file's name?");
        File saveGame;
        while (true) {
            saveGame = new File("saves\\" + promptResult + ".adv");
            if (saveGame.isFile() || promptResult == null) {
                break;
            }
            promptResult = JOptionPane.showInputDialog(null,
                    "This save does not exist! What is the save file's name?");
        }
        if (saveGame.isFile()) {
            myChanges.firePropertyChange("LOAD GAME", null, saveGame);
            myDungeon = DungeonLogic.getDungeonInstance();
            myDungeon.updateView();
            myMap.setText(myDungeon.getFloorString());
            System.out.println(myMap.getText());
            myName.setText(myDungeon.getHero().getCharName());
            myHeroHP.setText(String.valueOf(myDungeon.getHero().getHP()));
            myHeroMaxHP.setText(String.valueOf(myDungeon.getHero().getMaxHP()));
            try {
                BufferedImage hero = ImageIO.read(new File(myDungeon.getHero().getImage()));
                Image temp = hero.getScaledInstance(PORTRAIT_SIZE, PORTRAIT_SIZE, Image.SCALE_SMOOTH);
                updateRoomContents(myHero, new JLabel(new ImageIcon(temp)), 3);
            } catch (IOException exception) {
                updateRoomContents(myHero, new JLabel("HERO IMAGE NOT FOUND"), 3);
            }
            updateRoomContents(myRoomContents, new JLabel("Empty!"), 5);
            SwingUtilities.invokeLater(myPanel::repaint);
        }
    }

    /**
     * Helper method that creates an option pane to
     * confirm if the player wants to start a new game.
     */
    private void startConfirm() throws SQLException {
        String[] exitOptions = {"Yes","No"};
        int PromptResult = JOptionPane.showOptionDialog(null,
            "Start new game?","Start Game",
            JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,
            null,exitOptions,exitOptions[1]);
        if(PromptResult==JOptionPane.YES_OPTION)
        {
            myStart.setEnabled(false);
            characterCreation();
        }
    }
    /**
     * Helper method that opens up the window to choose your class
     */
    private void characterCreation() throws SQLException {
        JFrame creationWindow = new JFrame();
        String name = JOptionPane.showInputDialog(creationWindow, "Name:");
        if (name == null) {
            myStart.setEnabled(true);
            return;
        } else if (name.equals("")) {
            invalidNamePane();
            return;
        }
        String[] classes = {"Warrior", "Rogue", "Mage"};
        JFrame createWindow = new JFrame();
        int classChoice = JOptionPane.showOptionDialog(createWindow, "Choose a class",
            "Character Creation",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE,null, classes, classes[0]);
        createWindow.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent theCloseEvent) {
                myDungeon.setGameActive(false);
            }
        });
        try {
            myDungeon.setGameActive(true);
            myDungeon.createCharacter(name, classChoice);
            myName.setText(myDungeon.getHero().getCharName());
            myHeroHP.setText(String.valueOf(myDungeon.getHero().getHP()));
            myHeroMaxHP.setText(String.valueOf(myDungeon.getHero().getMaxHP()));
            try {
                BufferedImage hero = ImageIO.read(new File(myDungeon.getHero().getImage()));
                Image temp = hero.getScaledInstance(PORTRAIT_SIZE, PORTRAIT_SIZE, Image.SCALE_SMOOTH);
                updateRoomContents(myHero, new JLabel(new ImageIcon(temp)), 3);
            } catch (IOException exception) {
                updateRoomContents(myHero, new JLabel("HERO IMAGE NOT FOUND"), 3);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (NullPointerException npe) {
            myDungeon.setGameActive(false);
        }
        myDungeon.startGame();
        myDungeon.updateView();
    }

    /**
     * Window for if the name entered for the hero is invalid (left blank)
     * @throws SQLException The data for the hero could not be queried
     */
    private void invalidNamePane() throws SQLException {
        JFrame invalidBalance = new JFrame();
        JOptionPane.showMessageDialog(invalidBalance, "Invalid Name", "Error", JOptionPane.WARNING_MESSAGE);
        characterCreation();
    }

    /**
     * Helper method to enable the buttons that move the hero to be able to be pressed
     */
    private void enableMovement() {
        model.Room room = myDungeon.getCurrentRoom();
        myNorth.setEnabled(room.canWalkNorth());
        myEast.setEnabled(room.canWalkEast());
        mySouth.setEnabled(room.canWalkSouth());
        myWest.setEnabled(room.canWalkWest());
    }

    /**
     * Helper method to disable movement for the hero
     */
    private void disableMovement() {
        myNorth.setEnabled(false);
        myEast.setEnabled(false);
        mySouth.setEnabled(false);
        myWest.setEnabled(false);
    }
    /**
     * Property Change Listeners for all the property change fires.
     *
     * @param theEvent A PropertyChangeEvent object describing the event source
     *          and the property that has changed.
     */
    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        final String s = theEvent.getPropertyName();
        if ("MESSAGE".equals(s)) {
            String t = theEvent.getNewValue().toString();
            myMessages.setText(t);
        } else if ("COMBAT STATUS".equals(s)) {
            boolean inCombat = (boolean) theEvent.getNewValue();
            Color color = Color.RED;
            if (inCombat) {
                myOppName.setText(getEnemy().getName());
                myOppHP.setText(String.valueOf(getEnemy().getHP()));
                myOppMaxHP.setText(String.valueOf(getEnemy().getMaxHP()));
                disableMovement();
            } else {
                color = Color.LIGHT_GRAY.darker();
                enableMovement();
                updateRoomContents(myRoomContents, new JLabel("Empty!"), 5);
            }
            myAttack.setEnabled(inCombat);
            mySkill.setEnabled(inCombat);
            myFlee.setEnabled(inCombat);
            mySave.setEnabled(!inCombat);
            mySaveGame.setEnabled(!inCombat);
            if (!myVisionPotionAmount.getText().equals("0")) {
                myVisionPotion.setEnabled(!inCombat);
            }
            myOppHpPanel.setVisible(inCombat);
            myOppName.setVisible(inCombat);
            myFightPanel.setBackground(color);

        } else if ("Health Potion".equals(s)) {
            myHPPotionAmount.setText(String.valueOf(theEvent.getNewValue()));
            myHPPotion.setEnabled(!theEvent.getNewValue().equals(0));

        } else if ("Vision Potion".equals(s)) {
            myVisionPotionAmount.setText(String.valueOf(theEvent.getNewValue()));
            myMap.setText(myDungeon.getFloorString());
            myVisionPotion.setEnabled(!theEvent.getNewValue().equals(0));

        } else if ("UPDATE MAP".equals(s)) {
            myMap.setText(myDungeon.getFloorString());

        } else if ("GAME STATE".equals(s)) {
            mySave.setEnabled((boolean) theEvent.getNewValue());
            mySaveGame.setEnabled((boolean) theEvent.getNewValue());
            myStart.setEnabled((boolean) theEvent.getOldValue());
            myNorth.setEnabled((boolean) theEvent.getNewValue());
            myEast.setEnabled((boolean) theEvent.getNewValue());
            mySouth.setEnabled((boolean) theEvent.getNewValue());
            myWest.setEnabled((boolean) theEvent.getNewValue());
            myHPPotion.setEnabled((boolean) theEvent.getNewValue());
            myAttack.setEnabled((boolean) theEvent.getNewValue());
            mySkill.setEnabled((boolean) theEvent.getNewValue());
            myFlee.setEnabled((boolean) theEvent.getNewValue());

        } else if ("Can Load".equals(s)) {
            myLoad.setEnabled(true);
            myLoadGame.setEnabled(true);

        } else if ("Dir".equals(s)) {
            if ((boolean) theEvent.getNewValue()) {
                enableMovement();
            } else {
                disableMovement();
            }

        } else if ("HP CHANGE".equals(s)) {
            myHeroHP.setText(theEvent.getNewValue().toString());

        } else if ("LEVEL UP".equals(s)) {
            myHeroMaxHP.setText(theEvent.getNewValue().toString());
            myHeroHP.setText(theEvent.getNewValue().toString());
        } else if ("Hero".equals(s)) {
            myHero.setText(theEvent.getNewValue().toString());
        } else if ("IMAGE".equals(s)) {
            try {
                BufferedImage roomImage = ImageIO.read(new File((String) theEvent.getNewValue()));
                Image temp = roomImage.getScaledInstance(PORTRAIT_SIZE, PORTRAIT_SIZE, Image.SCALE_SMOOTH);
                updateRoomContents(myRoomContents, new JLabel(new ImageIcon(temp)), 5);
            } catch (IOException exception) {
                updateRoomContents(myRoomContents, new JLabel("IMAGE NOT FOUND"), 5);
            }
        } else if ("EMPTY".equals(s)) {
            updateRoomContents(myRoomContents, new JLabel("Empty!"), 5);
        } else if ("RE-ADD LISTENERS".equals(s)) {

        }
    }

    /**
     * Helper method to update images of Room panel
     * @param myRoomContents The old image
     * @param myRoomContents1 The new image
     * @param index The location of the image in relation to the Room panel
     */
    private void updateRoomContents(JLabel myRoomContents, JLabel myRoomContents1, int index) {
        myRoomContents = myRoomContents1;
        JPanel rcPanel = centeredPanel(myRoomContents);
        myRoomPanel.remove(index);
        myRoomPanel.add(rcPanel, index);
    }
}
