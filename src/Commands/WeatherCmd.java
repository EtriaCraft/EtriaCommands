package Commands;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import Util.CommandHandle;

public class WeatherCmd {

	@CommandHandle(name = "weather", perms = "ec.weather", minargs = 1)
    public static boolean weather(CommandSender s, String[] args) {
        final World w;
        if (args.length >= 2) {
            w = Bukkit.getWorld(args[1]);
            if (w == null) {
                s.sendMessage("§cThat world doesn't exist!");
            }
        } else {
            if (!(s instanceof Player)) return false;
            w = ((Player) s).getWorld();
        }

        switch (args[0]) {
            case "on":
                w.setStorm(true);
                break;
            case "off":
                w.setStorm(false);
                break;
            case "thunder":
                w.setThundering(true);
                break;
            default:
                s.sendMessage("§cInvalid weather state");
                return true;
        }
        s.sendMessage("§aSet the weather in§e " + w.getName() + " §ato§e " + args[0]);
        return true;
    }

}