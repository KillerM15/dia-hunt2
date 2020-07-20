package killerm.minecraft.game.shop;

import killerm.minecraft.DiaHuntPlugin;
import killerm.minecraft.data.DiaConfig;
import killerm.minecraft.error.ItemNotFoundException;
import killerm.minecraft.game.item.GameItem;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.mock;

class ItemPriceTest {
    private DiaHuntPlugin diaHuntPlugin = mock(DiaHuntPlugin.class);
    private FileConfiguration config = mock(FileConfiguration.class);
    private GameItem gameItem = mock(GameItem.class);
    private ItemPrice itemPrice;

    @BeforeEach
    public void setup() {
        doReturn(config).when(diaHuntPlugin).getConfig();
        DiaConfig.setPlugin(diaHuntPlugin);

        // Initialize with gameItem.melon()
        String itemName = "melon";
        ItemMeta itemMeta = mock(ItemMeta.class);
        doReturn(itemName).when(itemMeta).getDisplayName();
        ItemStack itemStack = mock(ItemStack.class);
        doReturn(itemMeta).when(itemStack).getItemMeta();
        doReturn(itemStack).when(gameItem).melon();

        itemPrice = new ItemPrice(gameItem);
    }

    @Test
    public void GIVEN_PRICE_MELON_WHEN_getPrice_THEN_return_price() {
        // GIVEN
        doReturn(new Double(20.0)).when(config).get(DiaConfig.PRICE_MELON.toString());

        // WHEN
        int price = itemPrice.getPrice(gameItem.melon());

        // THEN
        assertEquals(20, price);
    }

    @Test
    public void GIVEN_unknown_item_WHEN_getPrice_THEN_Exception() {
        // GIVEN
        String itemName = "unknown name";
        ItemMeta itemMeta = mock(ItemMeta.class);
        doReturn(itemName).when(itemMeta).getDisplayName();
        ItemStack unknownItemStack = mock(ItemStack.class);
        doReturn(itemMeta).when(unknownItemStack).getItemMeta();
        doReturn(unknownItemStack).when(gameItem).melon();

        // WHEN
        ItemNotFoundException thrown = assertThrows(
                ItemNotFoundException.class,
                () -> itemPrice.getPrice(unknownItemStack),
                "Expected to throw, but didn't"
        );

        // THEN
        assertTrue(thrown.getMessage().equals(unknownItemStack.getItemMeta().getDisplayName() + " is not a valid shop item"));
    }

}