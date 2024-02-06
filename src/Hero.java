public class Hero extends DungeonCharacter{


    protected Hero(String theName){
        super(1);
        myName = theName;
    }

    @Override
    public String skill(final DungeonCharacter theTarget) {return null;}
    protected final void levelUp(){
        multiplyMaxHP(1.1);
        multiplyAttack(1.2);
    }
}
