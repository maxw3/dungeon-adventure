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
    public void skill(final AbstractDungeonCharacter theTarget) {
        healOrDamage(myMaxHP / 4);
        super.skill(theTarget);
    }

    @Override
    public String skillDescription() {
        return "model.Mage heals itself.";
    }
}
