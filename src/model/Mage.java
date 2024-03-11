package model;

import java.sql.SQLException;

/**
 * Mage/Priestess class for hero.
 * The special skill is that they can heal themselves for a turn
 * without imbibing an HP Potion or levelling up
 * Frail, but powerful
 */
public final class Mage extends Hero {
    /**
     * private constructor to avoid calls
     * @throws SQLException
     */
    private Mage() throws SQLException {
        this("");
        throw new IllegalCallerException("Private Constructor Call on Mage");
    }

    /**
     * Constructor
     * @param theName the name of the hero
     * @throws SQLException
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
        healOrDamage(myMaxHP / 4);
        return "healed " + myMaxHP / 4 + " HP!";
    }

    @Override
    public String skillDescription() {
        return super.getMyClass() + " heals itself.";
    }
}
