package killerm.minecraft.data;

import killerm.minecraft.DiaHuntPlugin;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

public class DiaConfig<T> {
    public static DiaConfig<Location> SPAWN_AQUA = new DiaConfig("Spawn location of team aqua");
    public static DiaConfig<Location> SPAWN_LAVA = new DiaConfig("Spawn location of team lava");

    private String name;
    private DiaHuntPlugin diaHuntPlugin = DiaHuntPlugin.getInstance();
    private FileConfiguration config = diaHuntPlugin.getConfig();

    private DiaConfig(String name) {
        this.name = name;
    }

    public void set(T value) {
        config.set(this.name, value);
        diaHuntPlugin.saveConfig();
    }

    public T get() {
        return (T) config.get(this.name);
    }
}
