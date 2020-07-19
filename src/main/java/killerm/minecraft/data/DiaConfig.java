package killerm.minecraft.data;

import killerm.minecraft.DiaHuntPlugin;
import killerm.minecraft.communication.Message;
import killerm.minecraft.error.ParameterException;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

public enum DiaConfig {
    WORLD_NAME("world", String.class),
    SPAWN_AQUA(new Location(null, 0, 0, 0), Location.class),
    SPAWN_LAVA(new Location(null, 0, 0, 0), Location.class),
    MAP_POS1(new Location(null, 0, 0, 0), Location.class),
    MAP_POS2(new Location(null, 0, 0, 0), Location.class),
    TICKS_PER_DIAMOND(80.0, Double.class),
    SECONDS_UNTIL_START(60.0, Double.class),
    SECONDS_COUNTS_AS_KILL(13.0, Double.class);

    private Class T;
    private Object defaultValue;

    private DiaConfig(Object defaultValue, Class T) {
        this.T = T;
        this.defaultValue = defaultValue;
    }

    public <U> void set(U value) {
        throwIfWrongType(value.getClass());

        DiaHuntPlugin plugin = DiaHuntPlugin.getInstance();

        plugin.getConfig().set(this.toString(), value);
        plugin.saveConfig();
    }

    private void throwIfWrongType(Class U) {
        if (!U.equals(T)) {
            throw new ParameterException(U.getName() + Message.AND + T.getName() + Message.NOT_SAME_TYPE);
        }
    }

    public <T> T get() {
        FileConfiguration config = DiaHuntPlugin.getInstance().getConfig();

        if (config.get(this.toString()) == null) {
            set(defaultValue);
        }

        return (T) config.get(this.toString());
    }

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }

    public static DiaConfig getInstance(String diaConfigString) {
        for (DiaConfig d : DiaConfig.values()) {
            if (diaConfigString.equals(d.toString())) {
                return d;
            }
        }

        throw new ParameterException(Message.DIACONFIG_NOT_VALID);
    }
}
