package Util;

import Commands.BackCmd;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class PlayerUtils {
    
	public static boolean teleport(Player p, Location loc) {
        BackCmd.backDb.put(p.getName(), p.getLocation());
        return p.teleport(loc);
    }

}
