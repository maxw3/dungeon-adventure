package model;

public class Mage extends Hero {
    private Mage(){this("");}
    protected Mage(String theName) {
        super(theName, 300, 70, 1, 80, 10);
    }

    @Override
    public final void skill(final AbstractDungeonCharacter theTarget) {
        healOrDamage(myMaxHP / 4);
        super.skill(theTarget);
    }

    @Override
    public final String skillDescription() {
        return "model.Mage heals itself.";
    }
}
