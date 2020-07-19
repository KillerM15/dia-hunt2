package killerm.minecraft.data;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.Collection;

public class PlayerData {
    public ItemStack[] itemStacks;
    public ItemStack[] itemStacksArmor;
    public Location location;
    public GameMode gameMode;
    public Collection<PotionEffect> potionEffects;
    public double health;
    public int foodLevel;
}
