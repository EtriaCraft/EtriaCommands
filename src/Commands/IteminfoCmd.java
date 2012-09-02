package Commands;

import java.util.Map;

import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import Main.Strings;
import Util.CommandHandle;

public class IteminfoCmd {
	
	@CommandHandle(name = "iteminfo", perms = "ec.iteminfo")
    public static boolean iteminfo(CommandSender s, String[] args) {
        if (!(s instanceof Player)) return false;

        ItemStack is = ((Player) s).getItemInHand();

        s.sendMessage("§aItem information:");
        s.sendMessage("§a" + Strings.toTitle(is.getType().name()) + " -§e " + Integer.toString(is.getTypeId()) + (is.getDurability() != 0 ? "§a:" + is.getDurability() : ""));

        if (is.getEnchantments().size() < 1) return true;
        for (Map.Entry<Enchantment, Integer> ench : is.getEnchantments().entrySet()) {
            s.sendMessage("§7§o" + Strings.toTitle(ench.getKey().getName()) + " " + Strings.toRomanNumeral(ench.getValue()));
        }
        return true;
    }

}
