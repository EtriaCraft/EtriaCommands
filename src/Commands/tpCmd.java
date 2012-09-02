package Commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import Main.Strings;
import Util.CommandHandle;
import Util.PlayerUtils;

public class tpCmd {

	@CommandHandle(name = "tp", perms = "ec.tp", minargs = 1)
    public static boolean tp(CommandSender s, String[] args) {
        Location loc;
        if (args.length >= 3) {//Teleporting to coordinates
            try {
                double x = Double.parseDouble(args[0]);
                double y = Double.parseDouble(args[1]);
                double z = Double.parseDouble(args[2]);
                World w = (args.length >= 4)? Bukkit.getWorld(args[3]) : ((Player) s).getWorld();
                if (w == null) {
                    s.sendMessage("§cThat world doesn't exist");
                    return true;
                }
                loc = new Location(w, x, y, z);
                s.sendMessage(String.format("§aTeleporting to: X:§e %1$s §aY:§e %2$s §aZ:§e %3$s§a in§e %4$s", x, y, z, w.getName()));
            } catch (NumberFormatException e) {
                s.sendMessage("Invalid coordinates");
                return true;
            }
        } else {//Teleporting to player
            Player p = Bukkit.getPlayer(args[0]);
            if (p == null) {
                s.sendMessage(Strings.PLAYER_OFFLINE.toString());
                return true;
            }
            loc = p.getLocation();
            s.sendMessage("§aTeleporting to§e " + p.getName());
        }

        PlayerUtils.teleport((Player) s, loc);
        return true;
    }

}
