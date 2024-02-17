package model;

public class Warrior extends Hero{
    private Warrior(){this("");}
    protected Warrior(String theName) {
        super(theName);
        setMaxHP(500);
        setAttack(50);
        setAtkSpd(1);
        setHitChance(75);
    }

    @Override
    public final void skill(final DungeonCharacter theTarget){
        increaseAttack(50);
        increaseHitChance(-25);

        attack(theTarget);

        increaseAttack(-50);
        increaseHitChance(10);

        super.skill(theTarget);
    }

    @Override
    protected final String skillDescription(){
        return "model.Warrior performs a giant haphazard swing at the monster";
    }
}
