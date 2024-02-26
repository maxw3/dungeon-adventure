package model;

public class Monster extends AbstractDungeonCharacter {
    protected static final double FLOOR_1_MODIFIER = 1.0;
    protected static final double FLOOR_2_MODIFIER = 1.25;
    protected static final double FLOOR_3_MODIFIER = 1.5;
    protected static final double FLOOR_4_MODIFIER = 1.75;
    protected static final double FLOOR_5_MODIFIER = 2;
    protected static final double[] FLOOR_MODIFIERS = {FLOOR_1_MODIFIER, FLOOR_2_MODIFIER, FLOOR_3_MODIFIER, FLOOR_4_MODIFIER, FLOOR_5_MODIFIER};
    protected final double myHealMultiplier;
    protected final double myHealRate;

    private Monster() { this("", 0.0, 0.0); }
    protected Monster(final String theName, final double theHealMultiplier, final double theHealRate) {
        super();
        myName = theName;
        myHealMultiplier = theHealMultiplier;
        myHealRate = theHealRate;
    }

    public Monster(final String theName) {
        super();
        myName = theName;
        myHealMultiplier = 1.0;
        myHealRate = 1.0;
    }
    @Override
    public final void skill(final AbstractDungeonCharacter theTarget) {
        theTarget.healOrDamage((int) (myHP * myHealMultiplier));
        super.skill(theTarget);
    }

    @Override
    public String skillDescription() {
        return "model.Monster has a chance to heal itself after every round.";
    }
}
