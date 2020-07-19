package killerm.minecraft;

import killerm.minecraft.commands.DiaHuntExecutor;
import killerm.minecraft.data.DiaConfig;
import killerm.minecraft.game.data.ChestGameData;
import killerm.minecraft.game.data.GameStatus;
import killerm.minecraft.game.data.PlayerGameData;
import killerm.minecraft.listener.DiaHuntListener;
import org.bukkit.plugin.java.JavaPlugin;

public class DiaHuntPlugin extends JavaPlugin {
    private volatile static DiaHuntPlugin instance;
    private GameStatus gameStatus;
    private PlayerGameData playerGameData;
    private ChestGameData chestGameData;
    private DiaHuntExecutor diaHuntExecutor;
    private DiaHuntListener diaHuntListener;

    public static DiaHuntPlugin getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        prepareInstance();
        initializeConfig();
        initializeGame();
        initializeExecutor();
        initializeListener();
    }

    private void prepareInstance() {
        instance = this;
    }

    private void initializeConfig() {
        DiaConfig.setPlugin(instance);
    }

    private void initializeGame() {
        gameStatus = new GameStatus();
        playerGameData = new PlayerGameData();
        chestGameData = new ChestGameData();
    }

    private void initializeExecutor() {
        diaHuntExecutor = new DiaHuntExecutor(gameStatus, playerGameData, chestGameData);
        this.getCommand("diahunt").setExecutor(diaHuntExecutor);
    }

    private void initializeListener() {
        diaHuntListener = new DiaHuntListener(gameStatus, playerGameData, chestGameData);
        this.getServer().getPluginManager().registerEvents(diaHuntListener, this);
    }

    @Override
    public void onDisable() {
        // diaHuntExecutor.onCommand(null, ) // TODO: stop command
    }
}
