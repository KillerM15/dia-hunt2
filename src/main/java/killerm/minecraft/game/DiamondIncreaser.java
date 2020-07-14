package killerm.minecraft.game;

import killerm.minecraft.DiaHuntPlugin;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class DiamondIncreaser {
    private DiaChestGameData diaChestGameData;
    private PlayerGameData playerGameData;
    private DiamondIndicator diamondIndicator;
    private ItemGiver itemGiver;
    private BukkitRunnable increaserThread;

    public DiamondIncreaser(DiaChestGameData diaChestGameData, PlayerGameData playerGameData) {
        this.diaChestGameData = diaChestGameData;
        this.playerGameData = playerGameData;
        this.diamondIndicator = new DiamondIndicator();
        this.itemGiver = new ItemGiver();
    }

    public void start(int ticksPerDiamond) {
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
