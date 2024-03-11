package model;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The monster class that lives in the dungeon to fight the hero
 */
public final class Monster extends AbstractDungeonCharacter {
    /**
     * The amount of HP the character gains when healing itself
     */
    private double myHealMultiplier;
    /**
     * How often the character heals
     */
    private double myHealRate;
    /**
     * private constructor to avoid calls
     * @throws SQLException
     */
    private Monster() throws SQLException {
        this("", 0);
        throw new IllegalCallerException("Private Constructor Call on Monster");
    }

    /**
     * Constructor
     * @param theName What monster this is
     * @throws SQLException
     */
    Monster(final String theName) throws SQLException {
        this(theName, 1);
    }

    /**
     * Constructor
     * @param theName What monster this is
     * @param theFloor What floor this monster lives in
     * @throws SQLException
     */
    Monster(final String theName, final int theFloor)
        throws SQLException {
        super(theName, theFloor);

        String query = "SELECT * FROM character WHERE CharName = '" + theName + "'";
        ResultSet rs = controller.DungeonController.STATEMENT.executeQuery(query);
        myHealMultiplier = rs.getDouble("HealMultiplier");
        myHealRate = rs.getDouble("HealRate")
            * Math.pow(rs.getDouble("HealRateMultiplier"), theFloor);
    }

    /**
     * a chance to heal every turn the monster is fighting
     * @param theTarget the monster itself
     * @return How much did the monster heal?
     */
    @Override
    public String skill(final AbstractDungeonCharacter theTarget) {
        if (Math.random() <= myHealRate) {
            int healAmount = (int) (getHP() * myHealMultiplier);
            theTarget.healOrDamage(healAmount);
            return getName() + "healed " + healAmount + " HP!";
        }
        else {
            return "";
        }
    }

    @Override
    public String skillDescription() {
        return getName() + " has a chance to heal itself after every round.";
    }

    /**
     * If the monster is a Rat King, lowers the attack speed in correlation to its HP
     * @param theAmount the amount that HP changes (positive is heal, negative is damage)
     */
    @Override
    public void healOrDamage(final int theAmount) {
        super.healOrDamage(theAmount);
        if (theAmount < 0 && !myName.equals("Rat King")  && getHP() < getMaxHP()) {
            setAtkSpd(((int) ((double)getHP() / (double)getMaxHP()) * 10) + 1);
        }
    }
}
