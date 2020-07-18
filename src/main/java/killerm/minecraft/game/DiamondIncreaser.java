package killerm.minecraft.game;

import killerm.minecraft.DiaHuntPlugin;
import killerm.minecraft.data.DiaConfig;
import killerm.minecraft.manager.ScoreboardManager;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class DiamondIncreaser {
    private PlayerGameData playerGameData;
    private DiaChestGameData diaChestGameData;
    private DiamondIndicator diamondIndicator;
    private ItemGiver itemGiver;
    private BukkitRunnable increaserThread;
    private ScoreboardManager scoreboardManager;

    public DiamondIncreaser(PlayerGameData playerGameData, DiaChestGameData diaChestGameData) {
        this.playerGameData = playerGameData;
        this.diaChestGameData = diaChestGameData;
        this.diamondIndicator = new DiamondIndicator();
        this.itemGiver = new ItemGiver();
        this.scoreboardManager = new ScoreboardManager();
    }

    public DiamondIncreaser(PlayerGameData playerGameData, DiaChestGameData diaChestGameData, DiamondIndicator diamondIndicator, ItemGiver itemGiver, ScoreboardManager scoreboardManager) {
        this.playerGameData = playerGameData;
        this.diaChestGameData = diaChestGameData;
        this.diamondIndicator = diamondIndicator;
        this.itemGiver = itemGiver;
        this.scoreboardManager = scoreboardManager;
    }

    public void start() {
        int ticksPerDiamond = (int) (double) DiaConfig.TICKS_PER_DIAMOND.get();

        this.increaserThread = new BukkitRunnable() {
            public void run() {
                addDiamonds();
            }
        };

        increaserThread.runTaskTimer(DiaHuntPlugin.getInstance(), 0, ticksPerDiamond);
    }

    public void startMocked() {
        addDiamonds();
    }

    private void addDiamonds() {
        addDiamondsToPlayers();
        addDiamondsToChests();
    }

    private void addDiamondsToPlayers() {
        for (Player player : playerGameData.players()) {
            if (!diamondIndicator.hasDiamonds(player)) {
                continue;
            }

            itemGiver.giveDia(player);
            scoreboardManager.refresh(player);
        }
    }

    private void addDiamondsToChests() {
        for (ShulkerBox shulkerBox : diaChestGameData.getShulkerBoxes()) {
            if (!diamondIndicator.hasDiamonds(shulkerBox)) {
                continue;
            }

            // Chest gets amountOfPlayers diamonds
            for (int i = 0; i < playerGameData.amountOfPlayers(); i++) {
                itemGiver.giveDia(shulkerBox);
            }
        }
    }

    public void stop() {
        increaserThread.cancel();
    }
}
