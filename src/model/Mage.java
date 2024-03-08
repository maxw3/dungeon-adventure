package model;

import java.sql.SQLException;

public class Mage extends Hero {
    private Mage() throws SQLException {
        this("");
        throw new IllegalCallerException("Private Constructor Call on Mage");
    }
    public Mage(String theName) throws SQLException {
        super(theName, "Mage");
    }

    @Override
    public final void skill(final AbstractDungeonCharacter theTarget) {
        healOrDamage(myMaxHP / 4);
        super.skill(theTarget);
    }

    @Override
    public final String skillDescription() {
        return "model.Mage heals itself.";
    }
}
