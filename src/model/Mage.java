package model;

import java.sql.SQLException;

public final class Mage extends Hero {
    private Mage() throws SQLException {
        this("");
        throw new IllegalCallerException("Private Constructor Call on Mage");
    }
    public Mage(String theName) throws SQLException {
        super(theName, "Mage");
    }

    @Override
    public String skill(final AbstractDungeonCharacter theTarget) {
        healOrDamage(myMaxHP / 4);
        return "healed " + myMaxHP / 4 + " HP!";
    }

    @Override
    public String skillDescription() {
        return "model.Mage heals itself.";
    }
}
