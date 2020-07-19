package killerm.minecraft.game.shop;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.mock;

class ShopInventoryBuilderTest {
    private Inventory inventory = mock(Inventory.class);
    private ShopSlotInformation shopSlotInformation = mock(ShopSlotInformation.class);
    private ShopItemCategoryItemStacks shopItemCategoryItemStacks = mock(ShopItemCategoryItemStacks.class);
    private ShopInventoryBuilder shopInventoryBuilder = new ShopInventoryBuilder(inventory, shopSlotInformation, shopItemCategoryItemStacks, 54);

    @Test
    public void GIVEN_itemCategory_and_slot_and_itemstack_WHEN_build_withItem_THEN_item_added_to_slot() {
        // GIVEN
        ItemCategory itemCategory = ItemCategory.EFFECTS;
        int slot = 20;
        ItemStack itemStack = mock(ItemStack.class);
        doReturn(slot).when(shopSlotInformation).getNextAvailibleSlot(itemCategory);

        // WHEN
        Inventory inventory = shopInventoryBuilder
                .withItem(itemStack, itemCategory)
                .build();

        // THEN
        Mockito.verify(inventory).setItem(slot, itemStack);
    }
}