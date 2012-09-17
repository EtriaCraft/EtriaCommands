package Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import Util.CommandHandle;

public class FlyCmd {
	
	@CommandHandle(name="fly", perms="ec.fly")
	public static boolean fly(CommandSender s, String[] args) {
		if (args.length < 1) {
			Player p = (Player) s;
			if (p.getAllowFlight()) p.setAllowFlight(false);
			else p.setAllowFlight(true);
			String status = (p.getAllowFlight()) ? "on" : "off";
			p.sendMessage("§aToggled flight to §e" + status + "§a.");
		
		}
		return true;
	}

}
