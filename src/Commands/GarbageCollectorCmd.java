package Commands;

import org.bukkit.command.CommandSender;

import Util.CommandHandle;

public class GarbageCollectorCmd {
	
	
	/*
	 * Heavily based on garbage collector from RoyalCommands, credit goes to royalDev.
	 */
	
	@CommandHandle(name="garbagecollector", perms="ec.garbagecollector")
    public static boolean garbagecollector(CommandSender s, String[] args) {
            Runtime r = Runtime.getRuntime();
            long oldMem = r.freeMemory() / 1048576L;
            s.sendMessage("§cRunning Java garbage collector...");
            r.gc();
            int processors = r.availableProcessors();
            long maxMem = r.maxMemory() / 1048576L;
            long newMem = r.freeMemory() / 1048576L;
            if (r.maxMemory() < 0L) {
                s.sendMessage("§4You may be using CACAO Java, which means that these values may be negative.");
                s.sendMessage("§4Please switch to another JVM.");
            }
            s.sendMessage("§aUsed memory before: " + "§e" + (maxMem - oldMem) + " MB");
            s.sendMessage("§aCurrent memory: " + "§e" + (maxMem - newMem) + " MB" + "§a" + "/" + "§e" + maxMem + " MB");
            s.sendMessage("§aMemory freed: " + "§e" + (newMem - oldMem) + " MB");
            s.sendMessage("§aProcessors available to Java: " + "§e" + processors);
            return true;
    }

}