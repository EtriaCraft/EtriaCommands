package Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import Main.Strings;
import Util.CommandHandle;
import Util.Utils;
import Commands.MsgCmd;

public class ReplyCmd {

    @CommandHandle(name = "reply", perms = "ec.reply", minargs = 1)
    public static boolean reply(CommandSender s, String[] args) {
        if (!MsgCmd.chatterDb.containsKey(s)) {
            s.sendMessage("§cYou have no one to reply to");
        } else {
            final CommandSender r = MsgCmd.chatterDb.get(s);
            if (!Bukkit.getOfflinePlayer(r.getName()).isOnline()) {
                s.sendMessage("§7" + r.getName() + " §cis no longer online!");
                return true;
            }
            final String message = Strings.buildString(args, 0, " ");
            
            s.sendMessage("§a[§7You§a -> §7" + r.getName() + "§a] §e" + message);
            r.sendMessage("§a[§7" + s.getName() + "§a -> §7You§a] §e" + message);
            Utils.log.info(String.format("[PM][%1$s -> %2$s] %3$s", s.getName(), r.getName(),  message));
            
            MsgCmd.chatterDb.put(r, s);
        }
        return true;
    }
    
}