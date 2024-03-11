package view;

import enums.Direction;
import controller.DungeonController;
import model.DungeonLogic;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.border.Border;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public final class DungeonView extends JPanel implements PropertyChangeListener {
    private final static String NEWLINE = System.lineSeparator();
    /** Font for buttons on the main frame */
    private final static Font BUTTON_FONT = new Font("Arial", Font.PLAIN, 20);
    /** Font for labels on the main frame */
    private static final Font FONT = new Font("Arial", Font.BOLD, 20);

    private final PropertyChangeSupport myChanges
            = new PropertyChangeSupport(this);
  
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
    private final Color myBackgroundColor;
    private final Border myBorder;
    private JButton myNorth;
    private JButton myWest;
    private JButton mySouth;
    private JButton myEast;
    private JButton myStart;
    private JButton mySave;
    private JButton myLoad;
    private JLabel myHero;
    private JLabel myRoomContents;
    private JLabel myInventoryLabel;
    private JLabel myHeroHP;
    private JLabel myHeroMaxHP;
    private JLabel myOppHP;
    private JLabel myOppMaxHP;
    private JButton myHPPotion;
    private JButton myVisionPotion;
    private JButton myAttack;
    private JButton mySkill;
    private JButton myFlee;
    private JLabel myName;
    private JLabel myOppName;
    private JLabel myHPPotionAmount;
    private JLabel myVisionPotionAmount;
    private final JPanel myPanel;
    private JPanel myGamePanel;
    private JPanel myMovePanel;
    private JPanel myInventoryPanel;
    private JPanel myRoomPanel;
    private JPanel myFightPanel;
    private JPanel myOppHpPanel;

    private final JTextArea myMap;
    private JTextArea myMessages;

    DungeonLogic myDungeon;

    public DungeonView() {
        myDungeon = model.DungeonLogic.getDungeonInstance();

        myBackgroundColor = Color.LIGHT_GRAY.darker();
        myBorder = BorderFactory.createLineBorder(Color.DARK_GRAY,2);

        myMap = new JTextArea(myDungeon.getFloorString());
        myMap.setFont(new Font("Consolas", Font.BOLD, 32));
        myMap.setBackground(getBackground());

        myMessages = new JTextArea("Welcome to Dungeon Adventure!" + NEWLINE);
        myMessages.setPreferredSize(new Dimension(480,180));
        myMessages.setEditable(false);

        setMenuBar();
        DungeonController.myFrame.setJMenuBar(myMenu);

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

    private JPanel setActionPanel(final JPanel theFightMovePanel) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3,1, 0, 10));
        panel.add(myGamePanel);
        panel.add(theFightMovePanel);
        panel.add(myInventoryPanel);
        panel.setBackground(myBackgroundColor);
        return panel;
    }
    private JPanel setFightMovePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1,2));
        panel.add(myFightPanel);
        panel.add(myMovePanel);
        return panel;
    }
    private JPanel setMainPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.add(myRoomPanel);
        panel.add(myMessages);
        return panel;
    }
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
    private void checkSaveFile() {
        //if save file does not exist
        myLoad.setEnabled(false);
        myLoadGame.setEnabled(false);
    }
    private JButton setButton(final String theLabel, final Font theFont, final Color theBG) {
        JButton button = new JButton(theLabel);
        button.setFont(theFont);
        button.setBackground(theBG);
        return button;
    }
    private JLabel setLabel(final String theLabel, final Color theBG, final boolean theOpaque) {
        JLabel label = new JLabel(theLabel);
        label.setFont(FONT);
        label.setBackground(theBG);
        label.setOpaque(theOpaque);
        return label;
    }
    private void setRoomPanel() {
        myRoomPanel = new JPanel();
        myRoomPanel.setLayout( new GridLayout(3,3));
        myRoomPanel.setPreferredSize(new Dimension(480,300));
        myRoomPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
        myRoomPanel.setBackground(Color.LIGHT_GRAY);

        myName = new JLabel();
        myName.setFont(BUTTON_FONT);
        myHero = new JLabel("Hero.png");
        myHero.setBorder( BorderFactory.createLineBorder(Color.BLACK,2));
        myOppName = new JLabel();
        myOppName.setFont(BUTTON_FONT);
        myRoomContents = new JLabel("Monster/Item/Pit.png");
        myRoomContents.setBorder( BorderFactory.createLineBorder(Color.BLACK,2));

        JPanel hpPanel = setHPIndicator(myDungeon.getHero(), true);
        myOppHpPanel = setHPIndicator(getEnemy(), false);
        myOppHpPanel.setVisible(false);
        myOppName.setVisible(false);

        JPanel namePanel = new JPanel();
        namePanel.setLayout(new GridBagLayout());
        namePanel.add(myName);
        namePanel.setOpaque(false);

        JPanel oppNamePanel = new JPanel();
        oppNamePanel.setLayout(new GridBagLayout());
        oppNamePanel.add(myOppName);
        oppNamePanel.setOpaque(false);

        myRoomPanel.add(namePanel, 0);
        myRoomPanel.add(new JLabel());
        myRoomPanel.add(oppNamePanel, 2);

        myRoomPanel.add(myHero, 3);
        myRoomPanel.add(new JLabel());
        myRoomPanel.add(myRoomContents, 5);

        myRoomPanel.add(hpPanel,6);
        myRoomPanel.add(new JLabel());
        myRoomPanel.add(myOppHpPanel,8);

        myRoomPanel.setVisible(true);
    }
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
    private model.Monster getEnemy() {
        model.AbstractDungeonCharacter monster = null;
        for (model.AbstractDungeonCharacter character: myDungeon.getCurrentRoom().getCharacters()) {
            if(character instanceof model.Monster) {
                monster = character;
            }
        }
        return (model.Monster) monster;
    }
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

    private JPanel setItemPanel(final JButton theButton, final JLabel theLabel){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
        panel.setBackground(myBackgroundColor);
        panel.add(theButton);
        panel.add(theLabel);
        theButton.setEnabled(false);
        return panel;
    }
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
        myNorth.addActionListener(theEvent -> traverse(Direction.NORTH));
        myEast.addActionListener(theEvent -> traverse(Direction.EAST));
        mySouth.addActionListener(theEvent -> traverse(Direction.SOUTH));
        myWest.addActionListener(theEvent -> traverse(Direction.WEST));

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
            + NEWLINE + "Number of movement: "
            + NEWLINE + "Current level: ", "Statistics", JOptionPane.PLAIN_MESSAGE));

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
            myDungeon.addPropertyChangeListener(this);
            myDungeon.updateView();
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
     * Helper method that opens up the window to enter your balance.
     */
    private void characterCreation() throws SQLException {
        JFrame creationWindow = new JFrame();
        String name = JOptionPane.showInputDialog(creationWindow, "Name:");
        if (name == null || name.equals("")) {
            invalidNamePane();
        }
        String[] classes = {"Warrior", "Rogue", "Priestess"};
        JFrame createWindow = new JFrame();
        int classChoice = JOptionPane.showOptionDialog(createWindow, "Choose a class",
            "Character Creation",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE,null, classes, classes[0]);
        createWindow.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent theCloseEvent) {
                myDungeon.setGameActive(false);
            }
        });
        if (myDungeon.getGameActive()) {
            try {
                myDungeon.createCharacter(name, classChoice);
                myName.setText(myDungeon.getHero().getCharName());
                myHeroHP.setText(String.valueOf(myDungeon.getHero().getHP()));
                myHeroMaxHP.setText(String.valueOf(myDungeon.getHero().getMaxHP()));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (NullPointerException npe) {
                myDungeon.setGameActive(false);
            }
            startGame();
        }
    }
    private void invalidNamePane() throws SQLException {
        JFrame invalidBalance = new JFrame();
        JOptionPane.showMessageDialog(invalidBalance, "Invalid Name", "Error", JOptionPane.WARNING_MESSAGE);
        characterCreation();
    }
    private void startGame() throws SQLException {
        myDungeon.setGameActive(true);
        myDungeon.reset();
    }
    private void traverse (final Direction theDir){
        if(theDir == Direction.NORTH){
            myDungeon.moveUp();
        }else if(theDir == Direction.EAST){
            myDungeon.moveRight();
        }else if(theDir == Direction.SOUTH){
            myDungeon.moveDown();
        }else if(theDir == Direction.WEST){
            myDungeon.moveLeft();
        }
        myMap.setText(myDungeon.getFloorString());
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
        //update Inventory UI
        //update map
        //Hero hp falls to 0
        //Monster hp falls to 0
        if ("MESSAGE".equals(s)) {
            String t = theEvent.getNewValue().toString();
            myMessages.setText(t);
        } else if ("COMBAT STATUS".equals(s)) {
            if (theEvent.getNewValue().equals(true)) {
                myAttack.setEnabled(true);
                mySkill.setEnabled(true);
                myFlee.setEnabled(true);
                myOppName.setText(getEnemy().getName());
                myOppHP.setText(String.valueOf(getEnemy().getHP()));
                myOppMaxHP.setText(String.valueOf(getEnemy().getMaxHP()));
                myOppHpPanel.setVisible(true);
                myOppName.setVisible(true);
                // Test code to make it obvious that combat has been entered.
                Color color = Color.RED;
                myFightPanel.setBackground(color);
                myNorth.setEnabled(false);
                myEast.setEnabled(false);
                mySouth.setEnabled(false);
                myWest.setEnabled(false);
                mySave.setEnabled(false);
                mySaveGame.setEnabled(false);
                myVisionPotion.setEnabled(false);
            } else {
                myAttack.setEnabled(false);
                mySkill.setEnabled(false);
                myFlee.setEnabled(false);
                mySave.setEnabled(true);
                mySaveGame.setEnabled(true);
                myOppHpPanel.setVisible(false);
                myOppName.setVisible(false);
                if (!myVisionPotionAmount.getText().equals("0")) {
                    myVisionPotion.setEnabled(true);
                }
                Color color = Color.LIGHT_GRAY.darker();
                myFightPanel.setBackground(color);
                myNorth.setEnabled(myDungeon.getCurrentRoom().canWalkNorth());
                myEast.setEnabled(myDungeon.getCurrentRoom().canWalkEast());
                mySouth.setEnabled(myDungeon.getCurrentRoom().canWalkSouth());
                myWest.setEnabled(myDungeon.getCurrentRoom().canWalkWest());
            }
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
            myStartGame.setEnabled((boolean) theEvent.getOldValue());
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
            if (theEvent.getNewValue().equals(true)) {
                model.Room room = myDungeon.getCurrentRoom();
                myNorth.setEnabled(room.canWalkNorth());
                myEast.setEnabled(room.canWalkEast());
                mySouth.setEnabled(room.canWalkSouth());
                myWest.setEnabled(room.canWalkWest());
            } else {
                myNorth.setEnabled(false);
                myEast.setEnabled(false);
                mySouth.setEnabled(false);
                myWest.setEnabled(false);
            }
        } else if ("HP CHANGE".equals(s)) {
            myHeroHP.setText(theEvent.getNewValue().toString());
        } else if ("LEVEL UP".equals(s)) {
            myHeroMaxHP.setText(theEvent.getNewValue().toString());
            myHeroHP.setText(theEvent.getNewValue().toString());
        }
    }
}
