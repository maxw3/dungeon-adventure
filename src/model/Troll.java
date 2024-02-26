package model;

public class Troll extends Monster {
    public Troll() {
        this(1);
    }
    public Troll(final int theFloor) {
        super("model.Troll", 0.1, 0.5);
        final double modifier = FLOOR_MODIFIERS[theFloor - 1];

        setMaxHP((int)(200 * modifier));
        setAtkSpd(1);
        setHitChance((int)(100 - (60 / modifier)));
        setAttack((int)(60 * modifier));
    }
}
