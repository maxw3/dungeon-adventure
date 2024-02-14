public class Orc extends Monster{
    public Orc(){
        this(1);
    }
    public Orc(final int theFloor){
        super("Orc", 0.2, 0.25);
        double modifier = FLOOR_MODIFIERS[theFloor - 1];

        setMaxHP((int)(150 * modifier));
        setAtkSpd(1);
        setHitChance((int)(100 - (60 / modifier)));
        setAttack((int)(75 * modifier));
    }
}
