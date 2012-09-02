package Commands.Trade;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import Main.EtriaCommands;

public class TradeRequest {

	private Player init;
	private Player targ;
	private int cancelTaskId;
	
	public TradeRequest(Player initiator, Player target) {
		this.init = initiator;
		this.targ = target;
		scheduleRequestCancel();
	}
	
	/*
	 * Accepts Trade requests, starts new session
	 */
	public void accept() {
		init.closeInventory();
		//Delays allow opened inventories to close
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(EtriaCommands.instance, new Runnable() {
			@Override
			public void run() {
				Inventory i = Bukkit.createInventory(init, 54, "Trade");
				init.openInventory(i);
				targ.openInventory(i);
			}
		}, 5L);
		removeRequest();
		TradeCmd.trades.put(new Pair(init, targ), new TradeSession(init, targ));
		TradeCmd.traders.put(init, targ);
	}
	
	/*
	 * Remove Request
	 */
	public void deny() {
		init.sendMessage("§7" + targ.getName() + "§cdenied your trade request");
		TradeCmd.requesters.remove(init);
		removeRequest();
	}
	
	/*
	 * Schedule a task to remove the request if not accepted within 15 seconds
	 */
	private void scheduleRequestCancel() {
		this.cancelTaskId = Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(EtriaCommands.instance, new Runnable() {
			
			@Override
			public void run() {
				targ.sendMessage("§cTrade request expired");
				init.sendMessage("§cTrade Request expired");
				removeRequest();
			}
			
		}, 600L);
	}
	
	private void unscheduleRequestCancel() {
		Bukkit.getServer().getScheduler().cancelTask(this.cancelTaskId);
	}
	
	public void removeRequest() {
		unscheduleRequestCancel();
		TradeCmd.traderequests.remove(targ);
		TradeCmd.requesters.remove(init);
	}
}
