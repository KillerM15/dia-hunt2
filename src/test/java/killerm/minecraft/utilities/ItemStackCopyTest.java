package killerm.minecraft.utilities;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.Test;

import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.junit.jupiter.api.Assertions.*;

class ItemStackCopyTest {
    private ItemStackCopy itemStackCopy = new ItemStackCopy();

    @Test
    public void GIVEN_itemStacks_with_type_and_amount_WHEN_getCopy_THEN_items_not_equal_and_amount_and_type_the_same() {
        // GIVEN
        ItemStack itemStack0 = mock(ItemStack.class);
        ItemStack itemStack1 = mock(ItemStack.class);
        doReturn(20).when(itemStack0).getAmount();
        doReturn(Material.ACACIA_BOAT).when(itemStack0).getType();
        doReturn(36).when(itemStack1).getAmount();
        doReturn(Material.STICK).when(itemStack1).getType();

        ItemStack[] itemStacksSource = new ItemStack[]{itemStack0, itemStack1};

        // WHEN
        ItemStack[] itemStacksDest = itemStackCopy.getCopy(itemStacksSource);

        // THEN
        assertNotEquals(itemStacksSource[0], itemStacksDest[0]);
        assertNotEquals(itemStacksSource[1], itemStacksDest[1]);
        assertEquals(itemStacksSource[0].getAmount(), itemStacksDest[0].getAmount());
        assertEquals(itemStacksSource[0].getType(), itemStacksDest[0].getType());
        assertEquals(itemStacksSource[1].getAmount(), itemStacksDest[1].getAmount());
        assertEquals(itemStacksSource[1].getType(), itemStacksDest[1].getType());
    }
}