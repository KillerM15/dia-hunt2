package killerm.minecraft.game.flow;

import killerm.minecraft.communication.Message;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DiamondIndicator {
    public boolean hasDiamonds(Player player) {
        return amount(player) > 0;
    }

    public int amount(Player player) {
        ItemStack[] content = player.getInventory().getContents();
        int amount = 0;

        for (ItemStack itemStack : content) {
            if (itemStack == null) {
                continue;
            }

            if (itemStack.getItemMeta().getDisplayName().equals(Message.ITEM_DIAMOND)) {
                amount += itemStack.getAmount();
            }
        }

        return amount;
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
