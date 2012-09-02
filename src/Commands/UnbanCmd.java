package Commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import Util.CommandHandle;

public class UnbanCmd {

    @CommandHandle(name = "unban", perms = "ec.unban", minargs = 1)
    public static boolean unban(CommandSender s, String[] args) {
        OfflinePlayer op = Bukkit.getOfflinePlayer(args[0]);
        op.setBanned(false);
        s.sendMessage("§cYou have unbanned §e" + op.getName());
        return true;
    }
}