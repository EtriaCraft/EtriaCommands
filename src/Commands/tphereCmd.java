package Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import Main.Strings;
import Util.CommandHandle;
import Util.PlayerUtils;

public class tphereCmd {

	@CommandHandle(name = "tphere", perms = "ec.tphere", minargs = 1)
    public static boolean bring(CommandSender s, String[] args) {
        Player p = Bukkit.getPlayer(args[0]);
        if (p == null) s.sendMessage(Strings.PLAYER_OFFLINE.toString());
        else {
            PlayerUtils.teleport(p, ((Player) s).getLocation());
            p.sendMessage("§aSummoned by§e " + s.getName());
            s.sendMessage("§aYou brought§e " + p.getName());
        }
        return true;                                                                                                                               
    }
	
}
