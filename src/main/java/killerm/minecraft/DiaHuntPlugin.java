package killerm.minecraft;

import killerm.minecraft.commands.DiaHuntExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public class DiaHuntPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        this.getCommand("diahunt").setExecutor(new DiaHuntExecutor());
    }

    @Override
    public void onDisable() {
    }
}
