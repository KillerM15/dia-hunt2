package killerm.minecraft.data;

import killerm.minecraft.utilities.ItemStackCopy;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class PlayerBackup {
    private Map<Player, PlayerBackupData> savedPlayers = new HashMap<>();
    private ItemStackCopy itemStackCopy;

    public PlayerBackup() {
        this.itemStackCopy = new ItemStackCopy();
    }

    public PlayerBackup(ItemStackCopy itemStackCopy) {
        this.itemStackCopy = itemStackCopy;
    }

    public void backup(Collection<Player> players) {
        for (Player player : players) {
            backup(player);
        }
    }

    public void backup(Player player) {
        PlayerBackupData playerBackupData = new PlayerBackupData();

        playerBackupData.gameMode = player.getGameMode();
        playerBackupData.itemStacks = itemStackCopy.getCopy(player.getInventory().getContents());
        playerBackupData.itemStacksArmor = itemStackCopy.getCopy(player.getInventory().getArmorContents());
        playerBackupData.location = player.getLocation();
        playerBackupData.potionEffects = player.getActivePotionEffects();
        playerBackupData.health = player.getHealth();
        playerBackupData.foodLevel = player.getFoodLevel();

        savedPlayers.put(player, playerBackupData);
    }

    public void restore(Player player) {
        PlayerBackupData playerBackupData = savedPlayers.get(player);

        if (playerBackupData == null) {
            throw new RuntimeException("Player not found!");
        }

        player.setGameMode(playerBackupData.gameMode);
        player.getInventory().setContents(playerBackupData.itemStacks);
        player.getInventory().setArmorContents(playerBackupData.itemStacksArmor);
        player.teleport(playerBackupData.location);
        player.addPotionEffects(playerBackupData.potionEffects);
        player.setHealth(playerBackupData.health);
        player.setFoodLevel(playerBackupData.foodLevel);

        savedPlayers.remove(player);
    }
}
