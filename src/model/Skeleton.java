package model;

public class Skeleton extends Monster{
    public Skeleton(){
        this(1);
    }
    public Skeleton(final int theFloor){
        super("model.Skeleton");
        double modifier = FLOOR_MODIFIERS[theFloor - 1];

        setMaxHP((int)(100 * modifier));
        setAtkSpd(1);
        setMyHitChance((int)(100 - (50 / modifier)));
        setAttack((int)(50 * modifier));
    }
}
