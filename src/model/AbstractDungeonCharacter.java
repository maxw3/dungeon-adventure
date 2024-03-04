package model;

public abstract class AbstractDungeonCharacter {
    public static final String NEW_LINE = System.lineSeparator();
    public static final int MIN_STAT = 0;
    protected String myName;
    protected int myHP;
    protected int myMaxHP;
    protected int myAttack;
    protected int myAtkSpd;
    protected int myHitChance;
    protected int myBlockChance;

    //private final model.Dummy myDummy = new model.Dummy();

    private AbstractDungeonCharacter(){
        throw new IllegalCallerException("private Constructor");
    }
    protected AbstractDungeonCharacter(final String theName, final int theHP, final int theAttack,
                                       final int theAtkSpd, final int theHitChance, final int theBlockChance) {
        myName = theName;
        myMaxHP = theHP;
        myHP = myMaxHP;
        myAttack = theAttack;
        myAtkSpd = theAtkSpd;
        myHitChance = theHitChance;
        myBlockChance = theBlockChance;
    }

    /**
     * Changes the HP of the Character by a fixed amount.
     * @param theAmount the amount that HP changes (positive is heal, negative is damage)
     */
    public void healOrDamage(final int theAmount) {
        myHP += theAmount;
    }

    public final void attack(final AbstractDungeonCharacter theTarget) {
        for (int i = 0; i < myAtkSpd; i++) {
            final int roll = roll();

            if (roll <= getHitChance() && !theTarget.isBlocked()) {
                final double multiplier = Math.random() + 0.5;
                theTarget.healOrDamage((int) (getAttack() * multiplier * -1));
            }
        }
    }

    public void skill(final AbstractDungeonCharacter theTarget) {
        skillDescription();
    }

    protected final void setMaxHP(final int theHP) {
        final double hPRatio = (double)myHP / myMaxHP;
        myMaxHP = theHP;
        setCurrentHP((int)(myMaxHP * hPRatio));
    }

    protected final void setAttack(final int theAttack) {
        myAttack = theAttack;
    }
    protected final void setAtkSpd(final int theAtkSpd) {
        myAtkSpd = theAtkSpd;
    }

    protected final void setHitChance(final int theHitChance) {
        myHitChance = theHitChance;
    }

    private void setCurrentHP(final int theHP) {
        myHP = theHP;
    }

    public final String toString() {
        final StringBuilder output = new StringBuilder("Name: ");
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

    public final boolean isBlocked() {
        int roll = (int)(Math.random() * 100);
        return roll <= getBlockChance();
    }

    public final int getMaxHP() {
        return  myMaxHP;
    }
    public final int getHP() {
        return myHP;
    }
    public final int getBlockChance() {
        return myBlockChance;
    }
    public final int getAtkSpd() {
        return myAtkSpd;
    }
    public final int getHitChance() {
        return myHitChance;
    }
    public final int getAttack() {
        return myAttack;
    }

    public abstract String skillDescription();

    protected final int roll() {
        return (int)(Math.random() * 100);
    }
}