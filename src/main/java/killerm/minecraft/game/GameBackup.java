package killerm.minecraft.game;

import killerm.minecraft.data.DiaConfig;
import killerm.minecraft.data.PlayerBackup;
import killerm.minecraft.data.RegionBackup;
import killerm.minecraft.utilities.Region;
import org.bukkit.Location;

public class GameBackup {
    public PlayerBackup playerBackup;
    public RegionBackup regionBackup;

    public GameBackup() {
        playerBackup = new PlayerBackup();
        regionBackup = new RegionBackup(retrieveMap());
    }

    private Region retrieveMap() {
        Location loc1 = DiaConfig.MAP_POS1.get();
        Location loc2 = DiaConfig.MAP_POS2.get();

        return new Region(loc1, loc2);
    }

    public void reloadMapRegionFromConfig() {
        regionBackup.setRegion(retrieveMap());
    }
}