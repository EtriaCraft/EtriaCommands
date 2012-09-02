package Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import Main.DBConnection;
import Main.Strings;
import Util.CommandHandle;

public class DeletehomeCmd {

	@CommandHandle(name = "deletehome", perms = "ec.deletehome")
    public static boolean deletehome(CommandSender s, String[] args) {
        String homename = "home", player = s.getName();
        if (args.length >= 1) {
            if (args[0].contains(":")) {
                if (!s.hasPermission("ec.deletehome.other")) {
                    s.sendMessage(Strings.NO_PERMISSION.toString());
                    return true;
                }
                String[] homeinf = args[0].split(":");
                player = homeinf[0];
                homename = homeinf[1].toLowerCase();
            } else {
                if (!(s instanceof Player)) return false;
                homename = args[0].toLowerCase();
            }
        } else {
            if (!(s instanceof Player)) return false;
        }

        if (HomeCmd.homeExist(player, homename)) {
            DBConnection.sql.modifyQuery("DELETE FROM `player_homes` WHERE `owner` = '"+player+"' AND `name` = '"+homename+"';");
            HomeCmd.removePlayerHome(player, homename);
            s.sendMessage("§aDeleting home§e " + homename);
        } else {
            s.sendMessage("§cThat home doesn't exist");
        }
        return true;
    }
	
}
