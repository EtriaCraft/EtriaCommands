package Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import Main.Strings;
import Util.CommandHandle;
import Util.Utils;

public class KickCmd {

    @CommandHandle(name = "kick", perms = "ec.kick", minargs = 1)
    public static boolean kick(CommandSender s, String[] args) {
        final Player p = Bukkit.getPlayer(args[0]);
        if (p == null) {
            s.sendMessage(Strings.PLAYER_OFFLINE.toString());
            return true;
        }
        
        if (p.hasPermission("ec.kick.excempt") && !s.hasPermission("ec.kick.override")) {
            s.sendMessage("§cYou're not allowed to kick that player");
            return true;
        }

        String kick_message = "Kicked!";
        if (args.length >= 2) kick_message = Strings.buildString(args, 1, " ");

        p.kickPlayer(kick_message);
        Utils.serverBroadcast("§7" + s.getName() + " §chas kicked§7 " + p.getName() + " §cfor reason \"§7" + kick_message + "§c\"", "ec.kick.alert");
        s.sendMessage("§cKicked§7 " + p.getName());
        return true;
    }

}
