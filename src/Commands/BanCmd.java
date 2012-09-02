package Commands;

import java.util.*;
import Main.Strings;
import Util.CommandHandle;
import Util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BanCmd {
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static HashMap<String, String> banConfirmDb = new HashMap();
    
    @CommandHandle(name = "ban", perms = "ec.ban", minargs = 1)
    public static boolean ban(CommandSender s, String[] args) {
        OfflinePlayer op = Bukkit.getOfflinePlayer(args[0]);

        if (args[0].equalsIgnoreCase("confirm")) {
            if (!banConfirmDb.containsKey(s.getName())) {
                s.sendMessage("§cNo bans awaiting confirmation");
                return true;
            }

            String kick_message = "Banned!";
            if (args.length >= 2) kick_message = Strings.buildString(args, 1, " ");

            op = Bukkit.getOfflinePlayer(banConfirmDb.get(s.getName()));
            if (!op.isBanned()) {
                if (op.isOnline()) {
                    final Player p = Bukkit.getPlayer(op.getName());
                    if (p.hasPermission("ec.ban.exempt") && !s.hasPermission("ec.ban.override")) {
                        s.sendMessage("§cYou're not allowed to ban that player");
                        return true;
                    }
                    p.kickPlayer(kick_message);
                }
            }
            
            op.setBanned(!op.isBanned());
            final String action = (op.isBanned())? " banned§7 " : " unbanned§7 ";
            s.sendMessage("§cYou have" + action + op.getName());
            Utils.serverBroadcast("§7" + s.getName() + " §chas" + action + op.getName(), "ec.ban.alert");
            banConfirmDb.remove(s.getName());
        } else if (args[0].equalsIgnoreCase("cancel")) {
            if (!banConfirmDb.containsKey(s.getName())) {
                s.sendMessage("You have no bans to confirm");
                return true;
            }

            s.sendMessage("§aBan cancelled");
            banConfirmDb.remove(s.getName());
        } else {
            final String action = (op.isBanned())? "unban" : "ban";
            s.sendMessage("§aType §e/ban confirm§a to " + action + " §e" + op.getName() + " §aor§e /ban cancel§a to cancel");
            banConfirmDb.put(s.getName(), op.getName());
        }
        return true;
    }
}