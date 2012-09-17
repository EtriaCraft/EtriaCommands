package Main;

import java.util.logging.Level;

import SQLib.Database;
import SQLib.MySQL;
import SQLib.SQLite;
import Util.Utils;

public final class DBConnection {
    
    public static Database sql;
    
    public DBConnection() {
        initialize();
    }
    
    public void initialize() {
        if (Config.SQL_ENGINE.equalsIgnoreCase("mysql")) {
            sql = new MySQL(Utils.log, Strings.PREFIX.toString(), Config.MYSQL_HOST, Config.MYSQL_PORT, Config.MYSQL_USER, Config.MYSQL_PASS, Config.MYSQL_DB);
            ((MySQL) sql).open();
                    
            if (!sql.tableExists("player_homes")) {
                String query = "CREATE TABLE IF NOT EXISTS `player_homes` ("
                        + "`id` int(32) NOT NULL AUTO_INCREMENT,"
                        + "`owner` varchar(32) NOT NULL,"
                        + "`name` varchar(32) NOT NULL,"
                        + "`world` varchar(32) NOT NULL,"
                        + "`x` double(10,3) NOT NULL,"
                        + "`y` double(10,3) NOT NULL,"
                        + "`z` double(10,3) NOT NULL,"
                        + "`pitch` float(10,7) NOT NULL,"
                        + "`yaw` float(10,7) NOT NULL,"
                        + "PRIMARY KEY (`id`));";
                sql.modifyQuery(query);
            }

            if (!sql.tableExists("world_warps")) {
                String query = "CREATE TABLE IF NOT EXISTS `world_warps` ("
                        + "`id` int(11) NOT NULL AUTO_INCREMENT,"
                        + "`name` varchar(32) NOT NULL,"
                        + "`world` varchar(32) NOT NULL,"
                        + "`x` double(10,3) NOT NULL,"
                        + "`y` double(10,3) NOT NULL,"
                        + "`z` double(10,3) NOT NULL,"
                        + "`pitch` float(10,7) NOT NULL,"
                        + "`yaw` float(10,7) NOT NULL,"
                        + "PRIMARY KEY (`id`),"
                        + "UNIQUE KEY `name` (`name`));";
                sql.modifyQuery(query);
            }
        } else if (Config.SQL_ENGINE.equalsIgnoreCase("sqlite")) {
            sql = new SQLite(Utils.log, Strings.PREFIX.toString(), Config.SQLITE_DB, EtriaCommands.getInstance().getDataFolder().getAbsolutePath());
            ((SQLite) sql).open();
        
            if (!sql.tableExists("player_homes")) {
                String query = "CREATE TABLE `player_homes` ("
                        + "`owner` TEXT NOT NULL,"
                        + "`name` TEXT NOT NULL,"
                        + "`world` TEXT NOT NULL,"
                        + "`x` DOUBLE NOT NULL,"
                        + "`y` DOUBLE NOT NULL,"
                        + "`z` DOUBLE NOT NULL,"
                        + "`pitch` FLOAT NOT NULL,"
                        + "`yaw` FLOAT NOT NULL);";
                sql.modifyQuery(query);
            }

            if (!sql.tableExists("world_warps")) {
                String query = "CREATE TABLE `world_warps` ("
                        + "`name` TEXT NOT NULL UNIQUE,"
                        + "`world` TEXT NOT NULL,"
                        + "`x` DOUBLE NOT NULL,"
                        + "`y` DOUBLE NOT NULL,"
                        + "`z` DOUBLE NOT NULL,"
                        + "`pitch` FLOAT NOT NULL,"
                        + "`yaw` FLOAT NOT NULL);";
                sql.modifyQuery(query);
            }
        } else {
            Utils.log.log(Level.SEVERE, Strings.PREFIX + "Unkown value for SQL Engine, expected sqlite or mysql");
        }

    }
    
}