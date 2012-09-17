package Commands;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import Main.DBConnection;
import Main.Strings;
import Objects.Home;
import Util.CommandHandle;

import Commands.HomeCmd;

public class SethomeCmd {

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

        if (!HomeCmd.homesDb.containsKey(player)) HomeCmd.homesDb.put(player, new ArrayList());
        
        final int limit = HomeCmd.getPlayerHomeLimit(s);
        if (HomeCmd.homesDb.get(player) != null && HomeCmd.homesDb.get(player).size() >= limit) {
        	s.sendMessage("§cYou have already reached your limit of §e" + limit + " §chomes.");
        	return true;
        }

        Player p = (Player) s;
        if (!HomeCmd.homeExist(player, homename)) {
            DBConnection.sql.modifyQuery("INSERT INTO `player_homes` (`owner`, `name`, `world`, `x`, `y`,`z`, `pitch`, `yaw`) VALUES "
                    + "('"+p.getName()+ "', '"+homename+"', '"+p.getLocation().getWorld().getName()+"', "+p.getLocation().getX()+", "+p.getLocation().getY()+", "+p.getLocation().getZ()+", "+p.getLocation().getPitch()+", "+p.getLocation().getYaw()+");");
            s.sendMessage("§aYou have set home§e " + homename);
        } else {
            DBConnection.sql.modifyQuery("UPDATE `player_homes` SET `world` = '"+p.getLocation().getWorld().getName()+"', `x` = "+p.getLocation().getX()+", `y` = "+p.getLocation().getY()+", `z` = "+p.getLocation().getZ()+", `pitch` = "+p.getLocation().getPitch()+", `yaw` = "+p.getLocation().getYaw()+" WHERE"
                    + " `owner` = '"+player+"' AND `name` = '"+homename+"';");
            s.sendMessage("§aUpdated the location of§e " + homename);
            HomeCmd.homesDb.get(player).remove(HomeCmd.getPlayerHome(player, homename));
        }

        Home home = new Home(p.getLocation(), player, homename);
        HomeCmd.homesDb.get(player).add(home);
        return true;
    }
	
}
