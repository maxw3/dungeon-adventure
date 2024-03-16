/*
 *  Dungeon Adventure Project for TCSS 360
 *  Winter 2024, Jordan, Terence, Max, and Gabriel
 */

package model;

import java.sql.SQLException;

/**
 * Mage/Priestess class for hero.
 * The special skill is that they can heal themselves for a turn
 * without imbibing an HP Potion or levelling up
 * Frail, but powerful
 * @author Jordan, Max, Gabriel, Terence
 * @version Winter 2024
 */
public final class Mage extends Hero {
    /**
     * private constructor to avoid calls
     * @throws SQLException could not query mage data
     */
    @SuppressWarnings("unused")
    private Mage() throws SQLException {
        this("");
        throw new IllegalCallerException("Private Constructor Call on Mage");
    }

    /**
     * Constructor
     * @param theName the name of the hero
     * @throws SQLException could not query mage data
     */
    public Mage(String theName) throws SQLException {
        super(theName, "Mage");
    }

    /**
     * Heal the hero for 25% of their max health this turn
     * @param theTarget The target of the skill
     * @return How much did the hero get healed for?
     */
    @Override
    public String skill(final AbstractDungeonCharacter theTarget) {
        healOrDamage(getMaxHP() / 4);
        return "healed " + getMaxHP() / 4 + " HP!";
    }

    @Override
    public String skillDescription() {
        return super.getMyClass() + " heals itself.";
    }
}
