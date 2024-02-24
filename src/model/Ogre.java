package model;

public class Ogre extends Monster {
    public Ogre() {
        this(1);
    }
    public Ogre(final int theFloor) {
        super("model.Ogre", 0.25, 0.2);
        final double modifier = FLOOR_MODIFIERS[theFloor - 1];

        setMaxHP((int)(250 * modifier));
        setAtkSpd(1);
        setHitChance((int)(100 - (80 / modifier)));
        setAttack((int)(80 * modifier));
    }
}
