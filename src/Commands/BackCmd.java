package Commands;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import Util.CommandHandle;

public class BackCmd {

    public static HashMap<String, Location> backDb = new HashMap<String, Location>();

		@CommandHandle(name = "back", perms = "ec.back")
		public static boolean back(CommandSender s, String[] args) {
			if (!(s instanceof Player)) return false;
		
			if (!backDb.containsKey(s.getName())) {
					s.sendMessage("§cNowhere to go back to");
					return true;
		}
		
		((Player) s).teleport(backDb.get(s.getName()));
		s.sendMessage("§aReturned to previous teleport location");
		return true;
		}
		
}