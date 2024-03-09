package model;

import java.sql.SQLException;

public final class Monster extends AbstractDungeonCharacter {


    private Monster() throws SQLException {
        this("", 0);
        throw new IllegalCallerException("Private Constructor Call on Monster");
    }
    Monster(final String theName) throws SQLException {
        super(theName, 1);
    }
    Monster(final String theName, final int theFloor)
        throws SQLException {
        super(theName, theFloor);
    }
    @Override
    public void skill(final AbstractDungeonCharacter theTarget) {
        theTarget.healOrDamage((int) (myHP * myHealMultiplier));
    }

    @Override
    public String skillDescription() {
        return myName + " has a chance to heal itself after every round.";
    }
    @Override
    public void healOrDamage(final int theAmount) {
        super.healOrDamage(theAmount);
        if (theAmount < 0 && !myName.equals("Rat King")  && getHP() < getMaxHP()) {
            setAtkSpd(((int) ((double)getHP() / (double)getMaxHP()) * 10) + 1);
        }
    }
}
