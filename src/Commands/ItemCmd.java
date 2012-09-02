package Commands;

import Main.Strings;
import Util.CommandHandle;
import Util.Utils;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemCmd {
	
    @CommandHandle(name = "item", perms = "ec.item", minargs = 1)
    public static boolean item(CommandSender s, String[] args) {
    	if (!(s instanceof Player)) return false;
    	
    	ItemStack is = Utils.parseItemStack(args[0]);
    	if (is == null) {
    		s.sendMessage("§cInvalid Item Info");
    		return true;
    	}
    	
    	Player p;
    	if (args.length >= 2) p = Bukkit.getPlayer(args[1]);
    	else p = ((Player) s);
    	if (p == null) {
    		s.sendMessage(Strings.PLAYER_OFFLINE.toString());
    		return true;
    	}
    	
    	s.sendMessage("§aGiving§e " + is.getAmount() + " " + Strings.toTitle(is.getType().name()) + ((s != p)? " §ato§e " + p.getName() : ""));
    	p.getInventory().addItem(is);
    	return true;
    }
}