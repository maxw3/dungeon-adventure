package model.Database;

import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public final class SQLite {
    private static SQLiteDataSource myDS;
    private static Connection myConn;
    private static Statement myStatement;
    public static void main(final String[] theArgs) throws SQLException {
        createDB();
        createCharTable();
        createCharData();
        myStatement.close();
        myConn.close();
    }

    private static void createDB() {
        try{
            myDS = new SQLiteDataSource();
            myDS.setUrl("jdbc:sqlite:dungeonData.sqlite");
        } catch(Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    private static void createCharTable() {
        String query = "CREATE TABLE IF NOT EXISTS character (" +
            "CharName TEXT NOT NULL PRIMARY KEY, " +
            "MaxHP INTEGER NOT NULL, " +
            "Attack INTEGER NOT NULL, " +
            "AttackSpeed INTEGER NOT NULL, " +
            "HitChance INTEGER NOT NULL, " +
            "BlockChance INTEGER NULL, " +
            "HealMultiplier DECIMAL(3,2) NULL," +
            "HealRate DECIMAL(3,2) NULL," +
            "HPMultiplier DECIMAL(3,2) NOT NULL," +
            "AttackMultiplier DECIMAL(3,2) NOT NULL," +
            "HitChanceMultiplier DECIMAL(3,2) NOT NULL," +
            "BlockChanceMultiplier DECIMAL(3,2) NULL," +
            "HealRateMultiplier DECIMAL(3,2) NULL)";
        try {
            myConn = myDS.getConnection();
            myStatement = myConn.createStatement();
            myStatement.executeUpdate(query);
        } catch (SQLException e){
            e.printStackTrace();
            System.exit(0);
        }
    }

    private static void createCharData() throws SQLException {
        String mageQuery = "INSERT INTO character (CharName, MaxHP, Attack, " +
            "AttackSpeed, HitChance, BlockChance, HealMultiplier, HealRate, " +
            "HPMultiplier, AttackMultiplier, HitChanceMultiplier, " +
            "BlockChanceMultiplier, HealRateMultiplier) " +
            "VALUES ('Mage', 300, 70, 1, 80, 10, 0.25, null, 1.1, 1.5, 1.05, 1, null)";
        String rogueQuery = "INSERT INTO character (CharName, MaxHP, Attack, " +
            "AttackSpeed, HitChance, BlockChance, HealMultiplier, HealRate, " +
            "HPMultiplier, AttackMultiplier, HitChanceMultiplier, " +
            "BlockChanceMultiplier, HealRateMultiplier) " +
            "VALUES ('Rogue', 350, 30, 2, 85, 25, null, null, 1.2, 1.2, 1.1, 1.1, null)";
        String warriorQuery = "INSERT INTO character (CharName, MaxHP, Attack, " +
            "AttackSpeed, HitChance, BlockChance, HealMultiplier, HealRate, " +
            "HPMultiplier, AttackMultiplier, HitChanceMultiplier, " +
            "BlockChanceMultiplier, HealRateMultiplier) " +
            "VALUES ('Warrior', 500, 50, 1, 75, 50, null, null, 1.5, 1.5, 1.1, 1.2, null)";
        String skeletonQuery = "INSERT INTO character (CharName, MaxHP, Attack, " +
            "AttackSpeed, HitChance, BlockChance, HealMultiplier, HealRate, " +
            "HPMultiplier, AttackMultiplier, HitChanceMultiplier, " +
            "BlockChanceMultiplier, HealRateMultiplier) " +
            "VALUES ('Skeleton', 100, 50, 1, 50, null, 0.1, 0.1, 1.1, 1.1, 1.05, null, 1)";
        String gremlinQuery = "INSERT INTO character (CharName, MaxHP, Attack, " +
            "AttackSpeed, HitChance, BlockChance, HealMultiplier, HealRate, " +
            "HPMultiplier, AttackMultiplier, HitChanceMultiplier, " +
            "BlockChanceMultiplier, HealRateMultiplier) " +
            "VALUES ('Gremlin', 100, 30, 2, 40, null, 0.25, 0.1, 1.05, 1.1, 1.1, null, 1.1)";
        String ogreQuery = "INSERT INTO character (CharName, MaxHP, Attack, " +
            "AttackSpeed, HitChance, BlockChance, HealMultiplier, HealRate, " +
            "HPMultiplier, AttackMultiplier, HitChanceMultiplier, " +
            "BlockChanceMultiplier, HealRateMultiplier) " +
            "VALUES ('Ogre', 250, 80, 1, 80, null, 0.25, 0.2, 1.5, 1.3, 1.05, null, 1.1)";
        String orcQuery = "INSERT INTO character (CharName, MaxHP, Attack, " +
            "AttackSpeed, HitChance, BlockChance, HealMultiplier, HealRate, " +
            "HPMultiplier, AttackMultiplier, HitChanceMultiplier, " +
            "BlockChanceMultiplier, HealRateMultiplier) " +
            "VALUES ('Orc', 150, 75, 1, 60, null, 0.2, 0.25, 1.3, 1.5, 1.1, null, 1.2)";
        String trollQuery = "INSERT INTO character (CharName, MaxHP, Attack, " +
            "AttackSpeed, HitChance, BlockChance, HealMultiplier, HealRate, " +
            "HPMultiplier, AttackMultiplier, HitChanceMultiplier, " +
            "BlockChanceMultiplier, HealRateMultiplier) " +
            "VALUES ('Troll', 200, 60, 1, 60, null, 0.1, 0.5, 1.2, 1.3, 1.2, null, 1.2)";
        String dummyQuery = "INSERT INTO character (CharName, MaxHP, Attack, " +
            "AttackSpeed, HitChance, BlockChance, HealMultiplier, HealRate, " +
            "HPMultiplier, AttackMultiplier, HitChanceMultiplier, " +
            "BlockChanceMultiplier, HealRateMultiplier) " +
            "VALUES ('Dummy', 1000, 0, 0, 0, null, 1, 1, 1, 1, 1, null, null)";

        myStatement.executeUpdate( mageQuery );
        myStatement.executeUpdate( rogueQuery );
        myStatement.executeUpdate( warriorQuery );
        myStatement.executeUpdate( skeletonQuery );
        myStatement.executeUpdate( gremlinQuery );
        myStatement.executeUpdate( ogreQuery );
        myStatement.executeUpdate( orcQuery );
        myStatement.executeUpdate( trollQuery );
        myStatement.executeUpdate( dummyQuery );
    }

}
