package killerm.minecraft;

import killerm.minecraft.commands.DiaHuntExecutor;
import killerm.minecraft.game.DiaChestGameData;
import killerm.minecraft.game.DiaHuntGameState;
import killerm.minecraft.game.PlayerGameData;
import org.bukkit.plugin.java.JavaPlugin;

public class DiaHuntPlugin extends JavaPlugin {
    private volatile static DiaHuntPlugin instance;
    private DiaHuntGameState diaHuntGameState = new DiaHuntGameState();
    private PlayerGameData playerGameData = new PlayerGameData();
    private DiaChestGameData diaChestGameData = new DiaChestGameData();

    public static DiaHuntPlugin getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        initializeExecutor();
        initializeListener();
    }

    private void initializeExecutor() {
        this.getCommand("diahunt").setExecutor(new DiaHuntExecutor(diaHuntGameState, playerGameData, diaChestGameData));
    }

    private void initializeListener() {
        // TODO: DiaHuntListener
    }

    @Override
    public void onDisable() {
    }
}
