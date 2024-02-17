package model;

public class Gremlin extends Monster{
    public Gremlin(){
        this(1);
    }
    public Gremlin(final int theFloor){
        super("model.Gremlin", 0.25, 0.1);
        double modifier = FLOOR_MODIFIERS[theFloor - 1];

        setMaxHP((int)(100 * modifier));
        setAtkSpd(2);
        setHitChance((int)(100 - (40 / modifier)));
        setAttack((int)(30 * modifier));
    }
}
