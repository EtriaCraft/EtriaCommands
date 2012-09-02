package Main;

import SQLib.MySQL;
import Util.Utils;

public final class DBConnection {
    
    public static MySQL sql;
    
    public DBConnection() {
        initialize();
    }
    
    public void initialize() {
        sql = new MySQL(Utils.log, Strings.PREFIX.toString(), Config.MYSQL_HOST, Config.MYSQL_PORT, Config.MYSQL_USER, Config.MYSQL_PASS, Config.MYSQL_DB);
        sql.open();

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
    }
    
}
