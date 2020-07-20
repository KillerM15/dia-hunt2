package killerm.minecraft.utilities;

import killerm.minecraft.error.NotEnoughItemsException;
import killerm.minecraft.game.flow.DiamondIndicator;
import killerm.minecraft.game.item.GameItem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class InventoryItemRemover {
    private GameItem gameItem;
    private DiamondIndicator diamondIndicator;

    public InventoryItemRemover() {
        this.gameItem = new GameItem();
        this.diamondIndicator = new DiamondIndicator();
    }

    public InventoryItemRemover(GameItem gameItem, DiamondIndicator diamondIndicator) {
        this.gameItem = gameItem;
        this.diamondIndicator = diamondIndicator;
    }

    public void removeItemsMatchingDisplayName(Player player, ItemStack itemStack, int amountToRemove) throws NotEnoughItemsException {
        int amountAfterRemove = amountOfItemInInventory(player, itemStack) - amountToRemove;

        if (amountAfterRemove < 0) {
            throw new NotEnoughItemsException("Not enough items to remove");
        }

        removeItemsMatchingDisplayName(player, itemStack);
        giveItem(player, itemStack, amountAfterRemove);
    }

    private int amountOfItemInInventory(Player player, ItemStack itemStackToLookFor) {
        int amount = 0;
        ItemStack[] inventoryContent = player.getInventory().getContents();

        for (ItemStack itemStack : inventoryContent) {
            if (itemStack == null) {
                continue;
            }

            if (ItemEquals.equals(itemStack, itemStackToLookFor)) {
                amount += itemStack.getAmount();
            }
        }

        return amount;
    }

    private void removeItemsMatchingDisplayName(Player player, ItemStack itemStackToRemove) {
        ItemStack[] inventoryContent = player.getInventory().getContents();

        for (ItemStack itemStack : inventoryContent) {
            if (itemStack == null) {
                continue;
            }

            if (ItemEquals.equals(itemStack, itemStackToRemove)) {
                player.getInventory().remove(itemStack);
            }
        }
    }

    private void giveItem(Player player, ItemStack itemStack, int howMany) {
        for (int i = 0; i < howMany; i++) {
            player.getInventory().addItem(itemStack);
        }
    }
}
