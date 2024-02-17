package model;

public class Rogue extends Hero{
    protected Rogue(String theName) {
        super(theName);
        setMaxHP(350);
        setAttack(30);
        setAtkSpd(2);
        setHitChance(85);
    }

    @Override
    public final void skill(final DungeonCharacter theTarget){
        multiplyAtkSpd(1.5);
        theTarget.increaseHitChance(-20);
System.out.println("Remember to set hit chance back after skill duration expires.");

        attack(theTarget);

        multiplyAtkSpd(0.667);

        super.skill(theTarget);
    }

    @Override
    protected final String skillDescription(){
        return "model.Rogue gets haste and is able to attack faster and dodge more.";
    }
}
