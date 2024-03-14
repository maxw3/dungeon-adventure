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
            "HealRateMultiplier DECIMAL(3,2) NULL," +
            "Image TEXT NOT NULL)";
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
        String mageQuery = "INSERT OR IGNORE INTO character (CharName, MaxHP, Attack, " +
            "AttackSpeed, HitChance, BlockChance, HealMultiplier, HealRate, " +
            "HPMultiplier, AttackMultiplier, HitChanceMultiplier, " +
            "BlockChanceMultiplier, HealRateMultiplier, Image) " +
            "VALUES ('Mage', 300, 70, 1, 80, 10, 0.25, null, 1.1, 1.5, 1.05, 1, null, 'Mage.png')";
        String rogueQuery = "INSERT OR IGNORE INTO character (CharName, MaxHP, Attack, " +
            "AttackSpeed, HitChance, BlockChance, HealMultiplier, HealRate, " +
            "HPMultiplier, AttackMultiplier, HitChanceMultiplier, " +
            "BlockChanceMultiplier, HealRateMultiplier, Image) " +
            "VALUES ('Rogue', 350, 30, 2, 85, 25, null, null, 1.2, 1.2, 1.1, 1.1, null, 'Rogue.png')";
        String warriorQuery = "INSERT OR IGNORE INTO character (CharName, MaxHP, Attack, " +
            "AttackSpeed, HitChance, BlockChance, HealMultiplier, HealRate, " +
            "HPMultiplier, AttackMultiplier, HitChanceMultiplier, " +
            "BlockChanceMultiplier, HealRateMultiplier, Image) " +
            "VALUES ('Warrior', 500, 50, 1, 75, 50, null, null, 1.5, 1.5, 1.1, 1.2, null, 'Warrior.png')";
        String skeletonQuery = "INSERT OR IGNORE INTO character (CharName, MaxHP, Attack, " +
            "AttackSpeed, HitChance, BlockChance, HealMultiplier, HealRate, " +
            "HPMultiplier, AttackMultiplier, HitChanceMultiplier, " +
            "BlockChanceMultiplier, HealRateMultiplier, Image) " +
            "VALUES ('Skeleton', 100, 50, 1, 50, null, 0.1, 0.1, 1.1, 1.1, 1.05, null, 1, 'skeleton.png')";
        String gremlinQuery = "INSERT OR IGNORE INTO character (CharName, MaxHP, Attack, " +
            "AttackSpeed, HitChance, BlockChance, HealMultiplier, HealRate, " +
            "HPMultiplier, AttackMultiplier, HitChanceMultiplier, " +
            "BlockChanceMultiplier, HealRateMultiplier, Image) " +
            "VALUES ('Gremlin', 100, 30, 2, 40, null, 0.25, 0.1, 1.05, 1.1, 1.1, null, 1.1, 'gremlin.png')";
        String ogreQuery = "INSERT OR IGNORE INTO character (CharName, MaxHP, Attack, " +
            "AttackSpeed, HitChance, BlockChance, HealMultiplier, HealRate, " +
            "HPMultiplier, AttackMultiplier, HitChanceMultiplier, " +
            "BlockChanceMultiplier, HealRateMultiplier, Image) " +
            "VALUES ('Ogre', 250, 80, 1, 80, null, 0.25, 0.2, 1.5, 1.3, 1.05, null, 1.1, 'ogre.png')";
        String orcQuery = "INSERT OR IGNORE INTO character (CharName, MaxHP, Attack, " +
            "AttackSpeed, HitChance, BlockChance, HealMultiplier, HealRate, " +
            "HPMultiplier, AttackMultiplier, HitChanceMultiplier, " +
            "BlockChanceMultiplier, HealRateMultiplier, Image) " +
            "VALUES ('Orc', 150, 75, 1, 60, null, 0.2, 0.25, 1.3, 1.5, 1.1, null, 1.2, 'orc.png')";
        String trollQuery = "INSERT OR IGNORE INTO character (CharName, MaxHP, Attack, " +
            "AttackSpeed, HitChance, BlockChance, HealMultiplier, HealRate, " +
            "HPMultiplier, AttackMultiplier, HitChanceMultiplier, " +
            "BlockChanceMultiplier, HealRateMultiplier, Image) " +
            "VALUES ('Troll', 200, 60, 1, 60, null, 0.1, 0.5, 1.2, 1.3, 1.2, null, 1.2, 'troll.png')";
        String slimeQuery = "INSERT OR IGNORE INTO character (CharName, MaxHP, Attack, " +
            "AttackSpeed, HitChance, BlockChance, HealMultiplier, HealRate, " +
            "HPMultiplier, AttackMultiplier, HitChanceMultiplier, " +
            "BlockChanceMultiplier, HealRateMultiplier, Image) " +
            "VALUES ('Slime', 1000, 10, 1, 50, null, 0.1, 0.3, 1, 1, 1, null, null, 'Slime.png')";
        String minotaurQuery = "INSERT OR IGNORE INTO character (CharName, MaxHP, Attack, " +
            "AttackSpeed, HitChance, BlockChance, HealMultiplier, HealRate, " +
            "HPMultiplier, AttackMultiplier, HitChanceMultiplier, " +
            "BlockChanceMultiplier, HealRateMultiplier, Image) " +
            "VALUES ('Minotaur', 1000, 150, 1, 80, null, 0.05, 0.1, 1, 1, 1, null, null, 'Minotaur.png')";
        String griffonQuery = "INSERT OR IGNORE INTO character (CharName, MaxHP, Attack, " +
            "AttackSpeed, HitChance, BlockChance, HealMultiplier, HealRate, " +
            "HPMultiplier, AttackMultiplier, HitChanceMultiplier, " +
            "BlockChanceMultiplier, HealRateMultiplier, Image) " +
            "VALUES ('Griffon', 1200, 100, 1, 10, null, 0.1, 0.2, 1, 1, 1, null, null, 'Griffon.png')";
        String ratKingQuery = "INSERT OR IGNORE INTO character (CharName, MaxHP, Attack, " +
            "AttackSpeed, HitChance, BlockChance, HealMultiplier, HealRate, " +
            "HPMultiplier, AttackMultiplier, HitChanceMultiplier, " +
            "BlockChanceMultiplier, HealRateMultiplier, Image) " +
            "VALUES ('Rat King', 2000, 20, 10, 20, null, 0, 0, 1, 1, 1, null, null, 'Rat King.png')";
        String hydraQuery = "INSERT OR IGNORE INTO character (CharName, MaxHP, Attack, " +
            "AttackSpeed, HitChance, BlockChance, HealMultiplier, HealRate, " +
            "HPMultiplier, AttackMultiplier, HitChanceMultiplier, " +
            "BlockChanceMultiplier, HealRateMultiplier, Image) " +
            "VALUES ('Hydra', 2000, 75, 3, 20, null, 0.1, 0.8, 1, 1, 1, null, null, 'Hydra.png')";
        String dummyQuery = "INSERT OR IGNORE INTO character (CharName, MaxHP, Attack, " +
            "AttackSpeed, HitChance, BlockChance, HealMultiplier, HealRate, " +
            "HPMultiplier, AttackMultiplier, HitChanceMultiplier, " +
            "BlockChanceMultiplier, HealRateMultiplier, Image) " +
            "VALUES ('Dummy', 1000, 0, 0, 0, null, 1, 1, 1, 1, 1, null, null, 'Dummy.png')";

        myStatement.executeUpdate( mageQuery );
        myStatement.executeUpdate( rogueQuery );
        myStatement.executeUpdate( warriorQuery );
        myStatement.executeUpdate( skeletonQuery );
        myStatement.executeUpdate( gremlinQuery );
        myStatement.executeUpdate( ogreQuery );
        myStatement.executeUpdate( orcQuery );
        myStatement.executeUpdate( trollQuery );
        myStatement.executeUpdate( slimeQuery );
        myStatement.executeUpdate( minotaurQuery );
        myStatement.executeUpdate( griffonQuery );
        myStatement.executeUpdate( ratKingQuery );
        myStatement.executeUpdate( hydraQuery );
        myStatement.executeUpdate( dummyQuery );
    }

}
