package model;

import java.sql.SQLException;

public final class Warrior extends Hero {
    private Warrior() throws SQLException {
        this("");
        throw new IllegalCallerException("Private Constructor Call on Warrior");
    }
    public Warrior(final String theName) throws SQLException {
        super(theName, "Warrior");
    }

    @Override
    public String skill(final AbstractDungeonCharacter theTarget) {
        setAttack(getAttack() + 50);
        setHitChance(getHitChance() - 25);

        String result = attack(theTarget);

        setAttack(getAttack() - 50);
        setHitChance(getHitChance() + 25);

        return result;
    }

    @Override
    public String skillDescription() {
        return super.getMyClass() + " performs a giant haphazard swing at the monster";
    }
}
