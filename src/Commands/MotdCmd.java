package Commands;

import org.bukkit.command.CommandSender;

import Main.Config;
import Util.CommandHandle;

public class MotdCmd {

	@CommandHandle(name = "motd", perms = "ec.motd")
    public static boolean motd(CommandSender s, String[] args) {
        for (String mess : Config.MOTD) {
            mess = mess.replace("<name>", s.getName());
            mess = mess.replaceAll("(?i)&([a-fk-or0-9])", "\u00A7$1").replaceAll("<name>", s.getName());
            s.sendMessage(mess);
        }
        return true;
    }
    
}