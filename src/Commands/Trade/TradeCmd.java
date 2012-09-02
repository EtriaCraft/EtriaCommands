package Commands.Trade;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Util.CommandHandle;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class TradeCmd {

	public static HashMap<Pair<Player, Player>, TradeSession> trades = new HashMap();
	public static BiMap<Player, Player> traders = HashBiMap.create();
	public static HashMap<Player, TradeRequest> traderequests = new HashMap();
	public static List<Player> requesters = new ArrayList();
	
	@CommandHandle(name = "trade", perms = "ec.trade")
    public static boolean trade(CommandSender s, String[] args) {
        if (!(s instanceof Player)) return false;
        Player p = (Player) s;
        if (args.length < 1) {
            p.sendMessage("§aTrade §fcommands:");
            p.sendMessage("  §2/trade [§fname§2]§e Send a trade request to [name]");
            p.sendMessage("  §2/trade accept§e Accept a trade request");
            p.sendMessage("  §2/trade deny§e Deny a trade request");
            p.sendMessage("  §2/trade confirm§e Confirm the trade");
            p.sendMessage("  §2/trade cancel§e Cancel the trade");
            return true;
        }
        if (args[0].equalsIgnoreCase("accept")) {
            if (!traderequests.containsKey(p)) {
                p.sendMessage("§cYou had no trade requests pending");
                return true;
            }
            traderequests.get(p).accept();
            return true;
        } else if (args[0].equalsIgnoreCase("deny")) {
            if (!traderequests.containsKey(p)) {
                p.sendMessage("§cYou had no trade requests pending");
                return true;
            }
            
            p.sendMessage("§cYou denied the trade request");
            traderequests.get(p).deny();
            return true;
        } else if (args[0].equalsIgnoreCase("confirm")) {
            if (!isTrading(p)) {
                p.sendMessage("§cYou have no trades active");
                return true;
            }
            
            Player init = getInit(p);
            Player targ = getTarget(p);
            
            trades.get(new Pair(init, targ)).confirmPlayer(p);
            return true;
        } else if (args[0].equalsIgnoreCase("cancel")) {
            if (!isTrading(p)) {
                p.sendMessage("§cYou have no trades active");
                return true;
            }
            
            Player init = getInit(p);
            Player targ = getTarget(p);
            
            trades.get(new Pair(init, targ)).abort();
            return true;
        } else { //They're trying to type in a name.
            Player o = Bukkit.getPlayer(args[0]);
            if (o == null) {
                p.sendMessage("§cThat player isn't online");
                return true;
            } else if(isTrading(o) || traderequests.containsKey(o) || requesters.contains(o)) {
                p.sendMessage("§cThat player is busy");
                return true;
            } else if (o.equals(p)) {
                p.sendMessage("§cYou cannot trade with yourself");
                return true;
            } else if (traderequests.containsKey(p)) {
                p.sendMessage("§cYou can't send a request until you deal with your current");
                return true;
            }
            
            //We can continue
            TradeRequest tr = new TradeRequest(p, o);
            traderequests.put(o, tr);
            requesters.add(p);
            
            p.sendMessage("§aTrade request sent to§e " + o.getName());
            o.sendMessage("§aTrade request received from§e " + p.getName());
            o.sendMessage("§aType §e/trade accept§a to accept, or §e/trade deny§a to deny");
            return true;
        }
    }
    
    public static ItemStack[] getUpperContents(Inventory dblchst) {
        ItemStack[] is = new ItemStack[27];
        for (int i = 0; i <= 26; i++) {
            is[i] = dblchst.getItem(i);
        }
        return is;
    }
    
    public static ItemStack[] getLowerContents(Inventory dblchst) {
        int it = 0;
        ItemStack[] is = new ItemStack[27];
        for (int i = 27; i <= 53; i++) {
            is[it] = dblchst.getItem(i);
            it++;
        }
        return is;
    }
    
    public static int getEmptySlots(ItemStack[] iss) {
        int i = 0;
        for (ItemStack is : iss) {
            if (is == null) i++;
        }
        return i;
    }
    
    public static int getFullSlots(ItemStack[] iss) {
        int i = 0;
        for (ItemStack is : iss) {
            if (is != null) i++;
        }
        return i;
    }

    public static boolean isEmpty(Inventory inv) {
        for (ItemStack is : inv.getContents()) {
            if (is != null) return false;
        }
        return true;
    }

    public static String getItemList(ItemStack[] iss) {
        HashMap<String, Integer> items = new HashMap<String, Integer>();
        String list = "";
        for(ItemStack is : iss) {
            if (is == null) continue;
            int amm = 0;
            if (items.containsKey(is.getType().name())) {
                amm += (items.get(is.getType().name())) + is.getAmount();
                items.put(is.getType().name(), amm);
            } else {
                items.put(is.getType().name(), is.getAmount());
            }
        }
        for (Map.Entry<String, Integer> map : items.entrySet()) {
            list += map.getValue() + " " + map.getKey().toLowerCase()+ ", ";
        }
        return list;
    }
    public static boolean isTrading(Player p) {
        return (traders.containsKey(p) || traders.containsValue(p));
    }
    public static Player getInit(Player p) {
        return (traders.containsKey(p))? p : traders.inverse().get(p);
    }
    public static Player getTarget(Player p) {
        return (traders.containsValue(p))? p : traders.get(p);
    }
    
}