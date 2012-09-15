package Commands;

import java.util.HashMap;
import Main.Strings;
import Util.CommandHandle;
import Util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MsgCmd {

    public static HashMap<CommandSender, CommandSender> chatterDb = new HashMap<CommandSender, CommandSender>();

    @CommandHandle(name = "msg", perms = "ec.msg", minargs = 2)
    public static boolean msg(CommandSender s, String[] args) {
        final Player r = Bukkit.getPlayer(args[0]);
        if (r == null) {
            s.sendMessage(Strings.PLAYER_OFFLINE.toString());
            return true;
        }

        final String message = Strings.buildString(args, 1, " ");
        s.sendMessage("§a[§7You§a -> §7" + r.getName() + "§a] §e" + message);
        r.sendMessage("§a[§7" + s.getName() + "§a -> §7You§a] §e" + message);
        Utils.log.info(String.format("[PM][%1$s -> %2$s] %3$s", s.getName(), r.getName(),  message));

        chatterDb.put(s, r);
        chatterDb.put(r, s);
        
        for(Player player: Bukkit.getOnlinePlayers()) {
    		if ((player.hasPermission("ec.msg.spy"))) {
            	player.sendMessage("§3[Spy]§a[§7" + s.getName() + "§a -> §7" + r.getName() + "§a] §e" + message);
            }
    }
    return true;
    }
}