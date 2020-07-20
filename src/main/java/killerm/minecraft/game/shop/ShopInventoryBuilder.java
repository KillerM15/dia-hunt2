package killerm.minecraft.game.shop;

import killerm.minecraft.game.data.Team;
import killerm.minecraft.game.item.GameItem;
import killerm.minecraft.utilities.MinecraftConstants;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class ShopInventoryBuilder {
    private Inventory inventory;
    private ShopSlotInformation shopSlotInformation;
    private ShopItemCategoryItemStacks shopItemCategoryItemStacks;
    private GameItem gameItem;
    private int size;

    public ShopInventoryBuilder(int size, String name) {
        this(null, size, name);
    }

    public ShopInventoryBuilder(InventoryHolder owner, int size, String name) {
        this.inventory = Bukkit.createInventory(owner, size, name);
        this.shopSlotInformation = new ShopSlotInformation(ItemCategory.values());
        this.shopItemCategoryItemStacks = new ShopItemCategoryItemStacks();
        this.gameItem = new GameItem();
        this.size = size;
    }

    public ShopInventoryBuilder(Inventory inventory, ShopSlotInformation shopSlotInformation, ShopItemCategoryItemStacks shopItemCategoryItemStacks, GameItem gameItem, int size) {
        this.inventory = inventory;
        this.shopSlotInformation = shopSlotInformation;
        this.shopItemCategoryItemStacks = shopItemCategoryItemStacks;
        this.gameItem = gameItem;
        this.size = size;
    }

    public ShopInventoryBuilder withItem(ItemStack itemStack, ItemCategory itemCategory) {
        int slot = shopSlotInformation.getNextAvailibleSlot(itemCategory);
        inventory.setItem(slot, itemStack);

        return this;
    }

    public ShopInventoryBuilder withGlassPanes(Team team, int column) {
        for (int slot = column; slot < size; slot += MinecraftConstants.SLOTS_IN_INVENTORY_ROW) {
            inventory.setItem(slot, gameItem.shopGlassPane(team));
        }

        return this;
    }

    public ShopInventoryBuilder withItemCategorySidebar(int column) {
        int slots = column;

        for (ItemCategory itemCategory : ItemCategory.values()) {
            inventory.setItem(slots, shopItemCategoryItemStacks.getItemStack(itemCategory));

            slots += MinecraftConstants.SLOTS_IN_INVENTORY_ROW;
        }

        return this;
    }

    public Inventory build() {
        return inventory;
    }
}
