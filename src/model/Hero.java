package model;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The playable character controlled by the user
 */
public class Hero extends AbstractDungeonCharacter {
    /**
     * Class of the hero
     */
    private final String myClass;
    /**
     * name of the hero
     */
    private final String myCharName;
    /**
     * position of the hero in the floor
     */
    private int[] myPosition = new int[2];

    /**
     * Constructor
     * @param theName the name of the hero
     * @param theClass the class of the hero
     * @throws SQLException
     */
    protected Hero(final String theName, final String theClass) throws SQLException {
        super(theClass);
        myCharName = theName;
        myClass = theClass;
    }

    /**
     * The skill of the hero
     * @param theTarget The target of the skill
     * @return What did the skill do?
     */
    @Override
    public String skill(final AbstractDungeonCharacter theTarget) {
        return "";
    }

    /**
     * The description of the skill
     * @return the description
     */
    @Override
    public String skillDescription() {
        return null;
    }

    /**
     * Getter for myClass
     * @return the Class
     */
    protected final String getMyClass() {
        return myClass;
    }

    /**
     * Getter for myName
     * @return the Name
     */
    public final String getCharName() {
        return myCharName;
    }

    /**
     * Setter for myPosition
     * @param theRow the X-coord
     * @param theColumn the Y-coord
     */
    public final void setPosition(final int theRow, final int theColumn) {
        myPosition[0] = theRow;
        myPosition[1] = theColumn;
    }

    /**
     * Level up the hero
     * @throws SQLException
     */
    protected final void levelUp() throws SQLException {
        String query = "SELECT * FROM character WHERE CharName = '" + getMyClass() + "'";
        ResultSet rs = controller.DungeonController.STATEMENT.executeQuery(query);
        setMaxHP((int) (getMaxHP() * rs.getDouble("HPMultiplier")));
        setAttack((int)(getAttack() * rs.getDouble("AttackMultiplier")));
        setHitChance((int)(getHitChance() * rs.getDouble("HitChanceMultiplier")));
        myHP = getMaxHP();
    }
}
