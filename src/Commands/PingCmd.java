package Commands;

import Util.CommandHandle;
import org.bukkit.command.CommandSender;

public class PingCmd {

    @CommandHandle(name = "ping", perms = "ec.ping")
    public static boolean ping(CommandSender s, String[] args) {
        s.sendMessage("§aPong!");
        return true;
    }

}