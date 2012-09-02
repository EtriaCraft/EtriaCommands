package Commands;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import Main.Strings;
import Util.CommandHandle;

public class CopyinvCmd {
	
    private static HashMap<String, ItemStack[]> invRestoreDb = new HashMap<String, ItemStack[]>();

    @CommandHandle(name = "copyinv", perms = "ec.copyinv")
    public static boolean copyinv(CommandSender s, String[] args) {
        if (!(s instanceof Player)) return false;

        if (args.length >= 1) {
            Player p = Bukkit.getPlayer(args[0]);
            if (p == null) {
                s.sendMessage(Strings.PLAYER_OFFLINE.toString());
                return true;
            }

            if (!invRestoreDb.containsKey(s.getName())) {
                invRestoreDb.put(s.getName(), ((Player) s).getInventory().getContents());
            }
            ((Player) s).getInventory().setContents(p.getInventory().getContents());
            s.sendMessage("§aCopied inventory of§e " + p.getName());
        } else {
            if (invRestoreDb.containsKey(s.getName())) {
                ((Player) s).getInventory().setContents(invRestoreDb.get(s.getName()));
                invRestoreDb.remove(s.getName());
                s.sendMessage("§aInventory restored");
            } else {
                s.sendMessage("§cYou had no inventory to restore");
            }
        }
        return true;
    }
}