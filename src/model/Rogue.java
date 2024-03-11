package model;

import java.sql.SQLException;

/**
 * Rogue class for hero.
 * The special skill is that they can increase their attack speed temporarily
 * as well as partially blind the enemy to lower their hit chance
 * fast, and balanced
 */
public final class Rogue extends Hero {
    /**
     * private constructor to avoid calls
     * @throws SQLException
     */
    private Rogue() throws SQLException {
        this("");
        throw new IllegalCallerException("Private Constructor Call on Rogue");
    }
    /**
     * Constructor
     * @param theName the name of the hero
     * @throws SQLException
     */
    public Rogue(final String theName) throws SQLException {
        super(theName, "Rogue");
    }

    /**
     * attack the target with increased attack speed
     * and lower their accuracy
     * @param theTarget The enemy
     * @return how much damage did the rogue deal?
     */
    @Override
    public String skill(final AbstractDungeonCharacter theTarget) {
        setAtkSpd((int) (getAtkSpd() * 1.5));
        theTarget.setHitChance(getHitChance() - 20);

        String result = attack(theTarget);

        setAtkSpd((int) (getAtkSpd() * 0.667));

        return skillDescription() + "\n" + "You deal " + result;
    }

    @Override
    public String skillDescription() {
        return "You gain haste and are able to attack faster and dodge more!";
    }
}
