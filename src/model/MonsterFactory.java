package model;

import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

public final class MonsterFactory {
    private static final double FLOOR_1_MODIFIER = 1.0;
    private static final double FLOOR_2_MODIFIER = 1.25;
    private static final double FLOOR_3_MODIFIER = 1.5;
    private static final double FLOOR_4_MODIFIER = 1.75;
    private static final double FLOOR_5_MODIFIER = 2;
    private static final double[] FLOOR_MODIFIERS = {FLOOR_1_MODIFIER, FLOOR_2_MODIFIER, FLOOR_3_MODIFIER, FLOOR_4_MODIFIER, FLOOR_5_MODIFIER};
    private static double modifier;

    private static final Random RANDOM = new Random();
    private static final int NUM_OF_MONSTER_TYPES = 5;
    private MonsterFactory() { throw new IllegalStateException("Utility Class"); }

    public static AbstractDungeonCharacter createSkeleton(final int theFloor) {
        String query = "SELECT * FROM character WHERE CharName = 'Skeleton'";
        return createMonsterFromQuery(query, theFloor);
//        modifier = FLOOR_MODIFIERS[theFloor - 1];
//        return new Monster("Skeleton",(int)(100 * modifier),(int)(50 * modifier),
//            1,(int)(100 - (50 / modifier)),0,
//            0.1,0.1);
    }
    public static AbstractDungeonCharacter createGremlin(final int theFloor) {
        modifier = FLOOR_MODIFIERS[theFloor - 1];
        return new Monster("Gremlin",(int)(100 * modifier),(int)(30 * modifier),
            2,(int)(100 - (40 / modifier)),0,
            0.25,0.1);
    }
    public static AbstractDungeonCharacter createOgre(final int theFloor) {
        modifier = FLOOR_MODIFIERS[theFloor - 1];
        return new Monster("Ogre",(int)(250 * modifier),(int)(80 * modifier),
            1,(int)(100 - (80 / modifier)),0,
            0.25,0.2);
    }
    public static AbstractDungeonCharacter createOrc(final int theFloor) {
        modifier = FLOOR_MODIFIERS[theFloor - 1];
        return new Monster("Orc",(int)(150 * modifier),(int)(75 * modifier),
            1,(int)(100 - (60 / modifier)),0,
            0.2,0.25);
    }
    public static AbstractDungeonCharacter createTroll(final int theFloor) {
        modifier = FLOOR_MODIFIERS[theFloor - 1];
        return new Monster("Troll",(int)(200 * modifier),(int)(60 * modifier),
            1,(int)(100 - (60 / modifier)),0,
            0.1,0.5);
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
    public static AbstractDungeonCharacter createDummy() {
        return new Monster("Dummy",1,AbstractDungeonCharacter.MIN_STAT,
            AbstractDungeonCharacter.MIN_STAT, AbstractDungeonCharacter.MIN_STAT,
            AbstractDungeonCharacter.MIN_STAT,AbstractDungeonCharacter.MIN_STAT,
            AbstractDungeonCharacter.MIN_STAT);
    }

    private static Monster createMonsterFromQuery(final String theQuery, final int theFloor) {
        SQLiteDataSource ds = null;
        try {
            ds = new SQLiteDataSource();
            ds.setUrl("jdbc:sqlite:dungeonData.sqlite");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
        try (Connection conn = ds.getConnection();
             Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery(theQuery);
            return new Monster(rs.getString("CharName"),

                (int) (rs.getInt("MaxHP")
                    * rs.getDouble("HPMultiplier") * theFloor),

                (int) (rs.getInt("Attack")
                    * rs.getDouble("AttackMultiplier") * theFloor),

                rs.getInt("AttackSpeed"),

                (int) (100 - (rs.getInt("HitChance")
                    / (rs.getDouble("HitChanceMultiplier") * theFloor))),

                rs.getInt("BlockChance"), rs.getDouble("HealMultiplier"),

                rs.getDouble("HealRate")
                    * rs.getDouble("HealRateMultiplier") * theFloor);

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
        return null;
    }
}
