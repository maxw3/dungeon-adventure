public class Skeleton extends Monster{
    public Skeleton(){
        this(1);
    }
    public Skeleton(final int theFloor){
        super("Skeleton");
        double  modifier;
        switch(theFloor){
            case 2:
                modifier = FLOOR_2_MODIFIER;
                break;
            case 3:
                modifier = FLOOR_3_MODIFIER;
                break;
            case 4:
                modifier = FLOOR_4_MODIFIER;
                break;
            case 5:
                modifier = FLOOR_5_MODIFIER;
                break;
            default:
                modifier = 1;

        }

        setMyHP((int)(100 * modifier));
        setMyAtkSpd(1);
        setMyHitChance((int)(100 - (50 / modifier)));
        setMyAttack((int)(50 * modifier));
    }
}
