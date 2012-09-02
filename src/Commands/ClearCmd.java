package Commands;

import Main.Strings;
import Util.CommandHandle;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClearCmd {

    @CommandHandle(name = "clear", perms = "ec.clear")
    public static boolean onCommand(CommandSender s, String[] args) {
        final Player p;
        if (args.length >= 1) {
            p = Bukkit.getPlayer(args[0]);
        } else {
            if (!(s instanceof Player)) return false;
            p = (Player) s;
        }
        if (p == null) {
            s.sendMessage(Strings.PLAYER_OFFLINE.toString());
            return true;
        }

        p.getInventory().clear();
        p.sendMessage("§aInventory cleared!");
        return true;
    }
    
}