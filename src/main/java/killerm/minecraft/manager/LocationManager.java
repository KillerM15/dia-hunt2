package killerm.minecraft.manager;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Collection;

public class LocationManager {
    public void teleport(Collection<Player> players, Location loc) {
        for (Player player : players) {
            teleport(player, loc);
        }
    }

    private void teleport(Player player, Location loc) {
        player.teleport(loc);
    }
}
