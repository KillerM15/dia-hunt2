package killerm.minecraft.data;

import killerm.minecraft.utilities.ItemStackCopy;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class PlayerBackup {
    private Map<Player, PlayerData> savedPlayers = new HashMap<>();
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
        PlayerData playerData = new PlayerData();

        playerData.gameMode = player.getGameMode();
        playerData.itemStacks = itemStackCopy.getCopy(player.getInventory().getContents());
        playerData.itemStacksArmor = itemStackCopy.getCopy(player.getInventory().getArmorContents());
        playerData.location = player.getLocation();
        playerData.potionEffects = player.getActivePotionEffects();
        playerData.health = player.getHealth();
        playerData.foodLevel = player.getFoodLevel();

        savedPlayers.put(player, playerData);
    }

    public void restore(Player player) {
        PlayerData playerData = savedPlayers.get(player);

        if (playerData == null) {
            throw new RuntimeException("Player not found!");
        }

        player.setGameMode(playerData.gameMode);
        player.getInventory().setContents(playerData.itemStacks);
        player.getInventory().setArmorContents(playerData.itemStacksArmor);
        player.teleport(playerData.location);
        player.addPotionEffects(playerData.potionEffects);
        player.setHealth(playerData.health);
        player.setFoodLevel(playerData.foodLevel);

        savedPlayers.remove(player);
    }
}
