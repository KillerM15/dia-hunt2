package killerm.minecraft.utilities;

import killerm.minecraft.communication.Message;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.mock;

class InventoryCopyTest {
    private InventoryCopy inventoryCopy = new InventoryCopy();

    @Test
    public void GIVEN_player_source_with_diamonds_in_inventory_and_player_dest_WHEN_copyItemStacksWithName_THEN_items_added_in_dest_inventory() {
        // GIVEN
        PlayerInventory inventory = mock(PlayerInventory.class);

        ItemStack[] content = new ItemStack[1];
        ItemStack diamond = mock(ItemStack.class);
        ItemMeta itemMeta = mock(ItemMeta.class);
        String displayName = Message.ITEM_DIAMOND;

        doReturn(displayName).when(itemMeta).getDisplayName();
        doReturn(itemMeta).when(diamond).getItemMeta();
        doReturn(20).when(diamond).getAmount();
        content[0] = diamond;
        doReturn(content).when(inventory).getContents();

        Player source = mock(Player.class);
        doReturn(inventory).when(source).getInventory();

        Player dest = mock(Player.class);
        PlayerInventory inventoryDest = mock(PlayerInventory.class);
        doReturn(inventoryDest).when(dest).getInventory();

        // WHEN
        inventoryCopy.copyItemstacksWithName(source, dest, displayName);

        // THEN
        Mockito.verify(inventoryDest).addItem(content);
    }
}