package killerm.minecraft.manager;


import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;

public class ItemManager {
    public void setChestplate(Player player, ItemStack itemStack) {
        player.getInventory().setChestplate(itemStack);
    }

    public void give(Player player, ItemStack itemStack) {
        player.getInventory().addItem(itemStack);
    }

    public void give(ShulkerBox shulkerBox, ItemStack itemStack) {
        shulkerBox.getInventory().addItem(itemStack);
    }
}