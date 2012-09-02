package Listeners;

import Commands.GodCmd;
import Commands.VanishCmd;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class EntityListener implements Listener {
    
    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        if (GodCmd.godDb.contains(((Player) e.getEntity()).getName())) {
            e.setCancelled(true);
            e.getEntity().setFireTicks(0);
        }
    }
    
    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        if (GodCmd.godDb.contains(((Player) e.getEntity()).getName())) {
            e.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onEntityTarget(EntityTargetEvent e) {
        if ((e.getTarget() instanceof Player) && VanishCmd.isVanished((Player) e.getTarget())) {
            e.setCancelled(true);
        }
    }
    
}
