package Commands;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import Main.DBConnection;
import Main.Strings;
import Objects.Warp;
import Util.CommandHandle;
import Util.PlayerUtils;
import Util.Utils;

public class WarpCmd {
	
public static HashMap<String, Warp> warpsDb = new HashMap();
    
    public WarpCmd() {
        ResultSet results = DBConnection.sql.readQuery("SELECT * FROM `world_warps`;");
        try {
            while (results.next()) {
                String name = results.getString("name"), world = results.getString("world");
                World w = Bukkit.getWorld(world);
                if (w == null) continue;
                int x = results.getInt("x"), y = results.getInt("y"), z = results.getInt("z");
                float pitch = results.getFloat("pitch"), yaw = results.getFloat("yaw");
                warpsDb.put(name, new Warp(w, x, y, z, pitch, yaw, name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Utils.log.info(Strings.PREFIX + "Successfully loaded " + warpsDb.size() + " warps");
    }

	@CommandHandle(name = "warp", perms = "ec.warp")
    public static boolean warp(CommandSender s, String[] args) {
        if (!(s instanceof Player)) return false;
        
        if (args.length < 1) {
            String list = "";
            for (String string : warpsDb.keySet()) {
                if (!list.isEmpty()) list += ", ";
                list += string;
            }
            if (list.isEmpty()) {
                s.sendMessage("§cNo warps found!");
                return true;
            }
                
            s.sendMessage("§aWarps:§e " + list);
        } else {
            if (!warpsDb.containsKey(args[0].toLowerCase())) {
                s.sendMessage("§cThat warp does not exist");
                return true;
            }

            PlayerUtils.teleport((Player) s, warpsDb.get(args[0].toLowerCase()).getLocation());
            s.sendMessage("§eSent to warp§a " + args[0]);
        }
        return true;
    }
}
