package model;

import java.sql.SQLException;

public class Warrior extends Hero {
    private Warrior() throws SQLException {
        this("");
        throw new IllegalCallerException("Private Constructor Call on Warrior");
    }
    public Warrior(final String theName) throws SQLException {
        super(theName, "Warrior");
    }

    @Override
    public final void skill(final AbstractDungeonCharacter theTarget) {
        setAttack(getAttack() + 50);
        setHitChance(getHitChance() - 25);

        attack(theTarget);

        setAttack(getAttack() - 50);
        setHitChance(getHitChance() + 25);

        super.skill(theTarget);
    }

    @Override
    public final String skillDescription() {
        return "model.Warrior performs a giant haphazard swing at the monster";
    }
}
