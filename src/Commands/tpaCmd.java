package Commands;

import java.util.HashMap;
import java.util.LinkedList;
import Main.Strings;
import Util.CommandHandle;
import Util.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class tpaCmd {
    
    public static HashMap<String, LinkedList<String>> tpRequestDb = new HashMap<String, LinkedList<String>>();

    @CommandHandle(name = "tpa", minargs = 1)
    public static boolean tpa(CommandSender s, String[] args) {
        if (!(s instanceof Player)) return false;

        String player = args[0];
        final Player p;
        if (args[0].equalsIgnoreCase("accept") || args[0].equalsIgnoreCase("y")) {
            if (!s.hasPermission("ec.tpa.answer")) {
                s.sendMessage(Strings.NO_PERMISSION.toString());
                return true;
            }
            if (!tpRequestDb.containsKey(s.getName()) || tpRequestDb.get(s.getName()).isEmpty()) {
                s.sendMessage("§cYou have no teleport requests pending");
                return true;
            }
            if (args.length < 2) {
                player = tpRequestDb.get(s.getName()).getLast();
            } else {
                player = args[1];
            }

            p = Bukkit.getPlayer(player);
            if (p == null) {
                s.sendMessage("§cThat player is no longer online");
                return true;
            }
            if (tpRequestDb.get(s.getName()).contains(p.getName())) {
            	PlayerUtils.teleport(p, ((Player) s).getLocation());
                p.sendMessage("§e" + s.getName() + " §aaccepted your teleport request");
                s.sendMessage("§aYou accepted the teleport request from§e " + p.getName());
                tpRequestDb.get(s.getName()).remove(p.getName());
            } else s.sendMessage("§cThis player hasn't requested teleport permission from you");
        } else if (args[0].equalsIgnoreCase("decline") || args[0].equalsIgnoreCase("n")) {
            if (!s.hasPermission("ec.tpa.answer")) {
                s.sendMessage(Strings.NO_PERMISSION.toString());
                return true;
            }
            if (args.length < 2) return false;
            player = args[1];
            
            p = Bukkit.getPlayer(player);
            if (p == null) {
                s.sendMessage("§cThat player is no longer online");
                return true;
            }
            if (!tpRequestDb.containsKey(s.getName()) || tpRequestDb.get(s.getName()).contains(p.getName())) {
                p.sendMessage("§cYour teleport request to§7 " + s.getName() + " §cwas declined");
                tpRequestDb.get(s.getName()).remove(p.getName());
                s.sendMessage("§aDenied§e " + player + "'s §ateleport request");
            } else s.sendMessage("§cThis player hasn't requested teleport permission from you");
        } else {
            if (!s.hasPermission("ec.tpa")) {
                s.sendMessage(Strings.NO_PERMISSION.toString());
                return true;
            }
            p = Bukkit.getPlayer(player);
            if (p == null) {
                s.sendMessage(Strings.PLAYER_OFFLINE.toString());
                return true;
            }
            if (!tpRequestDb.containsKey(p.getName())) {
                tpRequestDb.put(p.getName(), new LinkedList<String>());
            }

            tpRequestDb.get(p.getName()).add(s.getName());
            s.sendMessage("§aYou have sent a teleport request to§e " + p.getName());
            p.sendMessage("§aYou have recieved a teleport request from§e " + s.getName());
            p.sendMessage("§aDo §e/tpa accept " + s.getName() + " §ato accept, or §e/tpa decline " + s.getName() + " §ato deny");
        }
        return true;
    }

}
