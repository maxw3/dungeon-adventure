package view;

import enums.Direction;
import controller.DungeonController;
import model.DungeonLogic;
import model.HealthPotion;
import model.VisionPotion;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public final class DungeonView extends JPanel implements PropertyChangeListener {
    private final static String NEWLINE = System.lineSeparator();
    /** Font for buttons on the main frame */
    private final static Font BUTTON_FONT = new Font("Arial", Font.PLAIN, 20);
    /** Font for labels on the main frame */
    private static final Font FONT = new Font("Arial", Font.BOLD, 20);
    private Direction myFleeDirection = Direction.NORTH;
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
    private JButton myHPPotion;
    private JButton myVisionPotion;
    private JButton myAttack;
    private JButton mySkill;
    private JButton myFlee;
    private JLabel myName;
    private JLabel myHPPotionAmount;
    private JLabel myVisionPotionAmount;
    private final JPanel myPanel;
    private JPanel myGamePanel;
    private JPanel myMovePanel;
    private JPanel myInventoryPanel;
    private JPanel myRoomPanel;
    private JPanel myFightPanel;

    private final JTextArea myMap;
    private JTextArea myMessages;

    final DungeonLogic myDungeon;

    public DungeonView() {
        myDungeon = model.DungeonLogic.getDungeonInstance();

        myBackgroundColor = Color.LIGHT_GRAY.darker();
        myBorder = BorderFactory.createLineBorder(Color.DARK_GRAY,2);

        myMap = new JTextArea(myDungeon.getFloorString());
        myMap.setFont(new Font("Consolas", 1, 32));
        myMap.setBackground(getBackground());

        myMessages = new JTextArea("Welcome to Dungeon Adventure!" + NEWLINE);
        myMessages.setPreferredSize(new Dimension(480,180));
        myMessages.setEditable(false);

        setMenuBar();
        DungeonController.myFrame.setJMenuBar(myMenu);

        myPanel = new JPanel();
//        GridBagLayout theLayout = new GridBagLayout();
//        GridBagConstraints theConstraints = new GridBagConstraints();
//        myPanel.setLayout(theLayout);

        setGamePanel();
        setRoomPanel();
        setFightPanel();
        setMovementPanel();
        setInventoryPanel();

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
//        locationPanel.setLayout(new GridBagLayout());
//        GridBagConstraints constraints = new GridBagConstraints();
//        constraints.gridx = 0;
//        constraints.gridy = 0;
//        constraints.gridwidth = 480;
//        constraints.gridheight = 300;
//        constraints.weightx = 0.5;
//        constraints.weighty = 0.5;
//        constraints.anchor = GridBagConstraints.CENTER;
//        constraints.fill = GridBagConstraints.BOTH;
        mainPanel.add(myRoomPanel);
        mainPanel.add(myMessages);

        JPanel fightMovePanel = new JPanel();
        fightMovePanel.setLayout(new GridLayout(1,2));
        fightMovePanel.add(myFightPanel);
        fightMovePanel.add(myMovePanel);

        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new GridLayout(3,1, 0, 10));
        actionPanel.add(myGamePanel);
        actionPanel.add(fightMovePanel);
        actionPanel.add(myInventoryPanel);

        myPanel.add(myMap, BorderLayout.LINE_START);
        myPanel.add(mainPanel, BorderLayout.CENTER);
        myPanel.add(actionPanel, BorderLayout.LINE_END);

        add(myPanel);
        myMap.setCursor(null);
        myMap.setFocusable(false);
        myMap.setEditable(false);

        addListeners();

    }

    private void setGamePanel() {
        myGamePanel = new JPanel();
        myGamePanel.setLayout(new GridLayout(3,1));
        myGamePanel.setBackground(myBackgroundColor);

        myStart = new JButton("Start Game");
        myStart.setFont(BUTTON_FONT);
        myStart.setBackground(myBackgroundColor.brighter());
        mySave = new JButton("Save Game");
        mySave.setFont(BUTTON_FONT);
        mySave.setBackground(myBackgroundColor.brighter());
        myLoad = new JButton("Load Game");
        myLoad.setFont(BUTTON_FONT);
        myLoad.setBackground(myBackgroundColor.brighter());

        myGamePanel.add(myStart);
        myGamePanel.add(mySave);
        myGamePanel.add(myLoad);
        myGamePanel.setPreferredSize(new Dimension(300,150));

        myGamePanel.setVisible(true);
    }
    private void setRoomPanel() {
        myRoomPanel = new JPanel();
        myRoomPanel.setLayout( new GridLayout(3,3));
        myRoomPanel.setPreferredSize(new Dimension(480,300));
        myRoomPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
        myRoomPanel.setBackground(Color.LIGHT_GRAY);

        myName = new JLabel(myDungeon.getHero().getCharName());
        myName.setFont(BUTTON_FONT);
        myHero = new JLabel("Hero.png");
        myHero.setBorder( BorderFactory.createLineBorder(Color.BLACK,2));
        myRoomContents = new JLabel("Monster/Item/Pit.png");
        myRoomContents.setBorder( BorderFactory.createLineBorder(Color.BLACK,2));

        JPanel namePanel = new JPanel();
        namePanel.setLayout(new GridBagLayout());
        namePanel.add(myName);
        namePanel.setOpaque(false);

        myRoomPanel.add(namePanel);
        myRoomPanel.add(new JLabel());
        myRoomPanel.add(new JLabel());

        myRoomPanel.add(myHero, 3);
        myRoomPanel.add(new JLabel());
        myRoomPanel.add(myRoomContents, 5);

        myRoomPanel.add(new JLabel());
        myRoomPanel.add(new JLabel());
        myRoomPanel.add(new JLabel());

        myRoomPanel.setVisible(true);
    }
    private void setMovementPanel(){
        myMovePanel = new JPanel();
        myMovePanel.setBackground(myBackgroundColor);

        myNorth = new JButton("^");

        myEast = new JButton(">");

        mySouth = new JButton("v");

        myWest = new JButton("<");

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

    private void setInventoryPanel(){
        myInventoryPanel = new JPanel();
        myInventoryPanel.setBackground(myBackgroundColor);

        myInventoryLabel = new JLabel("Inventory");
        myInventoryLabel.setFont(FONT);
        myInventoryLabel.setBackground(myBackgroundColor);
        myInventoryLabel.setOpaque(true);

        myHPPotion = new JButton("Health Potion");
        myHPPotion.setBackground(myBackgroundColor);
        myHPPotion.setBorder(myBorder);
        myHPPotion.setFont(BUTTON_FONT);
        myHPPotionAmount = new JLabel(
            String.valueOf(myDungeon.getInventory().getHPPotionAmount()));
        myHPPotion.setBackground(myBackgroundColor);
        myHPPotionAmount.setOpaque(false);
        myHPPotionAmount.setFont(FONT);

        myVisionPotion = new JButton("Vision Potion");
        myVisionPotion.setBackground(myBackgroundColor);
        myVisionPotion.setBorder(myBorder);
        myVisionPotion.setFont(BUTTON_FONT);
        myVisionPotionAmount = new JLabel(
            String.valueOf(myDungeon.getInventory().getVisionPotionAmount()));
        myVisionPotion.setBackground(myBackgroundColor);
        myVisionPotionAmount.setOpaque(false);
        myVisionPotionAmount.setFont(FONT);

        JPanel hpInv = new JPanel();
        hpInv.setLayout(new BoxLayout(hpInv, BoxLayout.LINE_AXIS));
        hpInv.setBackground(myBackgroundColor);

        JPanel vision = new JPanel();
        vision.setLayout(new BoxLayout(vision, BoxLayout.LINE_AXIS));
        vision.setBackground(myBackgroundColor);

        hpInv.add(myHPPotion);
        hpInv.add(myHPPotionAmount);
        vision.add(myVisionPotion);
        vision.add(myVisionPotionAmount);

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

        myAttack = new JButton("Attack");
        myAttack.setBackground(myBackgroundColor);
        myAttack.setBorder(myBorder);
        myAttack.setFont(BUTTON_FONT);
        mySkill = new JButton("Use Skill");
        mySkill.setBackground(myBackgroundColor);
        mySkill.setBorder(myBorder);
        mySkill.setFont(BUTTON_FONT);
        myFlee = new JButton("FLEE!");
        myFlee.setFont(BUTTON_FONT);

        myFightPanel.add(myAttack);
        myFightPanel.add(mySkill);
        myFightPanel.add(myFlee);
        myFightPanel.setPreferredSize(new Dimension(150,150));
    }

    /**
     * Helper method that attaches listeners to everything.
     */
    private void addListeners() {
        myNorth.addActionListener(theEvent -> traverse(Direction.NORTH));
        myEast.addActionListener(theEvent -> traverse(Direction.EAST));
        mySouth.addActionListener(theEvent -> traverse(Direction.SOUTH));
        myWest.addActionListener(theEvent -> traverse(Direction.WEST));

        myHPPotion.addActionListener(theEvent -> useItem(1));
        myVisionPotion.addActionListener(theEvent -> useItem(2));

//        myStartGame.addActionListener((theEvent -> {}));
//        myStart.addActionListener((theEvent -> {}));
//        mySaveGame.addActionListener((theEvent -> {}));
//        mySave.addActionListener((theEvent -> {}));
//        myLoadGame.addActionListener((theEvent -> {}));
//        myLoad.addActionListener((theEvent -> {}));

        myAttack.addActionListener(theEvent -> combatAction(1));
        mySkill.addActionListener(theEvent -> combatAction(2));
        myFlee.addActionListener(theEvent -> traverse(myFleeDirection));

        //listener for exit game button
        myExitGame.addActionListener((theEvent -> {
            String[] exitOptions = {"Yes","No", "Cancel"};
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
    }

    private void traverse (final Direction theDir){
        if(theDir == Direction.NORTH){
            myFleeDirection = Direction.SOUTH;
            myDungeon.moveUp();
        }else if(theDir == Direction.EAST){
            myFleeDirection = Direction.WEST;
            myDungeon.moveRight();
        }else if(theDir == Direction.SOUTH){
            myFleeDirection = Direction.NORTH;
            myDungeon.moveDown();
        }else if(theDir == Direction.WEST){
            myFleeDirection = Direction.EAST;
            myDungeon.moveLeft();
        }
        myMap.setText(myDungeon.getFloorString());
    }
  
    private void useItem(final int theItem) {
        if (theItem == 1) {
            myDungeon.getInventory().useItem(new HealthPotion());
        } else if (theItem == 2) {
            myDungeon.getInventory().useItem(new VisionPotion());
        } else {
            throw new IllegalArgumentException("Invalid item for consumption");
        }
    }
  
    private void combatAction(final int theAction) {
        if (theAction == 1) {
            //player chooses fight for the fight round
        } else if (theAction == 2) {
            //player chooses to use skill for fright round
        } else {
            throw new IllegalArgumentException("Invalid action for combat phase");
        }
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
        }
    }
}
