package killerm.minecraft.game;

import killerm.minecraft.manager.ItemManager;
import killerm.minecraft.utilities.Team;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.Player;

import java.util.Collection;

public class ItemGiver {
    private ItemManager itemManager = new ItemManager();

    public void giveDia(ShulkerBox shulkerBox) {
        itemManager.give(shulkerBox, GameItem.diamond());
    }

    public void giveDia(Collection<Player> players) {
        for (Player player : players) {
            giveDia(player);
        }
    }

    public void giveDia(Player player) {
        itemManager.give(player, GameItem.diamond());
    }

    public void giveBaseAquaItems(Collection<Player> players) {
        for (Player player : players) {
            giveBaseAquaItems(player);
        }
    }

    public void giveBaseAquaItems(Player player) {
        itemManager.setChestplate(player, GameItem.chestplate(Team.AQUA));
        itemManager.give(player, GameItem.knockbackStick());
    }

    public void giveBaseLavaItems(Collection<Player> players) {
        for (Player player : players) {
            giveBaseLavaItems(player);
        }
    }

    public void giveBaseLavaItems(Player player) {
        itemManager.setChestplate(player, GameItem.chestplate(Team.LAVA));
        itemManager.give(player, GameItem.knockbackStick());
    }

    public void giveAquaDiaChest(Player player) {
        itemManager.give(player, GameItem.diaChest(Team.AQUA));
    }

    public void giveLavaDiaChest(Player player) {
        itemManager.give(player, GameItem.diaChest(Team.LAVA));
    }
}
