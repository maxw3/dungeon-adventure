package model;

public class Monster extends DungeonCharacter{
    protected static final double FLOOR_1_MODIFIER = 1.0;
    protected static final double FLOOR_2_MODIFIER = 1.25;
    protected static final double FLOOR_3_MODIFIER = 1.5;
    protected static final double FLOOR_4_MODIFIER = 1.75;
    protected static final double FLOOR_5_MODIFIER = 2;
    protected static final double[] FLOOR_MODIFIERS= {FLOOR_1_MODIFIER, FLOOR_2_MODIFIER, FLOOR_3_MODIFIER,FLOOR_4_MODIFIER, FLOOR_5_MODIFIER};
    protected double myHealMultiplier;

    private Monster(){this("");}
    protected Monster(String theName){
        super();
        myName = theName;
    }

    @Override
    public final String skill(final DungeonCharacter theTarget) {
        theTarget.healOrDamage((int) (myHP * myHealMultiplier));
        return "model.Monster has a chance to heal itself after every round.";
    }
}
