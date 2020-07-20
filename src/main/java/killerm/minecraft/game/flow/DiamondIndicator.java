package killerm.minecraft.game.flow;

import killerm.minecraft.game.item.GameItem;
import killerm.minecraft.utilities.ItemEquals;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DiamondIndicator {
    private GameItem gameItem = new GameItem();

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

            if (ItemEquals.equals(itemStack, gameItem.diamond())) {
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

            if (ItemEquals.equals(itemStack, gameItem.diamond())) {
                return true;
            }
        }

        return false;
    }
}
