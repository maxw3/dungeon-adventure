package model;

import enums.Direction;

public class Hero extends AbstractDungeonCharacter {


    protected Hero(final String theName, final int theHP, final int theAttack,
                   final int theAtkSpd, final int theHitChance, final int theBlockChance) {
        super(theName, theHP, theAttack, theAtkSpd,theHitChance, theBlockChance);
    }

    @Override
    public void skill(final AbstractDungeonCharacter theTarget) {
        super.skill(theTarget);
    }

    @Override
    public String skillDescription() {
        return null;
    }

    protected final void levelUp() {
        multiplyMaxHP(1.1);
        multiplyAttack(1.2);
        myHP = getMaxHP();
    }
}
