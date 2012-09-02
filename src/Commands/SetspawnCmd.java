package Commands;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import Util.CommandHandle;

public class SetspawnCmd {

    @CommandHandle(name = "setspawn", perms = "ec.setspawn")
    public static boolean setspawn(CommandSender s, String[] args) {
        if (!(s instanceof Player)) return false;
        final Location newspawn = ((Player) s).getLocation();
        newspawn.getWorld().setSpawnLocation(newspawn.getBlockX(), newspawn.getBlockY(), newspawn.getBlockZ());
        s.sendMessage("§aSet spawn of§e " + newspawn.getWorld().getName());
        return true;
    }
    
}