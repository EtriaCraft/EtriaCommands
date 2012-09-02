package Commands;

import org.bukkit.command.CommandSender;

import Main.Strings;
import Objects.Home;
import Util.CommandHandle;

public class ListhomesCmd {

	@CommandHandle(name = "listhomes", perms = "ec.listhomes")
    public static boolean listhomes(CommandSender s, String[] args) {
        String player = s.getName();
        if (args.length >= 1) {
            if (!s.hasPermission("ec.listhomes.other")) {
                s.sendMessage(Strings.NO_PERMISSION.toString());
                return true;
            }
            player = args[0];
        }

        if (!HomeCmd.homesDb.containsKey(player) || HomeCmd.homesDb.get(player).isEmpty()) {
            s.sendMessage("§cNo homes found");
            return true;
        }

        String list = "";
        for (Home home : HomeCmd.homesDb.get(player)) {
            if (!list.isEmpty()) list += "§a,§e ";
            list += home.getName();
        }

        s.sendMessage("§aHomes:§e " + list);
        s.sendMessage("§e" + HomeCmd.homesDb.get(player).size() + " §ahomes in total");
        return true;
    }
	
}
