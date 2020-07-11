package killerm.minecraft.data;

import killerm.minecraft.DiaHuntPlugin;
import killerm.minecraft.communication.Message;
import killerm.minecraft.error.DiaHuntParameterException;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

public enum DiaConfig {
    WORLD_NAME("world", String.class),
    SECONDS_UNTIL_START(60, Integer.class),
    SPAWN_AQUA(new Location(null, 0, 0, 0), Location.class),
    SPAWN_LAVA(new Location(null, 0, 0, 0), Location.class),
    MAP_POS1(new Location(null, 0, 0, 0), Location.class),
    MAP_POS2(new Location(null, 0, 0, 0), Location.class);

    private Class T;
    private Object defaultValue;

    private DiaConfig(Object defaultValue, Class T) {
        this.T = T;
        this.defaultValue = defaultValue;
    }

    public <U> void set(U value) {
        throwIfWrongType(value.getClass());

        DiaHuntPlugin plugin = DiaHuntPlugin.getInstance();

        plugin.getConfig().set(this.getName(), value);
        plugin.saveConfig();
    }

    private void throwIfWrongType(Class U) {
        if (!U.equals(T)) {
            throw new RuntimeException("Type " + U.getName() + " and " + T.getName() + " are not the same type");
        }
    }

    public <T> T get() {
        FileConfiguration config = DiaHuntPlugin.getInstance().getConfig();

        if (config.get(this.getName()) == null) {
            set(defaultValue);
        }

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
