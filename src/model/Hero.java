package model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Hero extends AbstractDungeonCharacter {
    private final String myClass;
    private final String myCharName;
    private int[] myPosition = new int[2];


    protected Hero(final String theName, final String theClass) throws SQLException {
        super(theClass);
        myCharName = theName;
        myClass = theClass;
    }

    @Override
    public void skill(final AbstractDungeonCharacter theTarget) {}

    @Override
    public String skillDescription() {
        return null;
    }
    protected final String getMyClass() {
        return myClass;
    }
    public final String getCharName() {
        return myCharName;
    }

    public final void setPosition(final int theRow, final int theColumn) {
        myPosition[0] = theRow;
        myPosition[1] = theColumn;
    }
    public final int[] getPosition() {
        return myPosition;
    }
    protected final void levelUp() throws SQLException {
        String query = "SELECT * FROM character WHERE CharName = '" + getMyClass() + "'";
        ResultSet rs = controller.DungeonController.STATEMENT.executeQuery(query);
        setMaxHP((int) (getMaxHP() * rs.getDouble("HPMultiplier")));
        setAttack((int)(getAttack() * rs.getDouble("AttackMultiplier")));
        setHitChance((int)(getHitChance() * rs.getDouble("HitChanceMultiplier")));
        myHP = getMaxHP();
    }
}
