package killerm.minecraft.utilities;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.junit.jupiter.api.Test;

import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.mock;

class ItemEqualsTest {
    @Test
    public void GIVEN_itemStack1_null_and_itemStack2_null_WHEN_equals_THEN_false() {
        ItemStack itemStack1 = null;
        ItemStack itemStack2 = null;

        assert (ItemEquals.equals(itemStack1, itemStack2));
    }

    @Test
    public void GIVEN_itemStack1_null_and_itemStack2_not_null_WHEN_equals_THEN_false() {
        ItemStack itemStack1 = null;
        ItemStack itemStack2 = mock(ItemStack.class);

        assert (!ItemEquals.equals(itemStack1, itemStack2));
    }

    @Test
    public void GIVEN_itemStack1_not_null_and_itemStack2_null_WHEN_equals_THEN_false() {
        ItemStack itemStack1 = mock(ItemStack.class);
        ItemStack itemStack2 = null;

        assert (!ItemEquals.equals(itemStack1, itemStack2));
    }

    @Test
    public void GIVEN_itemStack1_with_itemMeta1_null_and_itemStack2_with_itemMeta2_null_WHEN_equals_THEN_true() {
        ItemStack itemStack1 = mock(ItemStack.class);
        ItemStack itemStack2 = mock(ItemStack.class);
        ItemMeta itemMeta1 = null;
        ItemMeta itemMeta2 = null;
        doReturn(itemMeta1).when(itemStack1).getItemMeta();
        doReturn(itemMeta2).when(itemStack2).getItemMeta();

        assert (ItemEquals.equals(itemStack1, itemStack2));
    }

    @Test
    public void GIVEN_itemStack1_with_itemMeta1_not_null_and_itemStack2_with_itemMeta2_null_WHEN_equals_THEN_false() {
        ItemStack itemStack1 = mock(ItemStack.class);
        ItemStack itemStack2 = mock(ItemStack.class);
        ItemMeta itemMeta1 = mock(ItemMeta.class);
        ItemMeta itemMeta2 = null;
        doReturn(itemMeta1).when(itemStack1).getItemMeta();
        doReturn(itemMeta2).when(itemStack2).getItemMeta();

        assert (!ItemEquals.equals(itemStack1, itemStack2));
    }

    @Test
    public void GIVEN_itemStack1_with_itemMeta1_null_and_itemStack2_with_itemMeta2_not_null_WHEN_equals_THEN_false() {
        ItemStack itemStack1 = mock(ItemStack.class);
        ItemStack itemStack2 = mock(ItemStack.class);
        ItemMeta itemMeta1 = null;
        ItemMeta itemMeta2 = mock(ItemMeta.class);
        doReturn(itemMeta1).when(itemStack1).getItemMeta();
        doReturn(itemMeta2).when(itemStack2).getItemMeta();

        assert (!ItemEquals.equals(itemStack1, itemStack2));
    }

    @Test
    public void GIVEN_itemStacks_with_different_name_WHEN_equals_THEN_false() {
        ItemStack itemStack1 = mock(ItemStack.class);
        ItemStack itemStack2 = mock(ItemStack.class);
        ItemMeta itemMeta1 = mock(ItemMeta.class);
        ItemMeta itemMeta2 = mock(ItemMeta.class);
        doReturn(itemMeta1).when(itemStack1).getItemMeta();
        doReturn(itemMeta2).when(itemStack2).getItemMeta();
        String itemName1 = "name";
        String itemName2 = "not same name";
        doReturn(itemName1).when(itemMeta1).getDisplayName();
        doReturn(itemName2).when(itemMeta2).getDisplayName();

        assert (!ItemEquals.equals(itemStack1, itemStack2));
    }

    @Test
    public void GIVEN_itemStacks_with_same_name_WHEN_equals_THEN_true() {
        ItemStack itemStack1 = mock(ItemStack.class);
        ItemStack itemStack2 = mock(ItemStack.class);
        ItemMeta itemMeta1 = mock(ItemMeta.class);
        ItemMeta itemMeta2 = mock(ItemMeta.class);
        doReturn(itemMeta1).when(itemStack1).getItemMeta();
        doReturn(itemMeta2).when(itemStack2).getItemMeta();
        String itemName1 = "name";
        String itemName2 = "name";
        doReturn(itemName1).when(itemMeta1).getDisplayName();
        doReturn(itemName2).when(itemMeta2).getDisplayName();

        assert (ItemEquals.equals(itemStack1, itemStack2));
    }
}