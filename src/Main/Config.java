package Main;

import org.bukkit.configuration.Configuration;

public final class Config {

    public static Configuration config;
    
    // MySQL
    public static String MYSQL_HOST;
    public static int MYSQL_PORT;
    public static String MYSQL_USER;
    public static String MYSQL_PASS;
    public static String MYSQL_DB;
    
    // Homes
    public static int GLOBAL_MAX_HOMES;
    
    public Config(EtriaCommands instance) {
        config = instance.getConfig();
        config.set("MYSQL.HOST", MYSQL_HOST = config.getString("MYSQL.HOST", "localhost"));
        config.set("MYSQL.PORT", MYSQL_PORT = config.getInt("MYSQL.PORT", 3306));
        config.set("MYSQL.USER", MYSQL_USER = config.getString("MYSQL.USER", "user"));
        config.set("MYSQL.PASS", MYSQL_PASS = config.getString("MYSQL.PASS", "pass"));
        config.set("MYSQL.DB", MYSQL_DB = config.getString("MYSQL.DB", "database"));
        config.set("Homes.Global Homes Limit", GLOBAL_MAX_HOMES = config.getInt("Homes.Global_Homes_Limit", 300));
        instance.saveConfig();
    }
}
