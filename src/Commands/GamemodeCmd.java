package Commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import Main.Strings;
import Util.CommandHandle;

public class GamemodeCmd {
	
	@CommandHandle(name = "gamemode", perms = "ec.gamemode")
	public static boolean gm(CommandSender s, String[] args) {
		final Player p;
		if (args.length >= 1) p = Bukkit.getPlayer(args[0]);
		else {
			if (!(s instanceof Player)) return false;
			p = (Player) s;
		}
		if (p == null) {
			s.sendMessage(Strings.PLAYER_OFFLINE.toString());
			return true;
		}
		
		p.setGameMode((p.getGameMode().equals(GameMode.CREATIVE))? GameMode.SURVIVAL : GameMode.CREATIVE);
		if (s != p) s.sendMessage("§aSet§e " + p.getName() + "'s §agamemode to§e" + Strings.toTitle(p.getGameMode().name()));
		return true;
	}
	
}
