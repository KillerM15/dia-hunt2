package killerm.minecraft.game.shop;

import killerm.minecraft.game.item.GameItem;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class ShopItemCategoryItemStacks {
    private GameItem gameItem = new GameItem();
    private Map<ItemCategory, ItemStack> itemStackOfItemCategory = new HashMap<>();

    public ShopItemCategoryItemStacks() {
        itemStackOfItemCategory.put(ItemCategory.BUILDING, gameItem.shopBuildingItem());
        itemStackOfItemCategory.put(ItemCategory.MELEE, gameItem.shopMeleeItem());
        itemStackOfItemCategory.put(ItemCategory.RANGED, gameItem.shopRangedItem());
        itemStackOfItemCategory.put(ItemCategory.EFFECTS, gameItem.shopEffectsItem());
        itemStackOfItemCategory.put(ItemCategory.PROTECTION, gameItem.shopProtectionItem());
        itemStackOfItemCategory.put(ItemCategory.TRICKY, gameItem.shopTrickyItem());
    }

    public ItemStack getItemStack(ItemCategory itemCategory) {
        return itemStackOfItemCategory.get(itemCategory);
    }
}
