
public abstract class DungeonCharacter {
    protected static final int MIN_STAT = 0;

    protected DungeonCharacter(){

    }

    protected abstract void setMyHP(final int theChange);
    protected abstract void setMyAtkSpd(final int theChange);
    protected abstract void setMyHitChance(final int theChange);
    protected abstract void setMyAttack(final int theChange);
    public boolean isBlocked(){
        int roll =(int) (Math.random() * 100);
        if (roll > getBlockChance()){
            return false;
        }
        return true;
    }

    protected abstract int getBlockChance();

    protected abstract int getHitChance();
    protected abstract int getAttack();
    public final void attack(final DungeonCharacter theTarget){
        int roll =(int) (Math.random() * 100);

        if (roll <= getHitChance() && !theTarget.isBlocked()){
            double multiplier = Math.random() + 0.5;
            theTarget.setMyHP((int)(getAttack() * multiplier * -1));
        }
    }
    public abstract void skill();

    public String toString(){
        return null;
    }
}
