package Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import Main.Strings;
import Util.CommandHandle;

public class HealCmd {
	
	@CommandHandle(name = "heal", perms = "ec.heal")
    public static boolean heal(CommandSender s, String[] args) {
        final Player p;
        
        if (args.length >= 1) p = Bukkit.getPlayer(args[0]);
        else {
            if (!(s instanceof Player)) return false;
            p = (Player) s;
        }
        if (p == null) {
            s.sendMessage(Strings.PLAYER_OFFLINE.toString());
            return true;
        }
        p.setHealth(20);
        p.setFoodLevel(20);
        p.setFireTicks(0);

        if (s == p) s.sendMessage("§aHealed");
        else {
            s.sendMessage("§aHealed§e " + p.getName());
        }
        return true;
    }

}