package model;

import java.sql.SQLException;

/**
 * Warrior class for hero.
 * The special skill is that they can attempt to do a great attack
 * at the risk of missing
 * Strong, and sturdy, but slow
 */
public final class Warrior extends Hero {
    /**
     * private constructor to avoid calls
     * @throws SQLException
     */
    private Warrior() throws SQLException {
        this("");
        throw new IllegalCallerException("Private Constructor Call on Warrior");
    }
    /**
     * Constructor
     * @param theName the name of the hero
     * @throws SQLException
     */
    public Warrior(final String theName) throws SQLException {
        super(theName, "Warrior");
    }
    /**
     * Attack the target with greater strength but lower accuracy
     * @param theTarget The target of the skill
     * @return How much damage did the Warrior deal?
     */
    @Override
    public String skill(final AbstractDungeonCharacter theTarget) {
        setAttack(getAttack() + 50);
        setHitChance(getHitChance() - 25);

        String result = attack(theTarget);

        setAttack(getAttack() - 50);
        setHitChance(getHitChance() + 25);

        return skillDescription() + " for " + result + "damage";
    }

    @Override
    public String skillDescription() {
        return "You perform a giant haphazard swing at the monster";
    }
}
