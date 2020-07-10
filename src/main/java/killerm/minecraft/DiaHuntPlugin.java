package killerm.minecraft;

import killerm.minecraft.commands.DiaHuntExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public class DiaHuntPlugin extends JavaPlugin {
    private volatile static DiaHuntPlugin instance;

    public static DiaHuntPlugin getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        this.getCommand("diahunt").setExecutor(new DiaHuntExecutor());
    }

    @Override
    public void onDisable() {
    }
}
