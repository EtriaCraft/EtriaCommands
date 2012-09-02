package Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import Util.CommandHandle;
import Util.Utils;
import Main.Strings;

public class EnchantCmd {
	
	@CommandHandle(name = "enchant", perms = "ec.enchant", minargs = 1)
	public static boolean enchant(CommandSender s, String[] args) {
        if (!(s instanceof Player)) return false;

        final Enchantment ench = Enchantment.getByName(args[0].toUpperCase());
        final ItemStack is = ((Player) s).getItemInHand();
        
        if (ench == null) {
            s.sendMessage("§cEnchantment invalid");
            s.sendMessage("§aAbailable enchantments: " + Utils.getEnchantmentList(null));
            return true;
        }

        int level = 1;
        if (args.length >= 2) {
            try {
                level = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                s.sendMessage("§cLevel invalid");
                return true;
            }
        }
        
        if (!ench.canEnchantItem(is)) {
            s.sendMessage("§cCannot enchant your§7 " + Strings.toTitle(is.getType().name()) + " §cwith§7 " + Strings.toTitle(ench.getName()));
            s.sendMessage("§aEnchantments you can use: " + Utils.getEnchantmentList(is));
            return true;
        }

        is.addUnsafeEnchantment(ench, level);
        s.sendMessage("§aEnchanted your§e " + Strings.toTitle(is.getType().name()) + " §awith " + Strings.toTitle(ench.getName()) + " " + Strings.toRomanNumeral(level));
        return true;
    }
	
}