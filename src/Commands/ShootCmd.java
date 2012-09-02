package Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import Util.CommandHandle;
import Util.Utils;

public class ShootCmd {
	
	@CommandHandle(name = "shoot", perms = "ec.shoot")
	public static boolean shoot(CommandSender s, String[] args) {
		final Player victim = Bukkit.getPlayer(args[0]);
		if (victim == null) {
			s.sendMessage("§cThat player is not online");
			return true;
		}
		Utils.serverBroadcast("§2" + s.getName() + "§4 has shot " + "§2" + victim.getName() + "§4. <3!", null);
		return false;
	}
	
}