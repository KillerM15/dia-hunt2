package killerm.minecraft.utilities;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemEquals {
    public static boolean equals(ItemStack itemStack1, ItemStack itemStack2) {
        if (bothNull(itemStack1, itemStack2)) {
            return true;
        } else if (oneNulloneNotNull(itemStack1, itemStack2)) {
            return false;
        }

        ItemMeta itemMeta1 = itemStack1.getItemMeta();
        ItemMeta itemMeta2 = itemStack2.getItemMeta();

        if (bothNull(itemMeta1, itemMeta2)) {
            return true;
        } else if (oneNulloneNotNull(itemMeta1, itemMeta2)) {
            return false;
        }

        String name1 = itemMeta1.getDisplayName();
        String name2 = itemMeta2.getDisplayName();

        return name1.equals(name2);
    }

    private static boolean bothNull(Object object1, Object object2) {
        return object1 == null && object2 == null;
    }

    private static boolean oneNulloneNotNull(Object object1, Object object2) {
        return (object1 == null && object2 != null) || (object1 != null && object2 == null);
    }
}
