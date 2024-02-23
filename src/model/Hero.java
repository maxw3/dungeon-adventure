package model;

import enums.Direction;

public class Hero extends AbstractDungeonCharacter {


    protected Hero(String theName){
        super();
        myName = theName;
    }

    public void move(Direction theDirection){
        
    }

    @Override
    public void skill(final AbstractDungeonCharacter theTarget) {
        super.skill(theTarget);
    }

    @Override
    protected String skillDescription() {
        return null;
    }

    protected final void levelUp(){
        multiplyMaxHP(1.1);
        multiplyAttack(1.2);
    }
}
