public class Mage extends Hero{
    protected Mage(String theName) {
        super(theName);
        setMaxHP(300);
        setAttack(70);
        setAtkSpd(1);
        setHitChance(80);
    }

    @Override
    public final void skill(final DungeonCharacter theTarget){
        healOrDamage(myMaxHP/4);

        super.skill(theTarget);
    }

    @Override
    protected final String skillDescription(){
        return "Mage heals itself.";
    }
}
