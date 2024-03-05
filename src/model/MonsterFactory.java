package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public final class MonsterFactory {

    private static final Random RANDOM = new Random();
    private static final int NUM_OF_MONSTER_TYPES = 5;
    private MonsterFactory() { throw new IllegalStateException("Utility Class"); }

    public static AbstractDungeonCharacter createSkeleton(final int theFloor)
        throws SQLException {
        String query = "SELECT * FROM character WHERE CharName = 'Skeleton'";
        return createMonsterFromQuery(query, theFloor);
//        modifier = FLOOR_MODIFIERS[theFloor - 1];
//        return new Monster("Skeleton",(int)(100 * modifier),(int)(50 * modifier),
//            1,(int)(100 - (50 / modifier)),0,
//            0.1,0.1);
    }
    public static AbstractDungeonCharacter createGremlin(final int theFloor)
        throws SQLException {
        String query = "SELECT * FROM character WHERE CharName = 'Gremlin'";
        return createMonsterFromQuery(query, theFloor);
    }
    public static AbstractDungeonCharacter createOgre(final int theFloor) throws SQLException {
        String query = "SELECT * FROM character WHERE CharName = 'Ogre'";
        return createMonsterFromQuery(query, theFloor);
    }
    public static AbstractDungeonCharacter createOrc(final int theFloor) throws SQLException {
        String query = "SELECT * FROM character WHERE CharName = 'Orc'";
        return createMonsterFromQuery(query, theFloor);
    }
    public static AbstractDungeonCharacter createTroll(final int theFloor)
        throws SQLException {
        String query = "SELECT * FROM character WHERE CharName = 'Troll'";
        return createMonsterFromQuery(query, theFloor);
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
        String query = "SELECT * FROM character WHERE CharName = 'Dummy'";
        return createMonsterFromQuery(query, 0);
    }

    private static Monster createMonsterFromQuery(final String theQuery, final int theFloor)
        throws SQLException {
            ResultSet rs = controller.DungeonController.STATEMENT.executeQuery(theQuery);
            return new Monster(rs.getString("CharName"),

                (int) (rs.getInt("MaxHP")
                    * Math.pow(rs.getDouble("HPMultiplier"), theFloor)),

                (int) (rs.getInt("Attack")
                    * Math.pow(rs.getDouble("AttackMultiplier"), theFloor)),

                rs.getInt("AttackSpeed"),

                (int) (100 - rs.getInt("HitChance")
                    / Math.pow(rs.getDouble("HitChanceMultiplier"), theFloor)),

                rs.getInt("BlockChance"), rs.getDouble("HealMultiplier"),

                rs.getDouble("HealRate")
                    * Math.pow(rs.getDouble("HealRateMultiplier"), theFloor));
    }
}
