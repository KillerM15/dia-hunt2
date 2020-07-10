package killerm.minecraft.data;

import killerm.minecraft.DiaHuntPlugin;
import killerm.minecraft.communication.Message;
import killerm.minecraft.error.DiaHuntParameterException;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

public enum DiaConfig {
    SPAWN_AQUA(Location.class),
    SPAWN_LAVA(Location.class),
    SECONDS_UNTIL_START(Integer.class);

    private DiaHuntPlugin diaHuntPlugin;
    private FileConfiguration config;
    private Class T;

    private DiaConfig(Class T) {
        diaHuntPlugin = DiaHuntPlugin.getInstance();
        config = diaHuntPlugin.getConfig();
        this.T = T;
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
