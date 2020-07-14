package killerm.minecraft.game;

import killerm.minecraft.communication.Message;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DiamondIndicator {
    public boolean hasDiamonds(Player player) {
        ItemStack[] content = player.getInventory().getContents();

        for (ItemStack itemStack : content) {
            if (itemStack == null) {
                continue;
            }

            if (itemStack.getItemMeta().getDisplayName().equals(Message.ITEM_DIAMOND)) {
                return true;
            }
        }

        return false;
    }

    public boolean hasDiamonds(ShulkerBox shulkerBox) {
        ItemStack[] content = shulkerBox.getInventory().getContents();

        for (ItemStack itemStack : content) {
            if (itemStack == null) {
                continue;
            }

            if (itemStack.getItemMeta().getDisplayName().equals(Message.ITEM_DIAMOND)) {
                return true;
            }
        }

        return false;
    }
}
