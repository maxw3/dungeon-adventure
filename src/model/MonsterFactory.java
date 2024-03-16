/*
 *  Dungeon Adventure Project for TCSS 360
 *  Winter 2024, Jordan, Terence, Max, and Gabriel
 */

package model;

import java.sql.SQLException;
import java.util.Random;

/**
 * A Factory for monster generation
 * @author Jordan, Max, Gabriel, Terence
 * @version Winter 2024
 */
public final class MonsterFactory {
    /**
     * Random number generator
     */
    private static final Random RANDOM = new Random();
    /**
     * how many non-boss monsters there are
     */
    private static final int NUM_OF_MONSTER_TYPES = 5;

    /**
     * private constructor to avoid calls
     */
    private MonsterFactory() { throw new IllegalStateException("Utility Class"); }

    /**
     * create a skeleton
     * @param theFloor the floor the skeleton lives in
     * @return the skeleton
     * @throws SQLException
     */
    public static Monster createSkeleton(final int theFloor)
        throws SQLException {
        return new Monster("Skeleton", theFloor);
    }
    /**
     * create a Gremlin
     * @param theFloor the floor the Gremlin lives in
     * @return the Gremlin
     * @throws SQLException
     */
    public static Monster createGremlin(final int theFloor)
        throws SQLException {
        return new Monster("Gremlin", theFloor);
    }
    /**
     * create a Ogre
     * @param theFloor the floor the Ogre lives in
     * @return the Ogre
     * @throws SQLException
     */
    public static Monster createOgre(final int theFloor) throws SQLException {
        return new Monster("Ogre", theFloor);
    }
    /**
     * create a Orc
     * @param theFloor the floor the Orc lives in
     * @return the Orc
     * @throws SQLException
     */
    public static Monster createOrc(final int theFloor) throws SQLException {
        return new Monster("Orc", theFloor);
    }
    /**
     * create a Troll
     * @param theFloor the floor the Troll lives in
     * @return the Troll
     * @throws SQLException
     */
    public static Monster createTroll(final int theFloor)
        throws SQLException {
        return new Monster("Troll", theFloor);
    }

    /**
     * Monster creator that's randomized
     * @param theFloor the floor the monster resides in
     * @return  the monster
     * @throws SQLException
     */
    public static Monster createMonster(final int theFloor)
        throws SQLException {
        final int choice = RANDOM.nextInt(NUM_OF_MONSTER_TYPES);
        if (choice == 0) {
            return createSkeleton(theFloor);
        } else if (choice == 1) {
            return createGremlin(theFloor);
        } else if (choice == 2) {
            return createOgre(theFloor);
        } else if (choice == 3) {
            return createOrc(theFloor);
        } else {
            return createTroll(theFloor);
        }
    }
    /**
     * create a Boss
     * @param theFloor the floor the Boss lives in
     * @return the Boss
     * @throws SQLException
     */
    public static Monster createBoss(final int theFloor)
        throws SQLException {
        return switch (theFloor) {
            case 1 -> new Monster("Slime");
            case 2 -> new Monster("Minotaur");
            case 3 -> new Monster("Griffon");
            case 4 -> new Monster("Rat King");
            case 5 -> new Monster("Hydra");
            default -> throw new SQLException("Invalid floor for creating a boss.");
        };

    }
    /**
     * create a Dummy
     * @return the Dummy
     * @throws SQLException
     */
    public static Monster createDummy() throws SQLException {
        return new Monster("Dummy", 1);
    }


}
