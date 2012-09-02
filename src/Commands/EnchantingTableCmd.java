package Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import Util.CommandHandle;

public class EnchantingTableCmd {

	@CommandHandle(name = "enchantingtable", perms = "ec.enchantingtable")
	public static boolean enchantingtable(CommandSender s, String[] args) {
		if (!(s instanceof Player)) return false;
		Player p = (Player) s;
		p.openEnchanting(null, true);
		p.sendMessage("§aEnchanting Table Opened");
		return true;
	}
}
