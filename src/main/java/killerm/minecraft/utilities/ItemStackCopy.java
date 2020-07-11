package killerm.minecraft.utilities;

import org.bukkit.inventory.ItemStack;

public class ItemStackCopy {
    public ItemStack[] getCopy(ItemStack[] itemStacks) {
        ItemStack[] copy = new ItemStack[itemStacks.length];

        for (int i = 0; i < itemStacks.length; i++) {
            if (itemStacks[i] != null) {
                copy[i] = new ItemStack(itemStacks[i]);
            }
        }

        return copy;
    }
}