package killerm.minecraft.game;

import killerm.minecraft.communication.Message;
import killerm.minecraft.utilities.Team;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class GameItem { // TODO: Nice descriptions and effects with enchantments
    public static ItemStack diamond() {
        ItemStack diamond = new ItemStack(Material.DIAMOND, 1);
        setCustomName(diamond, Message.DIAMOND);

        return diamond;
    }

    public static ItemStack knockbackStick() {
        ItemStack knockbackStick = new ItemStack(Material.STICK, 1);
        knockbackStick.addUnsafeEnchantment(Enchantment.KNOCKBACK, 10);

        return knockbackStick;
    }

    private static void setCustomName(ItemStack itemStack, String name) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(name);
        itemStack.setItemMeta(meta);
    }

    public static ItemStack diaChest(Team team) {
        ItemStack diaChest = null;

        if (team == Team.AQUA) {
            diaChest = new ItemStack(Material.BLUE_SHULKER_BOX, 1);
            setCustomName(diaChest, Message.DIA_CHEST);
        }

        if (team == Team.LAVA) {
            diaChest = new ItemStack(Material.RED_SHULKER_BOX, 1);
            setCustomName(diaChest, Message.DIA_CHEST);
        }

        return diaChest;
    }

    public static ItemStack chestplate(Team team) {
        ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
        LeatherArmorMeta meta = (LeatherArmorMeta) chestplate.getItemMeta();

        if (team == Team.AQUA) {
            meta.setColor(Color.fromRGB(4222368));
        } else if (team == Team.LAVA) {
            meta.setColor(Color.fromRGB(10502212));
        }

        meta.setUnbreakable(true);
        chestplate.setItemMeta(meta);

        return chestplate;
    }
}
