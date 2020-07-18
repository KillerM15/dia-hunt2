package killerm.minecraft.utilities;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class InventoryCopy {
    public void copyItemstacksWithName(Player source, Player dest, String itemDisplayName) {
        ItemStack[] itemStacks = source.getInventory().getContents();

        for (ItemStack itemStack : itemStacks) {
            if (itemStack == null) {
                continue;
            }

            String displayName = itemStack.getItemMeta().getDisplayName();

            if (displayName.equals(itemDisplayName)) {
                dest.getInventory().addItem(itemStack);
            }
        }
    }
}
