package Commands;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import Main.Strings;
import Util.CommandHandle;
import Util.Utils;

public class MuteCmd {

    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static Set<String> muteDb = new HashSet();
	
	@CommandHandle(name = "mute", perms = "ec.mute")
    public static boolean mute(CommandSender s, String[] args) {
        if (args.length < 1) {
            String list = "";
            for (String str : muteDb) {
                if (!list.isEmpty()) list += "§e,§a ";
                list += str;
            }

            s.sendMessage(list.isEmpty()? "§cNo muted players" : "§aMuted players:§e " + list);
        } else {
            final Player p = Bukkit.getPlayer(args[0]);
            if (p == null) {
                s.sendMessage(Strings.PLAYER_OFFLINE.toString());
                return true;
            }
            
            if (p.hasPermission("ec.mute.excempt") && !s.hasPermission("ec.mute.override")) {
                s.sendMessage("§cYou're not allowed to kick that player");
                return true;
            }

            if (muteDb.contains(p.getName())) muteDb.remove(p.getName());
            else muteDb.add(p.getName());
            
            String action = (muteDb.contains(p.getName())? " muted§7 " : " unmuted§7 ");
            p.sendMessage("§cYou have been" + action);
            s.sendMessage("§cYou have" + action + p.getName());
            Utils.serverBroadcast("§7" + s.getName() + " §chas" + action + p.getName(), "ec.mute.alert");
        }
        return true;
    }
	
}
