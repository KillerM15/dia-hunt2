package killerm.minecraft.game.shop;

import killerm.minecraft.game.data.Team;
import killerm.minecraft.game.item.GameItem;
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
    private GameItem gameItem = mock(GameItem.class);
    private static final int size = 54;
    private ShopInventoryBuilder shopInventoryBuilder = new ShopInventoryBuilder(inventory, shopSlotInformation, shopItemCategoryItemStacks, gameItem, size);

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

    @Test
    public void GIVEN_Team_and_glassPane_WHEN_build_withGlassPanes_THEN_glassPaneColumn_added() {
        // GIVEN
        Team team = Team.AQUA;
        ItemStack glassPane = mock(ItemStack.class);
        doReturn(glassPane).when(gameItem).shopGlassPane(team);

        // WHEN
        Inventory inventory = shopInventoryBuilder
                .withGlassPanes(team, 3)
                .build();

        // THEN
        Mockito.verify(inventory).setItem(3, glassPane);
        Mockito.verify(inventory).setItem(12, glassPane);
        Mockito.verify(inventory).setItem(21, glassPane);
        Mockito.verify(inventory).setItem(30, glassPane);
        Mockito.verify(inventory).setItem(39, glassPane);
        Mockito.verify(inventory).setItem(48, glassPane);
    }

    @Test
    public void GIVEN_itemStacks_for_sidebar_WHEN_build_withItemCategorySidebar_THEN_sidebar_items_added() {
        // GIVEN
        Team team = Team.AQUA;
        ItemStack glassPane = mock(ItemStack.class);
        doReturn(glassPane).when(gameItem).shopGlassPane(team);
        ItemStack itemStackBuilding = mock(ItemStack.class);
        doReturn(itemStackBuilding).when(shopItemCategoryItemStacks).getItemStack(ItemCategory.BUILDING);
        ItemStack itemStackMelee = mock(ItemStack.class);
        doReturn(itemStackMelee).when(shopItemCategoryItemStacks).getItemStack(ItemCategory.MELEE);
        ItemStack itemStackRanged = mock(ItemStack.class);
        doReturn(itemStackRanged).when(shopItemCategoryItemStacks).getItemStack(ItemCategory.RANGED);
        ItemStack itemStackEffects = mock(ItemStack.class);
        doReturn(itemStackEffects).when(shopItemCategoryItemStacks).getItemStack(ItemCategory.EFFECTS);
        ItemStack itemStackProtection = mock(ItemStack.class);
        doReturn(itemStackProtection).when(shopItemCategoryItemStacks).getItemStack(ItemCategory.PROTECTION);
        ItemStack itemStackTricky = mock(ItemStack.class);
        doReturn(itemStackTricky).when(shopItemCategoryItemStacks).getItemStack(ItemCategory.TRICKY);

        // WHEN
        Inventory inventory = shopInventoryBuilder
                .withItemCategorySidebar(1)
                .build();

        // THEN
        Mockito.verify(inventory).setItem(1, itemStackBuilding);
        Mockito.verify(inventory).setItem(10, itemStackMelee);
        Mockito.verify(inventory).setItem(19, itemStackRanged);
        Mockito.verify(inventory).setItem(28, itemStackEffects);
        Mockito.verify(inventory).setItem(37, itemStackProtection);
        Mockito.verify(inventory).setItem(46, itemStackTricky);
    }
}