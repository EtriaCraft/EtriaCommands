package Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import Util.CommandHandle;

public class IsbannedCmd {

    @CommandHandle(name = "isbanned", perms = "ec.isbanned", minargs = 1)
    public static boolean isbanned(CommandSender s, String[] args) {
        s.sendMessage("§7" + args[0] + "§c is" + (Bukkit.getOfflinePlayer(args[0]).isBanned()? " banned" : " not banned"));
        return true;
    }
	
}
