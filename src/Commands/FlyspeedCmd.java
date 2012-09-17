package Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import Util.CommandHandle;

public class FlyspeedCmd {
	
	@CommandHandle(name="flyspeed", perms="ec.flyspeed")
	public static boolean flyspeed(CommandSender s, String[] args) {
		Player p = (Player) s;
		float flySpeed;
		try {
			flySpeed = Float.valueOf(args[0]);
		} catch (NumberFormatException e) {
			s.sendMessage("§cPlease enter a valid number.");
			return true;
		}
		if (flySpeed < -1F || flySpeed > 1F) {
			s.sendMessage("§cSpeed must be between -1 and 1.");
			return true;
		}
		s.sendMessage("§aSet your fly speed to §7" + flySpeed + "§a.");
		p.setFlySpeed(flySpeed);
		return true;
	}

}
