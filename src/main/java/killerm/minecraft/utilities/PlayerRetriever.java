package killerm.minecraft.utilities;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collection;

public class PlayerRetriever {
    public Collection<Player> retrieveOnlinePlayers() {
        return (Collection) Bukkit.getOnlinePlayers();
    }
}
