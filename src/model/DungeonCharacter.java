package model;

public abstract class DungeonCharacter {
    public static final String NEW_LINE = System.lineSeparator();
    protected static final int MIN_STAT = 0;
    protected String myName;
    protected int myHP;
    protected int myMaxHP;
    protected int myAttack;
    protected int myAtkSpd;
    protected int myHitChance;
    protected int myBlockChance;
    private final int[] myPosition;
    //private final model.Dummy myDummy = new model.Dummy();

    protected DungeonCharacter(){
        myMaxHP = -1;
        myHP = -1;
        myAttack = 0;
        myAtkSpd = 0;
        myBlockChance = 0;

        myPosition = new int[2];
        myPosition[0] = 0;
        myPosition[1] = 0;
    }

    protected final void setMaxHP(final int theHP){
        double hPRatio = (double)myHP/myMaxHP;
        myMaxHP = theHP;
        setCurrentHP((int)(myMaxHP * hPRatio));
    }
    protected final void increaseMaxHP(final int theChange){
        double hPRatio = (double)myHP/myMaxHP;
        myMaxHP += theChange;
        setCurrentHP((int)(myMaxHP * hPRatio));
    }
    protected final void multiplyMaxHP(final double theMultiplier){
        double hPRatio = (double)myHP/myMaxHP;
        myMaxHP *= theMultiplier;
        setCurrentHP((int)(myMaxHP * hPRatio));
    }
    private void setCurrentHP(final int theHP){
        myHP = theHP;
    }

    public final void setPosition(int[] thePosition){
        myPosition[0] = thePosition[0];
        myPosition[1] = thePosition[1];
    }

    /**
     * Changes the HP of the Character by a fixed amount.
     * @param theAmount the amount that HP changes (positive is heal, negative is damage)
     */
    protected void healOrDamage(final int theAmount){
        myHP += theAmount;
    }
    protected final void setAttack(final int theAttack){
        myAttack = theAttack;
    }
    protected final void increaseAttack(final int theChange){
        myAttack += theChange;
    }
    protected final void multiplyAttack(final double theMultiplier){
        myAttack *= theMultiplier;
    }
    protected final void setAtkSpd(final int theAtkSpd){
        myAtkSpd = theAtkSpd;
    }
    protected final void increaseAtkSpd(final int theChange){
        myAtkSpd += theChange;
    }
    protected final void multiplyAtkSpd(final double theMultiplier){
        myAtkSpd *= theMultiplier;
    }
    protected final void setHitChance(final int theHitChance){
        myHitChance = theHitChance;
    }
    protected final void increaseHitChance(final int theChange){
        myHitChance += theChange;
    }
    public final boolean isBlocked(){
        int roll =(int) (Math.random() * 100);
        if (roll > getBlockChance()){
            return false;
        }
        return true;
    }

    protected final int getMaxHP() {
        return  myMaxHP;
    }
    protected final int getHP() {
        return myHP;
    }
    protected final int getBlockChance(){
        return myBlockChance;
    }
    protected final int getAtkSpd(){
        return myAtkSpd;
    }
    protected final int getHitChance(){
        return myHitChance;
    }
    protected final int getAttack(){
        return myAttack;
    }
    public final int[] getPosition(){
        return myPosition;
    }
    public final void attack(final DungeonCharacter theTarget) {
        for (int i = 0; i < myAtkSpd; i++) {
            int roll = roll();

            if (roll <= getHitChance() && !theTarget.isBlocked()) {
                double multiplier = Math.random() + 0.5;
                theTarget.healOrDamage((int) (getAttack() * multiplier * -1));
            }
        }
    }
    public void skill(final DungeonCharacter theTarget){
        skillDescription();
    }
    protected abstract String skillDescription();
    protected final int roll(){
        return (int) Math.random() * 100;
    }

    public final String toString(){
        StringBuilder output = new StringBuilder("Name: ");
        output.append(myName);
        output.append(NEW_LINE);

        output.append("HP: ");
        output.append(myHP);
        output.append("/");
        output.append(myMaxHP);
        output.append(NEW_LINE);

        output.append("Attack: ");
        output.append(myAttack);
        output.append(NEW_LINE);

        output.append("Hit Chance: ");
        output.append(myHitChance);
        output.append(NEW_LINE);

        output.append("Block Rate: ");
        output.append(myBlockChance);
        output.append(NEW_LINE);

        output.append("Skill: ");
        output.append(skillDescription());
        output.append(NEW_LINE);

        return output.toString();
    }
}