package model;

import java.sql.SQLException;

public final class Rogue extends Hero {
    private Rogue() throws SQLException {
        this("");
        throw new IllegalCallerException("Private Constructor Call on Rogue");
    }
    public Rogue(final String theName) throws SQLException {
        super(theName, "Rogue");
    }

    @Override
    public String skill(final AbstractDungeonCharacter theTarget) {
        setAtkSpd((int) (getAtkSpd() * 1.5));
        theTarget.setHitChance(getHitChance() - 20);
System.out.println("Remember to set hit chance back after skill duration expires.");

        String result = attack(theTarget);

        setAtkSpd((int) (getAtkSpd() * 0.667));

        return result;
    }

    @Override
    public String skillDescription() {
        return "model.Rogue gets haste and is able to attack faster and dodge more.";
    }
}
