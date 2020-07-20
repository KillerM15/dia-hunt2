package killerm.minecraft.game.interaction;

import killerm.minecraft.game.data.Team;
import killerm.minecraft.game.item.GameItem;
import killerm.minecraft.manager.ItemManager;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collection;
import java.util.List;

public class ItemGiver {
    private static final int SHOP_ITEM_SLOT = 8;
    private ItemManager itemManager = new ItemManager();
    private GameItem gameItem = new GameItem();

    public void giveDia(ShulkerBox shulkerBox) {
        itemManager.give(shulkerBox, gameItem.diamond());
    }

    public void giveDia(Collection<Player> players) {
        for (Player player : players) {
            giveDia(player);
        }
    }

    public void giveDia(Player player) {
        itemManager.give(player, gameItem.diamond());
    }

    public void giveBaseAquaItems(Collection<Player> players) {
        for (Player player : players) {
            giveBaseAquaItems(player);
        }
    }

    public void giveBaseAquaItems(Player player) {
        itemManager.setChestplate(player, gameItem.chestplate(Team.AQUA));
        itemManager.give(player, gameItem.knockbackStick());
        itemManager.giveAtSlot(player, SHOP_ITEM_SLOT, gameItem.shop(Team.AQUA));
    }

    public void giveBaseLavaItems(Collection<Player> players) {
        for (Player player : players) {
            giveBaseLavaItems(player);
        }
    }

    public void giveBaseLavaItems(Player player) {
        itemManager.setChestplate(player, gameItem.chestplate(Team.LAVA));
        itemManager.give(player, gameItem.knockbackStick());
        itemManager.giveAtSlot(player, SHOP_ITEM_SLOT, gameItem.shop(Team.LAVA));
    }

    public void giveAquaDiaChest(Player player) {
        itemManager.give(player, gameItem.diaChest(Team.AQUA));
    }

    public void giveLavaDiaChest(Player player) {
        itemManager.give(player, gameItem.diaChest(Team.LAVA));
    }

    public void giveBoughtFromShop(Player player, ItemStack bought) {
        ItemStack itemBought = new ItemStack(bought);
        itemBought = removeLastLoreLine(itemBought);
        itemManager.give(player, itemBought);
    }

    private ItemStack removeLastLoreLine(ItemStack itemStack) {
        ItemMeta meta = itemStack.getItemMeta();
        List<String> lore = meta.getLore();
        lore.remove(lore.size() - 1);
        meta.setLore(lore);
        itemStack.setItemMeta(meta);

        return itemStack;
    }
}
