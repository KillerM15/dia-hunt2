package killerm.minecraft.game.interaction;

import killerm.minecraft.data.DiaConfig;
import killerm.minecraft.manager.LocationManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Collection;

public class LocationSetter {
    private LocationManager locationManager;

    public LocationSetter() {
        this.locationManager = new LocationManager();
    }

    public LocationSetter(LocationManager locationManager) {
        this.locationManager = locationManager;
    }

    public void teleport(Collection<Player> players, DiaConfig diaConfig) {
        for (Player player : players) {
            teleport(player, diaConfig);
        }
    }

    public void teleport(Player player, DiaConfig diaConfig) {
        player.teleport((Location) diaConfig.get());
    }

    public void teleportToAquaSpawn(Player player) {
        teleport(player, DiaConfig.SPAWN_AQUA);
    }

    public void teleportToLavaSpawn(Player player) {
        teleport(player, DiaConfig.SPAWN_LAVA);
    }
}
