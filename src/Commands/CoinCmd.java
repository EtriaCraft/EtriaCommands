package Commands;

import java.util.Random;

import org.bukkit.command.CommandSender;

import Util.CommandHandle;

public class CoinCmd {
	
	@CommandHandle(name = "coin", perms = "ec.coin") 
	public static boolean random (CommandSender s, String[] args){
		Random rnd = new Random();
		int rndstuffs;
		
		for(int counter = 1; counter <= 1; counter++) {
			rndstuffs = 1+rnd.nextInt(2);
			
			if(rndstuffs == 1) {
				s.sendMessage("§aYour coin landed on §cHeads§a!");
			} else if (rndstuffs == 2) {
				s.sendMessage("§aYour coin landed on §cTails§a!");
			}
		} return true;
	}

}
