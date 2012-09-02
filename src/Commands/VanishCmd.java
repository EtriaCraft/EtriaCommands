package Commands;

import java.util.HashSet;
import java.util.Set;
import Main.Strings;
import Util.CommandHandle;
import Util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;

public class VanishCmd {

    public static PluginCommand vanish;
    public static Set<String> vanishDb = new HashSet<String>();
    public static Set<String> chestUserDb = new HashSet<String>();
            
    @CommandHandle(name = "vanish", perms = "ec.vanish")
    public static boolean vanish(CommandSender s, String[] args) {
        final Player p;
        if (args.length >= 1) p = Bukkit.getPlayer(args[0]);
        else {
            if (!(s instanceof Player)) return false;
            p = (Player) s;
        }
        if (p == null) {
            s.sendMessage(Strings.PLAYER_OFFLINE.toString());
            return true;
        }

        if (!isVanished(p)) {
            GodCmd.godDb.add(p.getName());
            p.sendMessage("§aPoof!");
        } else {
            if (p.getAllowFlight()) {
               s.sendMessage("§cYou can't un-vanish right now.. perhaps you're in Creative Mode?");
               return true;
            }
            GodCmd.godDb.remove(p.getName());
            p.sendMessage("§aYou are now visible");
        }
        setVanished(p, !isVanished(p));
        Utils.serverBroadcast("§e" + p.getName() + " §ahas " + (isVanished(p)? "vanished" : "reappeared"), "ec.vanish.alert");
        return true;
    }
    
    public static void setVanished(Player p, boolean state) {
        for (Player o : Bukkit.getOnlinePlayers()) {
            if (state) {
                if (o.hasPermission("ec.vanish.seehidden")) continue;
                o.hidePlayer(p);
            } else o.showPlayer(p);
        }
        if (state) vanishDb.add(p.getName());
        else vanishDb.remove(p.getName());
    }
    
    public static boolean isVanished(Player p) {
        return vanishDb.contains(p.getName());
    }
    
    public static void silentChestOpen(Player p) {
        chestUserDb.add(p.getName());
    }
    
    public static void silentChestClose(Player p) {
        chestUserDb.remove(p.getName());
    }
    
    public static boolean silentChestInUse(Player p) {
        return chestUserDb.contains(p.getName());
    }
    
}
