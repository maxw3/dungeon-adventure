package model;

public class Monster extends AbstractDungeonCharacter {
    protected final double myHealMultiplier;
    protected final double myHealRate;

    private Monster() {
        this("", 0, 0, 0, 0, 0, 0.0, 0.0);
    }
    protected Monster(final String theName, final int theHP, final int theAttack,
                      final int theAtkSpd, final int theHitChance, final int theBlockChance,
                      final double theHealMultiplier, final double theHealRate) {
        super(theName, theHP, theAttack, theAtkSpd, theHitChance, theBlockChance);
        myHealMultiplier = theHealMultiplier;
        myHealRate = theHealRate;
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
