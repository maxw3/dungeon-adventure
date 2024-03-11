package model;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Abstract class for characters in the game
 */
public abstract class AbstractDungeonCharacter implements Serializable {
    /**
     * String that acts as a line separator for new lines
     */
    private static final String NEW_LINE = System.lineSeparator();
    /**
     * The name of the Character
     */
    private String myName;
    /**
     * The current HP of the Character
     */
    private int myHP;
    /**
     * The maximum HP of the Character
     */
    private int myMaxHP;
    /**
     * The Attack of the Character
     */
    private int myAttack;
    /**
     * The Attack Speed of the Character
     */
    private int myAtkSpd;
    /**
     * The Hit Chance of the Character
     */
    private int myHitChance;
    /**
     * The Block Chance of the Character
     */
    private int myBlockChance;

    //private final model.Dummy myDummy = new model.Dummy();

    /**
     * Constructor that accepts the name of the character
     * Mainly used for heros
     *
     * @param theName   The name of the hero, or the name of the Monster
     * @throws SQLException
     */
    protected AbstractDungeonCharacter(final String theName) throws SQLException {
        String query = "SELECT * FROM character WHERE CharName = '" + theName + "'";
        ResultSet rs = controller.DungeonController.STATEMENT.executeQuery(query);

        myName = theName;
        myMaxHP = rs.getInt("MaxHP");
        myHP = myMaxHP;
        myAttack = rs.getInt("Attack");
        myAtkSpd = rs.getInt("AttackSpeed");
        myHitChance = rs.getInt("HitChance");
        myBlockChance = rs.getInt("BlockChance");
    }

    /**
     * Constructor that accepts the name of the Character and what Floor it's in
     * Used for Monsters to determing its stats
     *
     * @param theName   The type of Monster it is
     * @param theFloor  The Floor where this monster is found
     * @throws SQLException
     */
    protected AbstractDungeonCharacter(final String theName, final int theFloor) throws SQLException {
        String query = "SELECT * FROM character WHERE CharName = '" + theName + "'";
        ResultSet rs = controller.DungeonController.STATEMENT.executeQuery(query);

        myName = theName;
        myMaxHP = (int) (rs.getInt("MaxHP") * Math.pow(rs.getDouble("HPMultiplier"), theFloor));
        myHP = myMaxHP;
        myAttack = (int) (rs.getInt("Attack") * Math.pow(rs.getDouble("AttackMultiplier"), theFloor));
        myAtkSpd = rs.getInt("AttackSpeed");
        myHitChance = (int) (100 - rs.getInt("HitChance")
            / Math.pow(rs.getDouble("HitChanceMultiplier"), theFloor));
        myBlockChance = rs.getInt("BlockChance");
    }

    /**
     * Changes the HP of the Character by a fixed amount.
     * @param theAmount the amount that HP changes (positive is heal, negative is damage)
     */
    public void healOrDamage(final int theAmount) {
        myHP += theAmount;
        myHP = Math.min(myHP, myMaxHP);
    }

    /**
     * The attacker attacks the target
     * @param theTarget The Target that's being attacked
     *
     * @return  A string that tells us how much damage was dealt in a turn
     */
    public final String attack(final AbstractDungeonCharacter theTarget) {
        int damage = 0;
        int hits = 0;
        for (int i = 0; i < myAtkSpd; i++) {
            final int roll = (int)(Math.random() * 100);

            if (roll <= getHitChance() && !theTarget.isBlocked()) {
                hits++;
                final double multiplier = Math.random() + 0.5;
                int hitDamage = (int) (getAttack() * multiplier * -1);
                theTarget.healOrDamage(hitDamage);
                damage += hitDamage;
            }
        }
        return "dealt " + damage + " in " + hits + " hits!";
    }

    /**
     * The special skill of the Character
     *
     * @param theTarget The target of the skill
     * @return  A String that tells us what the skill did
     */
    public abstract String skill(final AbstractDungeonCharacter theTarget);

    /**
     * Setter of Max HP
     * @param theHP The new Max HP
     */
    protected final void setMaxHP(final int theHP) {
        final double hPRatio = (double)myHP / myMaxHP;
        myMaxHP = theHP;
        setCurrentHP((int)(myMaxHP * hPRatio));
    }

    /**
     * Setter of Attack
     * @param theAttack The new Attack
     */
    protected final void setAttack(final int theAttack) {
        myAttack = theAttack;
    }
    /**
     * Setter of Attack Speed
     * @param theAtkSpd The new Attack Speed
     */
    protected final void setAtkSpd(final int theAtkSpd) {
        myAtkSpd = theAtkSpd;
    }

    /**
     * Setter of Hit Chance
     * @param theHitChance The new Hit Chance
     */
    public final void setHitChance(final int theHitChance) {
        myHitChance = theHitChance;
    }

    /**
     * Setter of current HP
     * @param theHP The new Hit Points
     */
    private void setCurrentHP(final int theHP) {
        myHP = theHP;
    }

    @Override
    public final String toString() {
        final StringBuilder output = new StringBuilder("Name: ");
        output.append(myName);
        output.append(NEW_LINE);

        output.append("HP: ");
        output.append(myHP);
        output.append("/");
        output.append(myMaxHP);
        output.append(NEW_LINE);

        output.append("Attack: ");
        output.append(myAttack);
        output.append(NEW_LINE);

        output.append("Hit Chance: ");
        output.append(myHitChance);
        output.append(NEW_LINE);

        output.append("Block Rate: ");
        output.append(myBlockChance);
        output.append(NEW_LINE);

        output.append("Skill: ");
        output.append(skillDescription());
        output.append(NEW_LINE);

        return output.toString();
    }

    /**
     * Did the attack get blocked?
     *
     * @return Did the attack get blocked?
     */
    public final boolean isBlocked() {
        int roll = (int)(Math.random() * 100);
        return roll <= getBlockChance();
    }

    /**
     * Getter for the Character's name
     * @return The Character's name
     */
    public final String getName() { return myName; }
    /**
     * Getter for the Character's Max HP
     * @return The Character's Max HP
     */
    public final int getMaxHP() { return  myMaxHP;}
    /**
     * Getter for the Character's HP
     * @return The HP
     */
    public final int getHP() { return myHP; }
    /**
     * Getter for the Character's Block Chance
     * @return The Block Chance
     */
    public final int getBlockChance() { return myBlockChance; }
    /**
     * Getter for the Character's Attack Speed
     * @return The Attack Speed
     */
    public final int getAtkSpd() { return myAtkSpd; }
    /**
     * Getter for the Character's Hit Chance
     * @return The Hit Chance
     */
    public final int getHitChance() { return myHitChance; }
    /**
     * Getter for the Character's Attack
     * @return The Attack
     */
    public final int getAttack() { return myAttack; }

    /**
     * The description of the skill
     *
     * @return What does the skill do?
     */
    public abstract String skillDescription();
}