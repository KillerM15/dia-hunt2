package killerm.minecraft.game;

import killerm.minecraft.DiaHuntPlugin;
import killerm.minecraft.data.DiaConfig;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class DiamondIncreaser {
    private PlayerGameData playerGameData;
    private DiaChestGameData diaChestGameData;
    private DiamondIndicator diamondIndicator;
    private ItemGiver itemGiver;
    private BukkitRunnable increaserThread;

    public DiamondIncreaser(PlayerGameData playerGameData, DiaChestGameData diaChestGameData) {
        this.playerGameData = playerGameData;
        this.diaChestGameData = diaChestGameData;
        this.diamondIndicator = new DiamondIndicator();
        this.itemGiver = new ItemGiver();
    }

    public void start() {
        int ticksPerDiamond = (int) (double) DiaConfig.TICKS_PER_DIAMOND.get();

        this.increaserThread = new BukkitRunnable() {
            public void run() {
                addDiamondToPlayers();
                addDiamondToChests();
            }
        };

        increaserThread.runTaskTimer(DiaHuntPlugin.getInstance(), 0, ticksPerDiamond);
    }

    private void addDiamondToPlayers() {
        for (Player player : playerGameData.players()) {
            if (diamondIndicator.hasDiamonds(player)) {
                itemGiver.giveDia(player);
            }
        }
    }

    private void addDiamondToChests() {
        for (ShulkerBox shulkerBox : diaChestGameData.getShulkerBoxes()) {
            if (diamondIndicator.hasDiamonds(shulkerBox)) {
                itemGiver.giveDia(shulkerBox);
            }
        }
    }

    public void stop() {
        increaserThread.cancel();
    }
}
