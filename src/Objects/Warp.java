package Objects;

import org.bukkit.Location;
import org.bukkit.World;

public class Warp {
    
    private Location loc;
    private String name;
    
    public Warp(Location loc, String name) {
        this.loc = loc;
        this.name = name;
    }
    
    public Warp(World w, int x, int y, int z, float pitch, float yaw, String name) {
        this(new Location(w, x, y, z, yaw, pitch), name);
    }
    
    public Location getLocation() {
        return this.loc;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setLocation(Location loc) {
        this.loc = loc;
    }
    
}
