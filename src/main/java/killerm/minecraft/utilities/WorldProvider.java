package killerm.minecraft.utilities;

import killerm.minecraft.data.DiaConfig;
import org.bukkit.Bukkit;
import org.bukkit.World;

public class WorldProvider {
    public World getWorld() {
        return Bukkit.getWorld((String) DiaConfig.WORLD_NAME.get());
    }
}
