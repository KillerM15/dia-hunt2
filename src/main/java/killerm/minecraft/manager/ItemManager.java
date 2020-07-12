package killerm.minecraft.manager;


import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

public class ItemManager {
    public void setChestplate(Set<Player> players, ItemStack itemStack) {
        for (Player player : players) {
            setChestplate(player, itemStack);
        }
    }

    public void setChestplate(Player player, ItemStack itemStack) {
        player.getInventory().setChestplate(itemStack);
    }

    public void give(Set<Player> players, ItemStack itemStack) {
        for (Player player : players) {
            give(player, itemStack);
        }
    }

    public void give(Player player, ItemStack itemStack) {
        player.getInventory().addItem(itemStack);
    }

    public void give(ShulkerBox shulkerBox, ItemStack itemStack) {
        shulkerBox.getInventory().addItem(itemStack);
    }
}