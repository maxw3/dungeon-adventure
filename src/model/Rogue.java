package model;

public class Rogue extends Hero {
    private Rogue(){this("");}
    protected Rogue(final String theName) {
        super(theName, 350, 30, 2, 85, 25);
    }

    @Override
    public final void skill(final AbstractDungeonCharacter theTarget) {
        setAtkSpd((int) (getAtkSpd() * 1.5));
        theTarget.setHitChance(getHitChance() - 20);
System.out.println("Remember to set hit chance back after skill duration expires.");

        attack(theTarget);

        setAtkSpd((int) (getAtkSpd() * 0.667));

        super.skill(theTarget);
    }

    @Override
    public final String skillDescription() {
        return "model.Rogue gets haste and is able to attack faster and dodge more.";
    }
}
