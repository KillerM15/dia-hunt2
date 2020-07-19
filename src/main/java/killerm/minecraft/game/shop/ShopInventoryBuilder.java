package killerm.minecraft.game.shop;

import killerm.minecraft.game.item.GameItem;
import killerm.minecraft.game.data.Team;
import killerm.minecraft.utilities.MinecraftConstants;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class ShopInventoryBuilder {
    private Inventory inventory;
    private ShopSlotInformation shopSlotInformation;
    private ShopItemCategoryItemStacks shopItemCategoryItemStacks;
    private int size;

    public ShopInventoryBuilder(int size, String name) {
        this(null, size, name);
    }

    public ShopInventoryBuilder(InventoryHolder owner, int size, String name) {
        inventory = Bukkit.createInventory(owner, size, name);
        shopSlotInformation = new ShopSlotInformation(ItemCategory.values());
        shopItemCategoryItemStacks = new ShopItemCategoryItemStacks();
        this.size = size;
    }

    public ShopInventoryBuilder(Inventory inventory, ShopSlotInformation shopSlotInformation, ShopItemCategoryItemStacks shopItemCategoryItemStacks, int size) {
        this.inventory = inventory;
        this.shopSlotInformation = shopSlotInformation;
        this.shopItemCategoryItemStacks = shopItemCategoryItemStacks;
        this.size = size;
    }

    public ShopInventoryBuilder withItem(ItemStack itemStack, ItemCategory itemCategory) {
        int slot = shopSlotInformation.getNextAvailibleSlot(itemCategory);
        inventory.setItem(slot, itemStack);

        return this;
    }

    public ShopInventoryBuilder withGlassPanes(Team team, int column) {
        for (int slot = column; slot < size; slot += MinecraftConstants.slotsInInventoryRow) {
            inventory.setItem(slot, GameItem.shopGlassPane(team));
        }

        return this;
    }

    public ShopInventoryBuilder withItemCategorySidebar(int column) {
        int slots = column;

        for (ItemCategory itemCategory : ItemCategory.values()) {
            inventory.setItem(slots, shopItemCategoryItemStacks.getItemStack(itemCategory));

            slots += MinecraftConstants.slotsInInventoryRow;
        }

        return this;
    }

    public Inventory build() {
        return inventory;
    }
}
