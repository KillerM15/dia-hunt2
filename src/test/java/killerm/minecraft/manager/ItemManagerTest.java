package killerm.minecraft.manager;

import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.mock;

class ItemManagerTest {
    ItemManager itemManager = new ItemManager();

    @Test
    public void GIVEN_player_and_itemStack_WHEN_setChestplate_THEN_itemStack_as_chestplate_set() {
        //GIVEN
        Player player = mock(Player.class);
        PlayerInventory inventory = mock(PlayerInventory.class);
        doReturn(inventory).when(player).getInventory();
        ItemStack itemStack = mock(ItemStack.class);

        // WHEN
        itemManager.setChestplate(player, itemStack);

        // THEN
        Mockito.verify(inventory).setChestplate(itemStack);
    }

    @Test
    public void GIVEN_player_and_itemStack_WHEN_give_THEN_itemStack_added_to_inventory() {
        // GIVEN
        Player player = mock(Player.class);
        PlayerInventory inventory = mock(PlayerInventory.class);
        doReturn(inventory).when(player).getInventory();
        ItemStack itemStack = mock(ItemStack.class);

        // WHEN
        itemManager.give(player, itemStack);

        // THEN
        Mockito.verify(inventory).addItem(itemStack);
    }

    @Test
    public void GIVEN_shulkerBox_and_itemStack_WHEN_give_THEN_itemstack_added() {
        // GIVEN
        ShulkerBox shulkerBox = mock(ShulkerBox.class);
        Inventory inventory = mock(Inventory.class);
        doReturn(inventory).when(shulkerBox).getInventory();
        ItemStack itemStack = mock(ItemStack.class);

        // WHEN
        itemManager.give(shulkerBox, itemStack);

        // THEN
        Mockito.verify(inventory).addItem(itemStack);
    }
}