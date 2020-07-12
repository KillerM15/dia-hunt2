package killerm.minecraft.manager;

import killerm.minecraft.data.DiaConfig;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Collection;

public class LocationManager {
    public void teleport(Collection<Player> players, Location loc) {
        for (Player player : players) {
            teleport(player, loc);
        }
    }

    public void teleport(Player player, Location loc) {
        player.teleport(loc);
    }

    public void teleport(Collection<Player> players, DiaConfig diaConfig) {
        for (Player player : players) {
            teleport(player, diaConfig);
        }
    }

    public void teleport(Player player, DiaConfig diaConfig) {
        player.teleport((Location) diaConfig.get());
    }
}
