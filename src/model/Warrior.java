package model;

public class Warrior extends Hero {
    private Warrior() { this(""); }
    protected Warrior(final String theName) {
        super(theName, 500, 50, 1, 75, 50);
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
