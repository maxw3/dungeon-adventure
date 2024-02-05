public class Monster extends DungeonCharacter{
    protected static final double FLOOR_2_MODIFIER = 1.25;
    protected static final double FLOOR_3_MODIFIER = 1.5;
    protected static final double FLOOR_4_MODIFIER = 1.75;
    protected static final double FLOOR_5_MODIFIER = 2;
    private final String myName;
    private int myHP;
    private int myMaxHP;
    private int myAttack;
    private int myAtkSpd;
    private int myHitChance;
    private int myBlockChance;

    private Monster(){myName = "";}
    protected Monster(String theName){
        super();
        myName = theName;
        myAttack = 0;
        myMaxHP = 0;
        myHP = 0;
        myAtkSpd = 0;
        myBlockChance = 0;

    }
    @Override
    protected void setMyHP(final int theChange) {
        myHP += theChange;
    }

    @Override
    protected void setMyAtkSpd(final int theChange) {
        myAtkSpd += theChange;
    }

    @Override
    protected void setMyHitChance(final int theChange) {
        myHitChance += theChange;
    }

    @Override
    protected void setMyAttack(int theChange) {
        myAttack += theChange;
    }

    @Override
    protected int getBlockChance() {
        return myBlockChance;
    }

    @Override
    protected int getHitChance() {
        return myHitChance;
    }

    @Override
    protected int getAttack() {
        return myAttack;
    }

    @Override
    public void skill() {
        setMyHP((int) (myHP * 0.1));
    }
}
