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

public class CmptHomes {

    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static HashMap<String, List<Home>> homesDb = new HashMap();
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public CmptHomes() {
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

    @SuppressWarnings({ "unchecked", "rawtypes" })
	@CommandHandle(name = "sethome", perms = "ec.sethome")
    public static boolean sethome(CommandSender s, String[] args) {
        if (!(s instanceof Player)) return false;

        String homename = "home", player = s.getName();
        if (args.length >= 1) {
            if (!s.hasPermission("ec.sethome.named")) {
                s.sendMessage(Strings.NO_PERMISSION.toString());
                return true;
            }
            homename = args[0].toLowerCase();
            if (!homename.matches("^[a-zA-Z0-9\\_]+$")) {
                s.sendMessage("§cHome name invalid, please use only a-Z, 0-9, and _");
                return true;
            }
        }

        if (!homesDb.containsKey(player)) homesDb.put(player, new ArrayList());
        
        final int limit = getPlayerHomeLimit(s);
        if (homesDb.get(player).size() >= limit) {
        	s.sendMessage("§cYou have already reached your limit of &e" + limit + " &chomes.");
        	return true;
        }

        Player p = (Player) s;
        if (!homeExist(player, homename)) {
            DBConnection.sql.modifyQuery("INSERT INTO `player_homes` (`owner`, `name`, `world`, `x`, `y`,`z`, `pitch`, `yaw`) VALUES "
                    + "('"+p.getName()+ "', '"+homename+"', '"+p.getLocation().getWorld().getName()+"', "+p.getLocation().getX()+", "+p.getLocation().getY()+", "+p.getLocation().getZ()+", "+p.getLocation().getPitch()+", "+p.getLocation().getYaw()+");");
            s.sendMessage("§aYou have set home§e " + homename);
        } else {
            DBConnection.sql.modifyQuery("UPDATE `player_homes` SET `world` = '"+p.getLocation().getWorld().getName()+"', `x` = "+p.getLocation().getX()+", `y` = "+p.getLocation().getY()+", `z` = "+p.getLocation().getZ()+", `pitch` = "+p.getLocation().getPitch()+", `yaw` = "+p.getLocation().getYaw()+" WHERE"
                    + " `owner` = '"+player+"' AND `name` = '"+homename+"';");
            s.sendMessage("§aUpdated the location of§e " + homename);
            homesDb.get(player).remove(getPlayerHome(player, homename));
        }

        Home home = new Home(p.getLocation(), player, homename);
        homesDb.get(player).add(home);
        return true;
    }

    @CommandHandle(name = "listhomes", perms = "ec.listhomes")
    public static boolean listhomes(CommandSender s, String[] args) {
        String player = s.getName();
        if (args.length >= 1) {
            if (!s.hasPermission("ec.listhomes.other")) {
                s.sendMessage(Strings.NO_PERMISSION.toString());
                return true;
            }
            player = args[0];
        }

        if (!homesDb.containsKey(player) || homesDb.get(player).isEmpty()) {
            s.sendMessage("§cNo homes found");
            return true;
        }

        String list = "";
        for (Home home : homesDb.get(player)) {
            if (!list.isEmpty()) list += "§a,§e ";
            list += home.getName();
        }

        s.sendMessage("§aHomes:§e " + list);
        s.sendMessage("§e" + homesDb.get(player).size() + " §ahomes in total");
        return true;
    }

    @CommandHandle(name = "deletehome", perms = "ec.deletehome")
    public static boolean deletehome(CommandSender s, String[] args) {
        String homename = "home", player = s.getName();
        if (args.length >= 1) {
            if (args[0].contains(":")) {
                if (!s.hasPermission("ec.deletehome.other")) {
                    s.sendMessage(Strings.NO_PERMISSION.toString());
                    return true;
                }
                String[] homeinf = args[0].split(":");
                player = homeinf[0];
                homename = homeinf[1].toLowerCase();
            } else {
                if (!(s instanceof Player)) return false;
                homename = args[0].toLowerCase();
            }
        } else {
            if (!(s instanceof Player)) return false;
        }

        if (homeExist(player, homename)) {
            DBConnection.sql.modifyQuery("DELETE FROM `player_homes` WHERE `owner` = '"+player+"' AND `name` = '"+homename+"';");
            removePlayerHome(player, homename);
            s.sendMessage("§aDeleting home§e " + homename);
        } else {
            s.sendMessage("§cThat home doesn't exist");
        }
        return true;
    }

    private static boolean homeExist(String player, String homename) {
        if (!homesDb.containsKey(player)) return false;
        for(Home home : homesDb.get(player)) {
            if (home.getName().equals(homename))
                return true;
        }
        return false;
    }
    
    private static Home getPlayerHome(String player,  String homename) {
        for(Home home : homesDb.get(player)) {
            if (home.getName().equals(homename))
                return home;
        }
        return null;
    }
    
    private static void removePlayerHome(String player, String homename) {
        for (Home home : homesDb.get(player)) {
            if (home.getName().equals(homename)) {
                homesDb.get(player).remove(home);
                return;
            }
        }
    }
    
    private static int getPlayerHomeLimit(CommandSender s) {
    	if (s.isOp()) return Config.GLOBAL_MAX_HOMES;
    	int cap = 0;
    	for (int i = 0; i <= Config.GLOBAL_MAX_HOMES; i++) {
    		if (s.hasPermission("ec.sethome.limit." + i)) cap = i;
    	}
    	return cap;
    }
    
}