package killerm.minecraft.utilities;

import org.bukkit.Location;

public class Coordinates {
    public int x, y, z;

    public Coordinates(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Coordinates(Location loc) {
        this.x = (int) loc.getX();
        this.y = (int) loc.getY();
        this.z = (int) loc.getZ();
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }

        if (!(object instanceof Coordinates)) {
            return false;
        }

        Coordinates coordinates = (Coordinates) object;

        return this.x == coordinates.x && this.y == coordinates.y && this.z == coordinates.z;
    }
}
