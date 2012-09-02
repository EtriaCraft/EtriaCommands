package Util;

import org.bukkit.plugin.RegisteredServiceProvider;

import net.milkbowl.vault.chat.Chat;
import Listeners.ChatListener;
import Main.EtriaCommands;

public class ChatProv {

	public static Chat sp;
	
	EtriaCommands plugin;
	
	public ChatProv(EtriaCommands instance) {
		this.plugin = instance;
		plugin.getServer().getPluginManager().registerEvents(new ChatListener(), plugin);
		setupChat();
	}
	
	private boolean setupChat() {
		RegisteredServiceProvider<Chat> chatRsp = plugin.getServer().getServicesManager().getRegistration(Chat.class);
		sp = chatRsp.getProvider();
		return (sp != null);
	}
	
}
