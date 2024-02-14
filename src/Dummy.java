public class Dummy extends Monster{
    public Dummy(){
        super("Dummy");

        setMaxHP(1);
        setAtkSpd(0);
        setHitChance(100);
        setAttack(0);
    }

    @Override
    final protected void healOrDamage(final int theChange){}
}
