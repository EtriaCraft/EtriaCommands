package Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import Util.CommandHandle;
import Util.PlayerUtils;

public class SpawnCmd {

	@CommandHandle(name = "spawn", perms = "ec.spawn")
    public static boolean spawn(CommandSender s, String[] args) {
        if (!(s instanceof Player)) return false;

        PlayerUtils.teleport((Player) s, ((Player) s).getWorld().getSpawnLocation());
        s.sendMessage("§aSent to the spawn of§e " + ((Player) s).getWorld().getName());
        return true;
    }
	
}
