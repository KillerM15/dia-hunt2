package killerm.minecraft.game;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Collection;

public class StatsGiver {
    public void giveBaseStats(Collection<Player> players) {
        for (Player player : players) {
            giveBaseStats(player);
        }
    }

    public void giveBaseStats(Player player) {
        player.setGameMode(GameMode.SURVIVAL);
        player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, Integer.MAX_VALUE, 4));
        player.setHealth(40);
    }

    public void clear(Collection<Player> players) {
        for (Player player : players) {
            clear(player);
        }
    }

    public void clear(Player player) {
        clearPotions(player);
        player.getInventory().clear();
        player.setFireTicks(0);
    }

    public void clearPotions(Player player) {
        for (PotionEffect potionEffect : player.getActivePotionEffects()) {
            player.removePotionEffect(potionEffect.getType());
        }
    }

    public void giveSpectatorStats(Player player) {
        player.setGameMode(GameMode.SPECTATOR);
    }
}

