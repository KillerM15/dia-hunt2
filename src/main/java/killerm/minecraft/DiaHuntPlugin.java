package killerm.minecraft;

import killerm.minecraft.commands.DiaHuntExecutor;
import killerm.minecraft.game.ChestGameData;
import killerm.minecraft.game.GameState;
import killerm.minecraft.game.PlayerGameData;
import killerm.minecraft.listener.DiaHuntListener;
import org.bukkit.plugin.java.JavaPlugin;

public class DiaHuntPlugin extends JavaPlugin {
    private volatile static DiaHuntPlugin instance;
    private GameState gameState;
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
        initializeGame();
        initializeExecutor();
        initializeListener();
    }

    private void prepareInstance() {
        instance = this;
    }

    private void initializeGame() {
        gameState = new GameState();
        playerGameData = new PlayerGameData();
        chestGameData = new ChestGameData();
    }

    private void initializeExecutor() {
        diaHuntExecutor = new DiaHuntExecutor(gameState, playerGameData, chestGameData);
        this.getCommand("diahunt").setExecutor(diaHuntExecutor);
    }

    private void initializeListener() {
        diaHuntListener = new DiaHuntListener(gameState, playerGameData, chestGameData);
        this.getServer().getPluginManager().registerEvents(diaHuntListener, this);
    }

    @Override
    public void onDisable() {
        // diaHuntExecutor.onCommand(null, ) // TODO: stop command
    }
}
