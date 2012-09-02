package Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import Main.DBConnection;
import Objects.Warp;
import Util.CommandHandle;

public class SetwarpCmd {

    @CommandHandle(name = "setwarp", perms = "ec.setwarp", minargs = 1)
    public static boolean setwarp(CommandSender s, String[] args) {
        if (!(s instanceof Player)) return false;

        Player p = (Player) s;
        if (!WarpCmd.warpsDb.containsKey(args[0].toLowerCase())) {
            DBConnection.sql.modifyQuery("INSERT INTO `world_warps` (`name`, `world`, `x`, `y`,`z`, `pitch`, `yaw`) VALUES "
                    + "('"+args[0].toLowerCase()+"', '"+p.getLocation().getWorld().getName()+"', "+p.getLocation().getX()+", "+p.getLocation().getY()+", "+p.getLocation().getZ()+", "+p.getLocation().getPitch()+", "+p.getLocation().getYaw()+");");
            s.sendMessage("§aYou have set warp§e " + args[0]);
        } else {
            DBConnection.sql.modifyQuery("UPDATE `world_warps` SET `world` = '"+p.getLocation().getWorld().getName()+"', `x` = "+p.getLocation().getX()+", `y` = "+p.getLocation().getY()+", `z` = "+p.getLocation().getZ()+", `pitch` = "+p.getLocation().getPitch()+", `yaw` = "+p.getLocation().getYaw()+" WHERE "
                    + "`name` = '"+args[0].toLowerCase()+"';");
            s.sendMessage("§aUpdated the location of warp§e " + args[0]);
        }

        WarpCmd.warpsDb.put(args[0].toLowerCase(), new Warp(p.getLocation(), args[0].toLowerCase()));
        return true;
    }
	
}
