package Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import Util.CommandHandle;

public class WorkbenchCmd {
	
	@CommandHandle(name = "workbench", perms="ec.workbench")
	public static boolean workbench(CommandSender s, String[] args) {
			if (!(s instanceof Player)) return false;
			Player p = (Player) s;
			p.openWorkbench(null, true);
			p.sendMessage("§aHave a workbench!");
			return true;
	}
	
}