package Commands;

import Util.CommandHandle;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GetposCmd {
    
    @CommandHandle(name = "getpos", perms = "ec.getpos")
    public static boolean getpos(CommandSender s, String[] args) {
        if (!(s instanceof Player)) return false;

        Location loc = ((Player) s).getLocation();

        s.sendMessage("§aYour position:");
        s.sendMessage("§aWorld:§e " + loc.getWorld().getName());
        s.sendMessage(String.format("§aCoords: X:§e %1$s §aY:§e %2$s §aZ:§e %3$s", loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()));
        return true;
    }

}