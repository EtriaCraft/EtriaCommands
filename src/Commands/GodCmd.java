package Commands;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import Main.Strings;
import Util.CommandHandle;

public class GodCmd {
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Set<String> godDb = new HashSet();
	
	@CommandHandle(name = "god", perms = "ec.god")
	public static boolean god (CommandSender s, String[] args) {
		final Player p;
		if (args.length >= 1) {
			if (!s.hasPermission("ec.god.other")) {
				s.sendMessage(Strings.NO_PERMISSION.toString());
				return true;
			}
			p = Bukkit.getPlayer(args[0]);
		} else {
			if (!(s instanceof Player)) return false;
			p = (Player) s;
		}
		if (p == null) {
			s.sendMessage(Strings.PLAYER_OFFLINE.toString());
			return true;
		}
		
		if (godDb.contains(p.getName())) godDb.remove(p.getName());
		else godDb.add(p.getName());
		
		final String action = (godDb.contains(p.getName()))? " enabled " : " disabled ";
		if (p == s) s.sendMessage("§aGod mode" + action);
		else {
			s.sendMessage("§aGod mode" + action + "on§e " + p.getName());
			p.sendMessage("§aGod mode" + action);
		}
		return true;
	}
	
}