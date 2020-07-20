package killerm.minecraft.game.item;

import killerm.minecraft.communication.Message;
import killerm.minecraft.game.data.Team;
import killerm.minecraft.game.shop.ItemCategory;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.Arrays;

public class GameItem { // TODO: Nice descriptions and effects with enchantments
    public ItemStack diamond() {
        ItemStack diamond = new ItemStack(Material.DIAMOND, 1);
        setDisplayName(diamond, Message.ITEM_DIAMOND);

        return diamond;
    }

    public ItemStack knockbackStick() {
        ItemStack knockbackStick = new ItemStack(Material.STICK, 1);
        knockbackStick.addUnsafeEnchantment(Enchantment.KNOCKBACK, 10);

        return knockbackStick;
    }

    private void setDisplayName(ItemStack itemStack, String name) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(name);
        itemStack.setItemMeta(meta);
    }

    private void setLore(ItemStack itemStack, String... lore) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.setLore(Arrays.asList(lore));
        itemStack.setItemMeta(meta);
    }

    public ItemStack diaChest(Team team) {
        ItemStack diaChest = null;

        if (team == Team.AQUA) {
            diaChest = new ItemStack(Material.BLUE_SHULKER_BOX, 1);
            setDisplayName(diaChest, Message.ITEM_DIA_CHEST);
        } else if (team == Team.LAVA) {
            diaChest = new ItemStack(Material.RED_SHULKER_BOX, 1);
            setDisplayName(diaChest, Message.ITEM_DIA_CHEST);
        }

        return diaChest;
    }

    public ItemStack chestplate(Team team) {
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

    public ItemStack shopGlassPane(Team team) {
        ItemStack glassPane = null;

        if (team == Team.AQUA) {
            glassPane = new ItemStack(Material.BLUE_STAINED_GLASS_PANE);
        } else if (team == Team.LAVA) {
            glassPane = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        }

        setDisplayName(glassPane, Message.RESET + Message.ITALIC + Message.GREY + Message.SHOP_INSTRUCTION_LEFT_CLICK);
        setLore(glassPane, Message.RESET + Message.GREY + Message.SHOP_INSTRUCTION_RIGHT_CLICK);
        return glassPane;
    }

    public ItemStack shopBuildingItem() {
        ItemStack itemStack = new ItemStack(Material.BRICKS);
        setDisplayName(itemStack, Message.RESET + Message.ITALIC + Message.YELLOW + Message.BOLD + ItemCategory.BUILDING.toString());
        setLore(itemStack, Message.GREY + ItemCategory.BUILDING.getDescription());
        return itemStack;
    }

    public ItemStack shopMeleeItem() {
        ItemStack itemStack = new ItemStack(Material.STICK);
        setDisplayName(itemStack, Message.RESET + Message.YELLOW + Message.BOLD + ItemCategory.MELEE.toString());
        setLore(itemStack, Message.GREY + ItemCategory.MELEE.getDescription());
        return itemStack;
    }

    public ItemStack shopRangedItem() {
        ItemStack itemStack = new ItemStack(Material.BOW);
        setDisplayName(itemStack, Message.RESET + Message.YELLOW + Message.BOLD + ItemCategory.RANGED.toString());
        setLore(itemStack, Message.GREY + ItemCategory.RANGED.getDescription());
        return itemStack;
    }

    public ItemStack shopEffectsItem() {
        ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD);
        setDisplayName(itemStack, Message.RESET + Message.YELLOW + Message.BOLD + ItemCategory.EFFECTS.toString());
        setLore(itemStack, Message.GREY + ItemCategory.EFFECTS.getDescription());
        return itemStack;
    }

    public ItemStack shopProtectionItem() {
        ItemStack itemStack = new ItemStack(Material.SHIELD);
        setDisplayName(itemStack, Message.RESET + Message.YELLOW + Message.BOLD + ItemCategory.PROTECTION.toString());
        setLore(itemStack, Message.GREY + ItemCategory.PROTECTION.getDescription());
        return itemStack;
    }

    public ItemStack shopTrickyItem() {
        ItemStack itemstack = new ItemStack(Material.REDSTONE_TORCH);
        setDisplayName(itemstack, Message.RESET + Message.YELLOW + Message.BOLD + ItemCategory.TRICKY.toString());
        setLore(itemstack, Message.GREY + ItemCategory.TRICKY.getDescription());
        return itemstack;
    }

    public ItemStack melon() { // TODO: remove as soon as real items are implemented
        ItemStack itemstack = new ItemStack(Material.MELON);
        setDisplayName(itemstack, Message.DARK_GREEN + "MELONE!");
        setLore(itemstack, Message.GREEN + "A melon", Message.ITALIC + Message.AQUA + "20 Diamonds");
        itemstack.setAmount(64);
        return itemstack;
    }

    public ItemStack shop(Team team) {
        ItemStack itemstack = new ItemStack(Material.ENDER_CHEST);
        setDisplayName(itemstack, getShopName(team));
        return itemstack;
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
}
