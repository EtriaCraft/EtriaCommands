package Commands;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Main.Config;
import Main.DBConnection;
import Objects.Home;
import Main.Strings;
import Util.CommandHandle;
import Util.PlayerUtils;
import Util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HomeCmd {

    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static HashMap<String, List<Home>> homesDb = new HashMap();
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public HomeCmd() {
        //Populate homesDb
        ResultSet results = DBConnection.sql.readQuery("SELECT * FROM `player_homes`;");
        int i = 0;
        try {
            while (results.next()) {
                String owner = results.getString("owner"), name = results.getString("name"), world = results.getString("world");
                World w = Bukkit.getWorld(world);
                if (w == null) continue;
                int x = results.getInt("x"), y = results.getInt("y"), z = results.getInt("z");
                float pitch = results.getFloat("pitch"), yaw = results.getFloat("yaw");
                if (!homesDb.containsKey(owner)) homesDb.put(owner, new ArrayList());
                homesDb.get(owner).add(new Home(w, x, y, z, pitch, yaw, owner, name));
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Utils.log.info(Strings.PREFIX + "Successfully loaded " + i + " homes");
    }
    

    @CommandHandle(name = "home", perms = "ec.home")
    public static boolean home(CommandSender s, String[] args) {
        if (!(s instanceof Player)) return false;

        String homename = "home", player = s.getName();
        if (args.length >= 1) {
            if (!s.hasPermission("ec.home.named")) {
                s.sendMessage(Strings.NO_PERMISSION.toString());
                return true;
            }
            if (args[0].contains(":")) {
                if (!s.hasPermission("ec.home.other")) {
                    s.sendMessage(Strings.NO_PERMISSION.toString());
                    return true;
                }
                String[] homeinf = args[0].split(":");
                player = homeinf[0];
                homename = homeinf[1].toLowerCase();
            } else {
                homename = args[0].toLowerCase();
            }
        }

        if (!homeExist(player, homename)) {
            s.sendMessage("§cThat home doesn't exist!");
            return true;
        }

        if (PlayerUtils.teleport((Player) s, getPlayerHome(player, homename).getLocation()))
        	s.sendMessage("§aGoing to:§e " + homename);
        return true;
    }

    static boolean homeExist(String player, String homename) {
        if (!homesDb.containsKey(player)) return false;
        for(Home home : homesDb.get(player)) {
            if (home.getName().equals(homename))
                return true;
        }
        return false;
    }
    
    static Home getPlayerHome(String player,  String homename) {
        for(Home home : homesDb.get(player)) {
            if (home.getName().equals(homename))
                return home;
        }
        return null;
    }
    
    static void removePlayerHome(String player, String homename) {
        for (Home home : homesDb.get(player)) {
            if (home.getName().equals(homename)) {
                homesDb.get(player).remove(home);
                return;
            }
        }
    }
    
    static int getPlayerHomeLimit(CommandSender s) {
    	if (s.isOp()) return Config.GLOBAL_MAX_HOMES;
    	int cap = 0;
    	for (int i = 0; i <= Config.GLOBAL_MAX_HOMES; i++) {
    		if (s.hasPermission("ec.sethome.limit." + i)) cap = i;
    	}
    	return cap;
    }
    
}