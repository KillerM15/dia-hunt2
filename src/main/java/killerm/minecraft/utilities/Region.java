package killerm.minecraft.utilities;

import org.bukkit.Location;
import org.bukkit.World;

import static org.bukkit.util.NumberConversions.round;

public class Region {
    private Location location1;
    private Location location2;

    public Region(Location location1, Location location2) {
        this.location1 = location1;
        this.location2 = location2;
    }

    public int maxX() {
        return round(Double.max(location1.getX(), location2.getX()));
    }

    public int minX() {
        return round(Double.min(location1.getX(), location2.getX()));
    }

    public int maxY() {
        return round(Double.max(location1.getY(), location2.getY()));
    }

    public int minY() {
        return round(Double.min(location1.getY(), location2.getY()));
    }

    public int maxZ() {
        return round(Double.max(location1.getZ(), location2.getZ()));
    }

    public int minZ() {
        return round(Double.min(location1.getZ(), location2.getZ()));
    }

    public int rangeX() {
        return maxX() - minX();
    }

    public int rangeY() {
        return maxY() - minY();
    }

    public int rangeZ() {
        return maxZ() - minZ();
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }

        if (!(object instanceof Region)) {
            return false;
        }

        Region region = (Region) object;

        return this.location1.equals(region.location1) && this.location2.equals(region.location2);
    }

    public World getWorld() {
        return location1.getWorld();
    }
}
