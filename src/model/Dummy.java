package model;

public class Dummy extends Monster {
    public Dummy() {
        super("model.Dummy", 0, 0);

        setMaxHP(1);
        setAtkSpd(0);
        setHitChance(100);
        setAttack(0);
    }

    @Override
    public final void healOrDamage(final int theChange) { }
}
