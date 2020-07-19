package killerm.minecraft.game;

import killerm.minecraft.utilities.MinecraftConstants;

import java.util.HashMap;
import java.util.Map;

public class ShopSlotInformation {
    private Map<ItemCategory, Integer> amountOfItems = new HashMap<>();
    private Map<ItemCategory, Integer> slotOfFirstItem = new HashMap<>();

    public ShopSlotInformation(ItemCategory[] itemCategories) {
        final int startingPositionOfRow = 2;
        int slot = startingPositionOfRow;

        for (ItemCategory itemCategory : itemCategories) {
            amountOfItems.put(itemCategory, 0);
            slotOfFirstItem.put(itemCategory, slot);

            slot += MinecraftConstants.slotsInInventoryRow;
        }
    }

    public int getNextAvailibleSlot(ItemCategory itemCategory) {
        int slot = slotOfFirstItem.get(itemCategory) + amountOfItems.get(itemCategory);
        incrementAmountOfItems(itemCategory);

        return slot;
    }

    private void incrementAmountOfItems(ItemCategory itemCategory) {
        int oldAmountOfItems = amountOfItems.get(itemCategory);
        amountOfItems.put(itemCategory, oldAmountOfItems + 1);
    }
}
