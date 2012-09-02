package Commands;

import Util.CommandHandle;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TimeCmd {
    
    @CommandHandle(name = "time", perms = "ec.time", minargs = 1)
    public static boolean time(CommandSender s, String[] args) {
        long time;
        try {
            time = Long.parseLong(args[0]);
        } catch (NumberFormatException e) {
            switch (args[0]){
                case "day": time = 0; break;
                case "noon": time = 6000; break;
                case "night": time = 14000; break;
                default:
                    s.sendMessage("§cInvalid time");
                    return true;
            }
        }

        final World w;
        if (args.length >= 2) {
            w = Bukkit.getWorld(args[1]);
            if (w == null) {
                s.sendMessage("§cThat world doesn't exist!");
                return true;
            }
        } else {
            if (!(s instanceof Player)) return false;
            w = ((Player) s).getWorld();
        }

        w.setTime(time);
        s.sendMessage("§aSet the time in§e " + w.getName() + " §ato§e " + args[0]);
        return true;
    }
}