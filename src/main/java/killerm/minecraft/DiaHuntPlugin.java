package killerm.minecraft;

import killerm.minecraft.commands.DiaHuntExecutor;
import killerm.minecraft.game.ChestData;
import killerm.minecraft.game.DiaHuntGameState;
import killerm.minecraft.game.PlayerGameData;
import killerm.minecraft.listener.DiaHuntListener;
import org.bukkit.plugin.java.JavaPlugin;

public class DiaHuntPlugin extends JavaPlugin {
    private volatile static DiaHuntPlugin instance;
    private DiaHuntGameState diaHuntGameState;
    private PlayerGameData playerGameData;
    private ChestData chestData;
    private DiaHuntExecutor diaHuntExecutor;
    private DiaHuntListener diaHuntListener;

    public static DiaHuntPlugin getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        prepareInstance();
        initializeGame();
        initializeExecutor();
        initializeListener();
    }

    private void prepareInstance() {
        instance = this;
    }

    private void initializeGame() {
        diaHuntGameState = new DiaHuntGameState();
        playerGameData = new PlayerGameData();
        chestData = new ChestData();
    }

    private void initializeExecutor() {
        diaHuntExecutor = new DiaHuntExecutor(diaHuntGameState, playerGameData, chestData);
        this.getCommand("diahunt").setExecutor(diaHuntExecutor);
    }

    private void initializeListener() {
        diaHuntListener = new DiaHuntListener(diaHuntGameState, playerGameData, chestData);
        this.getServer().getPluginManager().registerEvents(diaHuntListener, this);
    }

    @Override
    public void onDisable() {
        // diaHuntExecutor.onCommand(null, ) // TODO: stop command
    }
}
