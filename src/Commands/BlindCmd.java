package Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import Util.CommandHandle;

public class BlindCmd {
	
	@CommandHandle(name = "blind", perms = "ec.blind")
	public static boolean blind(CommandSender s, String[] args) {
			((Player) s).addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 300, 0));
		return true;
	}

}
