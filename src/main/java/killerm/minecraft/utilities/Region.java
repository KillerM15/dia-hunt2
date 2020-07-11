package killerm.minecraft.utilities;

public class Region {
    private Coordinates coordinates1;
    private Coordinates coordinates2;

    public Region(Coordinates coordinates1, Coordinates coordinates2) {
        this.coordinates1 = coordinates1;
        this.coordinates2 = coordinates2;
    }

    public int maxX() {
        return Integer.max(coordinates1.x, coordinates2.x);
    }

    public int minX() {
        return Integer.min(coordinates1.x, coordinates2.x);
    }

    public int maxY() {
        return Integer.max(coordinates1.y, coordinates2.y);
    }

    public int minY() {
        return Integer.min(coordinates1.y, coordinates2.y);
    }

    public int maxZ() {
        return Integer.max(coordinates1.z, coordinates2.z);
    }

    public int minZ() {
        return Integer.min(coordinates1.z, coordinates2.z);
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

        return this.coordinates1.equals(region.coordinates1) && this.coordinates2.equals(region.coordinates2);
    }
}
