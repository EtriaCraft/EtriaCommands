package Main;

import org.bukkit.configuration.Configuration;

public final class Config {

    public static Configuration config;
    
    // MySQL
    public static String SQL_ENGINE;
    public static String SQLITE_DB;
    public static String MYSQL_HOST;
    public static int MYSQL_PORT;
    public static String MYSQL_USER;
    public static String MYSQL_PASS;
    public static String MYSQL_DB;
    
    // Homes
    public static int GLOBAL_MAX_HOMES;
    
    public Config(EtriaCommands instance) {
        config = instance.getConfig();
        config.set("SQL.ENGINE", SQL_ENGINE = config.getString("SQL.ENGINE", "sqlite"));
        config.set("SQL.SQLITE.DB", SQLITE_DB = config.getString("SQL.SQLITE.DB", "etriacommands.db"));
        config.set("SQL.MYSQL.HOST", MYSQL_HOST = config.getString("MYSQL.HOST", "localhost"));
        config.set("SQL.MYSQL.PORT", MYSQL_PORT = config.getInt("MYSQL.PORT", 3306));
        config.set("SQL.MYSQL.USER", MYSQL_USER = config.getString("MYSQL.USER", "user"));
        config.set("SQL.MYSQL.PASS", MYSQL_PASS = config.getString("MYSQL.PASS", "pass"));
        config.set("SQL.MYSQL.DB", MYSQL_DB = config.getString("MYSQL.DB", "database"));
        config.set("Homes.Global Homes Limit", GLOBAL_MAX_HOMES = config.getInt("Homes.Global_Homes_Limit", 300));
        instance.saveConfig();
    }
}
