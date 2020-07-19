package killerm.minecraft.game.shop;

import killerm.minecraft.game.item.GameItem;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class ShopItemCategoryItemStacks {
    private Map<ItemCategory, ItemStack> itemStackOfItemCategory = new HashMap<>();

    public ShopItemCategoryItemStacks() {
        itemStackOfItemCategory.put(ItemCategory.BUILDING, GameItem.shopBuildingItem());
        itemStackOfItemCategory.put(ItemCategory.MELEE, GameItem.shopMeleeItem());
        itemStackOfItemCategory.put(ItemCategory.RANGED, GameItem.shopRangedItem());
        itemStackOfItemCategory.put(ItemCategory.EFFECTS, GameItem.shopEffectsItem());
        itemStackOfItemCategory.put(ItemCategory.PROTECTION, GameItem.shopProtectionItem());
        itemStackOfItemCategory.put(ItemCategory.TRICKY, GameItem.shopTrickyItem());
    }

    public ItemStack getItemStack(ItemCategory itemCategory) {
        return itemStackOfItemCategory.get(itemCategory);
    }
}
