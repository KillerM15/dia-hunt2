package killerm.minecraft.game;

import killerm.minecraft.communication.Message;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.mock;

class DiamondIndicatorTest {
    private DiamondIndicator diamondIndicator = new DiamondIndicator();

    @Test
    public void GIVEN_player_with_diamonds_WHEN_hasDiamonds_THEN_true() {
        Player player = mock(Player.class);
        doReturn(playerInventoryWithDiamonds(20)).when(player).getInventory();

        assert (diamondIndicator.hasDiamonds(player));
    }

    private PlayerInventory playerInventoryWithDiamonds(int amount) {
        PlayerInventory inventory = mock(PlayerInventory.class);

        ItemStack[] content = new ItemStack[1];
        ItemStack diamond = mock(ItemStack.class);
        ItemMeta itemMeta = mock(ItemMeta.class);
        String displayName = Message.ITEM_DIAMOND;

        doReturn(displayName).when(itemMeta).getDisplayName();
        doReturn(itemMeta).when(diamond).getItemMeta();
        doReturn(amount).when(diamond).getAmount();
        content[0] = diamond;
        doReturn(content).when(inventory).getContents();

        return inventory;
    }

    @Test
    public void GIVEN_player_with_empty_inventory_WHEN_hasDiamonds_THEN_false() {
        Player player = mock(Player.class);
        doReturn(emptyPlayerInventory()).when(player).getInventory();

        assert (!diamondIndicator.hasDiamonds(player));
    }

    private PlayerInventory emptyPlayerInventory() {
        PlayerInventory inventory = mock(PlayerInventory.class);

        ItemStack[] content = new ItemStack[1];
        content[0] = null;
        doReturn(content).when(inventory).getContents();

        return inventory;
    }

    @Test
    public void GIVEN_player_with_20_diamonds_WHEN_amount_THEN_20() {
        Player player = mock(Player.class);
        doReturn(playerInventoryWithDiamonds(20)).when(player).getInventory();

        assertEquals(20, diamondIndicator.amount(player));
    }

    @Test
    public void GIVEN_shulkerBox_with_diamonds_WHEN_hasDiamonds_THEN_true() {
        ShulkerBox shulkerBox = mock(ShulkerBox.class);
        doReturn(inventoryWithDiamonds(30)).when(shulkerBox).getInventory();

        assert (diamondIndicator.hasDiamonds(shulkerBox));
    }

    private Inventory inventoryWithDiamonds(int amount) {
        Inventory inventory = mock(Inventory.class);

        ItemStack[] content = new ItemStack[1];
        ItemStack diamond = mock(ItemStack.class);
        ItemMeta itemMeta = mock(ItemMeta.class);
        String displayName = Message.ITEM_DIAMOND;

        doReturn(displayName).when(itemMeta).getDisplayName();
        doReturn(itemMeta).when(diamond).getItemMeta();
        doReturn(amount).when(diamond).getAmount();
        content[0] = diamond;
        doReturn(content).when(inventory).getContents();

        return inventory;
    }

    @Test
    public void GIVEN_shulkerBox_with_empty_inventory_WHEN_hasDiamonds_THEN_false() {
        ShulkerBox shulkerBox = mock(ShulkerBox.class);
        doReturn(emptyInventory()).when(shulkerBox).getInventory();

        assert (!diamondIndicator.hasDiamonds(shulkerBox));
    }

    private Inventory emptyInventory() {
        Inventory inventory = mock(Inventory.class);

        ItemStack[] content = new ItemStack[1];
        content[0] = null;
        doReturn(content).when(inventory).getContents();

        return inventory;
    }

}