package Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;

import Commands.Trade.Pair;
import Commands.Trade.TradeCmd;
import Commands.Trade.TradeSession;

public class TradeInventoryListener implements Listener {
    
    public final String NOT_ENOUGH_SPACE = "§cNot enough inventory space in one trader's inventory, cancelled";
    public final String TRADE_EMPTY = "§cTrade window was left empty, cancelling trade";
    
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (!TradeCmd.isTrading((Player) e.getWhoClicked())) return;
        if (!e.getInventory().getTitle().equals("Trade")) return;
        if (e.isShiftClick()) e.setCancelled(true);
        Player p = (Player) e.getWhoClicked();
        
        if (TradeCmd.getInit(p).equals(p)) {
            if (e.getRawSlot() >= 27 && e.getRawSlot() <= 53) {
                p.sendMessage("§cNot your trade slot");
                e.setCancelled(true);
            }
        } else {
            if (e.getRawSlot() >= 0 && e.getRawSlot() <= 26) {
                p.sendMessage("§cNot your trade slot");
                e.setCancelled(true);
            }
        }
    }
    
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        if (e.getPlayer().getOpenInventory().getType().equals(InventoryType.CRAFTING)) return;
        if (!e.getInventory().getTitle().equals("Trade")) return;
        if (!TradeCmd.isTrading((Player) e.getPlayer())) return;
        
        Player p = (Player) e.getPlayer();
        
        Player init = TradeCmd.getInit(p);
        Player targ = TradeCmd.getTarget(p);

        //Detect who has closed inventory, close other
        if (init.equals(p)) {
            targ.closeInventory();
        } else {
            init.closeInventory();
        }
        
        TradeSession ts = TradeCmd.trades.get(new Pair(init, targ));
        if (ts.isTradeSet()) return;
        ts.setInventory(e.getInventory());
        
        //Check that inventory is not empty
        if (TradeCmd.isEmpty(e.getInventory())) {
            init.sendMessage(TRADE_EMPTY);
            targ.sendMessage(TRADE_EMPTY);
            ts.abort();
            return;
        }
        
        //Check that both players have enough space in their inventory
        if ((TradeCmd.getEmptySlots(init.getInventory().getContents()) < TradeCmd.getFullSlots(TradeCmd.getLowerContents(e.getInventory())))
                || TradeCmd.getEmptySlots(targ.getInventory().getContents()) < TradeCmd.getFullSlots(TradeCmd.getUpperContents(e.getInventory()))) {
            init.sendMessage(NOT_ENOUGH_SPACE);
            targ.sendMessage(NOT_ENOUGH_SPACE);
            ts.abort();
            return;
        }
        
        ts.sendSummary();
    }
    
    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent e) {
        if (TradeCmd.traderequests.containsKey((Player) e.getPlayer())
                || TradeCmd.requesters.contains((Player) e.getPlayer())) {
            if (e.getInventory().getTitle().equals("Trade")) return;
            e.setCancelled(true);
            ((Player) e.getPlayer()).sendMessage("§cYou can not open an inventory while a trade is in session");
        }
    }

}