package killerm.minecraft.game.data;

import killerm.minecraft.data.DiaConfig;
import killerm.minecraft.data.PlayerBackup;
import killerm.minecraft.data.RegionBackup;
import killerm.minecraft.utilities.Region;
import org.bukkit.Location;

public class GameBackup {
    private PlayerBackup playerBackup;
    private RegionBackup mapBackup;

    public GameBackup() {
        playerBackup = new PlayerBackup();
        mapBackup = new RegionBackup(retrieveMap());
    }

    private Region retrieveMap() {
        Location loc1 = DiaConfig.MAP_POS1.get();
        Location loc2 = DiaConfig.MAP_POS2.get();

        return new Region(loc1, loc2);
    }

    public void reloadMapRegionFromConfig() {
        mapBackup.setRegion(retrieveMap());
    }

    public PlayerBackup getPlayerBackup() {
        return playerBackup;
    }

    public RegionBackup getMapBackup() {
        return mapBackup;
    }
}