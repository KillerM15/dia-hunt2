package killerm.minecraft.data;

import killerm.minecraft.DiaHuntPlugin;
import killerm.minecraft.communication.Message;
import killerm.minecraft.error.DiaHuntParameterException;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

public enum DiaConfig {
    WORLD_NAME("world", String.class),
    SECONDS_UNTIL_START(60, Integer.class),
    SPAWN_AQUA(new Location(Bukkit.getWorld((String) DiaConfig.WORLD_NAME.get()), 0, 0, 0), Location.class),
    SPAWN_LAVA(new Location(Bukkit.getWorld((String) DiaConfig.WORLD_NAME.get()), 0, 0, 0), Location.class);

    private DiaHuntPlugin diaHuntPlugin = DiaHuntPlugin.getInstance();
    private FileConfiguration config = diaHuntPlugin.getConfig();
    private Class T;

    private DiaConfig(Object defaultValue, Class T) {
        this.T = T;
        set(defaultValue);
    }

    public <U> void set(U value) {
        throwIfWrongType(value.getClass());

        config.set(this.getName(), value);
        diaHuntPlugin.saveConfig();
    }

    private void throwIfWrongType(Class U) {
        if (!U.equals(T)) {
            throw new RuntimeException("Type " + U.getName() + " and " + T.getName() + " are not the same type");
        }
    }

    public <T> T get() {
        return (T) config.get(this.getName());
    }

    public String getName() {
        return toString().toLowerCase();
    }

    public static DiaConfig getInstance(String diaConfigString) {
        for (DiaConfig d : DiaConfig.values()) {
            if (diaConfigString.equals(d.getName())) {
                return d;
            }
        }

        throw new DiaHuntParameterException(Message.DIACONFIG_NOT_VALID);
    }
}
