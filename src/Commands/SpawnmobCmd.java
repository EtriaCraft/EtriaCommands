package Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import Util.CommandHandle;

public class SpawnmobCmd {

	@CommandHandle(name = "spawnmob", perms = "ec.spawnmob", minargs = 2)
    public static boolean spawnmob(CommandSender s, String[] args) {
        if (!(s instanceof Player)) return false;
        final EntityType et = EntityType.fromName(args[0]);
        if (et == null || (!et.isSpawnable() || !et.isAlive())) {
            String types = "";
            for (EntityType ett : EntityType.values()) {
                if (!ett.isAlive() || !ett.isSpawnable()) continue;
                if (!types.isEmpty()) types += "§a, ";
                types += "§e" + ett.getName();
            }
            s.sendMessage("§aValid mob types: " + types);
            return true;
        }
        
        int amount = 1;
        try {
            amount = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            return false;
        }
        
        s.sendMessage("§aSpawned§e " + amount + "§a " + et.getName());
        while (amount != 0) {
            ((Player) s).getWorld().spawnEntity(((Player) s).getLocation(), et);
            --amount;
        }
        
        return true;
    }
	
}
