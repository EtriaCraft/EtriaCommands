package Listeners;

import Commands.VanishCmd;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class InventoryListener implements Listener {
    
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (VanishCmd.silentChestInUse((Player) e.getWhoClicked())) e.setCancelled(true);
    }
    
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        if (VanishCmd.silentChestInUse((Player) e.getPlayer())) VanishCmd.silentChestClose((Player) e.getPlayer());
    }
    
}
