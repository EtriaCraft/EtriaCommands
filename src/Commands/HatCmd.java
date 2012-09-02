package Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import Util.CommandHandle;

public class HatCmd {

    @CommandHandle(name = "hat", perms = "ec.hat")
    public static boolean hat(CommandSender s, String[] args) {
        if (!(s instanceof Player)) return false;

        final Player p = (Player) s;
        final ItemStack oldhat = p.getInventory().getHelmet();
        if (oldhat == null) {
            final PlayerInventory inv = p.getInventory();
            final ItemStack hat = new ItemStack(p.getItemInHand().getType(), 1, p.getItemInHand().getDurability());

            if (hat.getTypeId() >= 256 || hat.getTypeId() == 0) {
                s.sendMessage("§cYou can't wear that! Please use a block");
                return true;
            }

            inv.removeItem(hat);
            inv.setHelmet(hat);
            s.sendMessage("§aThat's a nice hat you got there.");
            return true;
        } else {
            s.sendMessage("§cTake off your old hat!");
            return true;
        }
    }
    
}