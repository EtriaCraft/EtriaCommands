package Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import Util.CommandHandle;

public class ConfuseCmd {
	
	@CommandHandle(name = "confuse", perms = "ec.blind")
	public static boolean blind(CommandSender s, String[] args) {
			((Player) s).addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 300, 0));
		return true;
	}

}
