package killerm.minecraft.game.shop;

import killerm.minecraft.data.DiaConfig;
import killerm.minecraft.error.ItemNotFoundException;
import killerm.minecraft.game.item.GameItem;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class ItemPrice {
    private Map<String, DiaConfig> prices = new HashMap<>();
    private GameItem gameItem = new GameItem();

    public ItemPrice() {
        prices.put(gameItem.melon().getItemMeta().getDisplayName(), DiaConfig.PRICE_MELON);
    }

    public ItemPrice(GameItem gameItem) {
        this.gameItem = gameItem;
        prices.put(gameItem.melon().getItemMeta().getDisplayName(), DiaConfig.PRICE_MELON);
    }

    public int getPrice(ItemStack itemStack) {
        DiaConfig diaConfig = prices.get(itemStack.getItemMeta().getDisplayName());

        if (diaConfig == null) {
            throw new ItemNotFoundException(itemStack.getItemMeta().getDisplayName() + " is not a valid shop item");
        }
        return (int) (double) diaConfig.get();
    }
}
