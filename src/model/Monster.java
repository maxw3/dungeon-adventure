package model;

import java.sql.SQLException;

/**
 * The monster class that lives in the dungeon to fight the hero
 */
public final class Monster extends AbstractDungeonCharacter {

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
        super(theName, 1);
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
    }

    /**
     * a chance to heal every turn the monster is fighting
     * @param theTarget the monster itself
     * @return How much did the monster heal?
     */
    @Override
    public String skill(final AbstractDungeonCharacter theTarget) {
        if (Math.random() <= myHealRate) {
            int healAmount = (int) (myHP * myHealMultiplier);
            theTarget.healOrDamage(healAmount);
            return myName + "healed " + healAmount + " HP!";
        }
        else {
            return "";
        }
    }

    @Override
    public String skillDescription() {
        return myName + " has a chance to heal itself after every round.";
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
