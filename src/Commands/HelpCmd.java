package Commands;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;

import Main.EtriaCommands;
import Main.Strings;
import Util.CommandHandle;
import Util.Utils;

public class HelpCmd {
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static HashMap<String, List<String>> helpPagesDb = new HashMap();
	
	public HelpCmd() {
		File helps = new File(EtriaCommands.getInstance().getDataFolder(), "helps.yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(helps);
		
		if (!helps.exists()) {
			config.set("default", new String[] {"&eYou can change this and add your own content to the default page"});
			try { config.save(helps); } catch (IOException e) { e.printStackTrace(); }
		}
		
		for (String key : config.getKeys(true)) {
			helpPagesDb.put(key.toLowerCase(), config.getStringList(key));
		}
		Utils.log.info(Strings.PREFIX + "Successfully loaded " + helpPagesDb.size() + " help pages");
	}
	
	@CommandHandle(name = "help", perms = "ec.help")
	public static boolean help(CommandSender s, String[] args) {
		String page = "default";
		if (args.length >= 1)
			page = args[0].toLowerCase();
		
		if (helpPagesDb.containsKey(page)) {
			for (String sent : helpPagesDb.get(page)) {
				s.sendMessage(sent.replaceAll("(?i)&([a-fk-or0-9])", "\u00A7$1"));
		}
	} else s.sendMessage("§cThat page doesn't exist!");
	return true;

}

}
