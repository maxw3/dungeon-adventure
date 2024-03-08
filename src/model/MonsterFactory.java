package model;

import java.sql.SQLException;
import java.util.Random;

public final class MonsterFactory {

    private static final Random RANDOM = new Random();
    private static final int NUM_OF_MONSTER_TYPES = 5;
    private MonsterFactory() { throw new IllegalStateException("Utility Class"); }

    public static AbstractDungeonCharacter createSkeleton(final int theFloor)
        throws SQLException {
        return new Monster("Skeleton", theFloor);
//        modifier = FLOOR_MODIFIERS[theFloor - 1];
//        return new Monster("Skeleton",(int)(100 * modifier),(int)(50 * modifier),
//            1,(int)(100 - (50 / modifier)),0,
//            0.1,0.1);
    }
    public static AbstractDungeonCharacter createGremlin(final int theFloor)
        throws SQLException {
        return new Monster("Gremlin", theFloor);
    }
    public static AbstractDungeonCharacter createOgre(final int theFloor) throws SQLException {
        return new Monster("Ogre", theFloor);
    }
    public static AbstractDungeonCharacter createOrc(final int theFloor) throws SQLException {
        return new Monster("Orc", theFloor);
    }
    public static AbstractDungeonCharacter createTroll(final int theFloor)
        throws SQLException {
        return new Monster("Troll", theFloor);
    }
    public static AbstractDungeonCharacter createMonster(final int theFloor)
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
    public static AbstractDungeonCharacter createDummy() throws SQLException {
        return new Monster("Dummy", 1);
    }


}
