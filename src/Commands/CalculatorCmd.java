package Commands;

import org.bukkit.command.CommandSender;

import Main.Strings;
import Util.CommandHandle;

public class CalculatorCmd {
	
	@CommandHandle(name="calculator", perms="ec.calculator") 
	public static boolean calculator(CommandSender s, String[] args) {
		if (args.length == 0) {
			s.sendMessage(Strings.NOT_ENOUGH_ARGS.toString());
		} else {
			int n1 = Integer.parseInt(args[0]);
			int n2 = Integer.parseInt(args[2]);
			int ans = 0;
			if (args[1].equals("+")) {
				ans = n1 + n2;
			}
			if (args[1].equals("-")) {
				ans = n1-n2;
			}
			if (args[1].equals("/")) {
				ans = n1 / n2;
			}
			if (args[1].equals("*")){
				ans = n2 * n2;
			}
			
			s.sendMessage("§3Answer: §a" + ans);
		}
		return true;
		}
	}