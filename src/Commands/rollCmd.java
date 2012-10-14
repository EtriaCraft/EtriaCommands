package Commands;

import java.util.Random;

import org.bukkit.command.CommandSender;

import Util.CommandHandle;

public class rollCmd {
	
	@CommandHandle(name = "roll", perms = "ec.roll")
	public static boolean onCommand(CommandSender s, String[] args) {
		Random r = new Random();
		int min = 1;
		int max = 6;
		
		int i1 = r.nextInt(max - min + 1) + min;
		s.sendMessage("§aYou rolled a §e" + i1);
		return true;
	}
}
