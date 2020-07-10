package killerm.minecraft.communication;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.Collection;

public class Sounds {
    public static void play(Sound sound) {
        play((Collection<Player>) Bukkit.getOnlinePlayers(), sound);
    }

    public static void play(Collection<Player> players, Sound sound, float pitch) {
        for (Player player : players) {
            play(player, sound, pitch);
        }
    }

    public static void play(Collection<Player> players, Sound sound) {
        for (Player player : players) {
            play(player, sound);
        }
    }

    public static void play(Player player, Sound sound) {
        play(player, sound, 1);
    }

    public static void play(Player player, Sound sound, float pitch) {
        World world = player.getWorld();
        world.playSound(player.getLocation(), sound, 1, pitch);
    }
}
