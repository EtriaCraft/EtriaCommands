package Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import Main.Strings;
import Util.CommandHandle;

public class RepairCmd {

	@CommandHandle(name = "repair", perms = "ec.repair")
	public static boolean repair(CommandSender s, String[] args) {
		if (!(s instanceof Player)) return false;
		if (!s.hasPermission("ec.repair")) {
			s.sendMessage(Strings.NO_PERMISSION.toString());
			return true;
		}
		
		((Player) s).getItemInHand().setDurability((short) 0);
		s.sendMessage("§aYour§e " + Strings.toTitle(((Player) s).getItemInHand().getType().name()) + " §awas repaired!");
		return true;
		}
	
}