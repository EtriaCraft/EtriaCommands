package Commands;

import org.bukkit.command.CommandSender;

import Main.DBConnection;
import Util.CommandHandle;

public class DeletewarpCmd {

    @CommandHandle(name = "deletewarp", perms = "ec.deletewarp", minargs = 1)
    public static boolean deletewarp(CommandSender s, String[] args) {
        if (WarpCmd.warpsDb.containsKey(args[0].toLowerCase())) {
            DBConnection.sql.modifyQuery("DELETE FROM `world_warps` WHERE `name` = '"+args[0].toLowerCase()+"';");
            WarpCmd.warpsDb.remove(args[0].toLowerCase());
            s.sendMessage("§aDeleteing warp§e " + args[0]);
        } else {
            s.sendMessage("§cThat warp doesn't exist");
        }
        return true;
    }
}
