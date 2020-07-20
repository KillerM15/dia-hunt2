package killerm.minecraft.game.shop;

import killerm.minecraft.communication.Message;
import killerm.minecraft.communication.Printer;
import killerm.minecraft.error.ItemNotFoundException;
import killerm.minecraft.error.NotEnoughItemsException;
import killerm.minecraft.game.data.Team;
import killerm.minecraft.game.flow.DiamondIndicator;
import killerm.minecraft.game.interaction.ItemGiver;
import killerm.minecraft.game.item.GameItem;
import killerm.minecraft.utilities.InventoryItemRemover;
import killerm.minecraft.utilities.ItemEquals;
import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Shop { // TODO: This class is untested!
    private static final int SLOTS = 54;
    private Printer printer = new Printer();
    private DiamondIndicator diamondIndicator = new DiamondIndicator();
    private ItemPrice itemPrice = new ItemPrice();
    private ItemGiver itemGiver = new ItemGiver();
    private InventoryItemRemover inventoryItemRemover = new InventoryItemRemover();
    private GameItem gameItem = new GameItem();
    private Inventory aquaInventory;
    private Inventory lavaInventory;

    public Shop() {
        initializeInventories();
    }

    private void initializeInventories() {
        aquaInventory = buildAquaInventory();
        lavaInventory = buildLavaInventory();
    }

    private Inventory buildAquaInventory() {
        Inventory inventory = new ShopInventoryBuilder(SLOTS, getShopName(Team.AQUA))
                .withItem(gameItem.melon(), ItemCategory.BUILDING)
                .withItemCategorySidebar(0)
                .withGlassPanes(Team.AQUA, 1)
                .build();

        return inventory;
    }

    private Inventory buildLavaInventory() {
        Inventory inventory = new ShopInventoryBuilder(SLOTS, getShopName(Team.LAVA))
                .withItem(gameItem.melon(), ItemCategory.BUILDING)
                .withItemCategorySidebar(0)
                .withGlassPanes(Team.LAVA, 1)
                .build();

        return inventory;
    }

    private String getShopName(Team team) {
        StringBuilder sb = new StringBuilder();

        String color = team.equals(Team.LAVA) ? Message.TEAM_LAVA : Message.TEAM_AQUA;
        sb.append(color);
        sb.append(Message.BOLD);
        sb.append(StringUtils.capitalize(team.toString()));
        sb.append(Message.SPACE);
        sb.append(Message.SHOP_NAME);

        return sb.toString();
    }

    public Inventory getInventory(Team team) {
        return team.equals(Team.AQUA) ? aquaInventory : lavaInventory;
    }

    public boolean isShopItem(ItemStack itemStack) {
        return ItemEquals.equals(itemStack, gameItem.shop(Team.AQUA)) || ItemEquals.equals(itemStack, gameItem.shop(Team.LAVA));
    }

    public void buyItem(Player player, ItemStack itemStack) throws ItemNotFoundException {
        try {
            int price = itemPrice.getPrice(itemStack);
            inventoryItemRemover.removeItemsMatchingDisplayName(player, gameItem.diamond(), price);
        } catch (ItemNotFoundException e) {
            return;
        } catch (NotEnoughItemsException e) {
            printer.tellError(player, Message.NOT_ENOUGH_DIAS);
            return;
        }

        itemGiver.giveBoughtFromShop(player, itemStack); // TODO: Lore last line remover
    }

    public void buyItemUntilOneDiaLeft(Player player, ItemStack itemStack) {
        do {
            buyItem(player, itemStack);
        } while (diamondIndicator.amount(player) > itemPrice.getPrice(itemStack));
    }

    public boolean isShop(Inventory inventory) {
        return inventory == aquaInventory || inventory == lavaInventory;
    }
}
