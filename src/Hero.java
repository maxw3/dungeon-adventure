public class Hero extends DungeonCharacter{
    private final String myName;
    private int myHP;
    private int myMaxHP;
    private int myAttack;
    private int myAtkSpd;
    private int myHitChance;
    private int myBlockChance;

    protected Hero(){
        super();
        myName = "Hero";
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

    }
}
