package killerm.minecraft.data;

import killerm.minecraft.utilities.Region;
import org.bukkit.Location;
import org.bukkit.block.data.BlockData;

public class RegionBackup {
    private Region region;
    private BlockData[][][] blockDatas;

    public RegionBackup(Region region) {
        setRegion(region);
    }

    public void setRegion(Region region) {
        this.region = region;

        initializeBlocks(region);
    }

    private void initializeBlocks(Region region) {
        blockDatas = new BlockData[region.rangeX() + 1][region.rangeY() + 1][region.rangeZ() + 1];
    }

    public void backup() {
        // Get variables for performance
        // .. although it doesn't change anything probably
        int minX = region.minX();
        int minY = region.minY();
        int minZ = region.minZ();
        int rangeX = region.rangeX();
        int rangeY = region.rangeY();
        int rangeZ = region.rangeZ();

        for (int x = 0; x < rangeX + 1; x++) {
            for (int y = 0; y < rangeY + 1; y++) {
                for (int z = 0; z < rangeZ + 1; z++) {
                    Location loc = new Location(region.getWorld(), x + minX, y + minY, z + minZ);
                    blockDatas[x][y][z] = loc.getBlock().getBlockData();
                }
            }
        }
    }

    public void restore() {
        // Get variables for performance
        // .. although it doesn't change anything probably
        int minX = region.minX();
        int minY = region.minY();
        int minZ = region.minZ();
        int rangeX = region.rangeX();
        int rangeY = region.rangeY();
        int rangeZ = region.rangeZ();

        for (int x = 0; x < rangeX + 1; x++) {
            for (int y = 0; y < rangeY + 1; y++) {
                for (int z = 0; z < rangeZ + 1; z++) {
                    Location loc = new Location(region.getWorld(), x + minX, y + minY, z + minZ);
                    loc.getBlock().setBlockData(blockDatas[x][y][z]);
                }
            }
        }
    }
}
